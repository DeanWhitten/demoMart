package com.example.demomart.dataCollections;

import com.example.demomart.dataCollections.TransactionLog;
import com.example.demomart.dataCollections.VirtualJournal;
import com.example.demomart.models.Product;
import com.example.demomart.models.Transaction;
import com.example.demomart.models.VirtualJournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

    public class Cart {
        private static ObservableList<Product> allCartItems = FXCollections.observableArrayList();

        public static ObservableList<Product> getAllCartItems() {
            return allCartItems;
        }

        public static void addCartItem(Product product) {
            allCartItems.add(product);
        }

        public static void voidProduct(String bc) {
            Iterator<Product> iterator = allCartItems.iterator();
            while (iterator.hasNext()) {
                Product p = iterator.next();
                if (p.getBarcode().equals(bc)) {
                    iterator.remove();
                    VirtualJournalEntry scanEntry = VirtualJournalEntry.builder()
                            .event("Product Voided")
                            .eventDetails("Product Details: " + p)
                            .build();
                    VirtualJournal.logJournalEntry(scanEntry);
                    break;
                }
            }
        }

        public static void voidCart() {
            VirtualJournalEntry scanEntry = VirtualJournalEntry.builder()
                    .event("Cart Voided")
                    .eventDetails("Void Details: " +
                            " Void Qty: " + getAllCartItems().size() +
                            " Voided Cart Total: " + getCartSubtotal() +
                            " Voided Items: " + getAllCartItems())
                    .build();
            VirtualJournal.logJournalEntry(scanEntry);
            allCartItems.clear();
        }

        public static BigDecimal getCartSubtotal() {
            return allCartItems.stream()
                    .filter(Objects::nonNull)
                    .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQty())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public static BigDecimal getTaxAmount() {
            BigDecimal subtotal = getCartSubtotal();
            BigDecimal taxRate = BigDecimal.valueOf(0.07);
            return subtotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        }

        public static BigDecimal getTotal() {
            BigDecimal subtotal = getCartSubtotal();
            BigDecimal taxAmount = getTaxAmount();
            return subtotal.add(taxAmount).setScale(2, RoundingMode.HALF_UP);
        }

        public static void completeTransaction(BigDecimal cash) {
            Transaction transaction = Transaction.builder()
                    .purchasedProducts(new ArrayList<>(getAllCartItems()))
                    .purchasedProductsQty(getAllCartItems().size())
                    .subtotal(getCartSubtotal())
                    .taxAmount(getTaxAmount())
                    .grandTotal(getTotal())
                    .cash(cash)
                    .change(getCalculatedChange(cash))
                    .build();

            logTransaction(transaction);

            allCartItems.clear();
        }

        private static void logTransaction(Transaction transaction) {
            TransactionLog.logTransaction(transaction);
        }

        public static BigDecimal getCalculatedChange(BigDecimal cash) {
            BigDecimal grandTotal = getTotal();
            return cash.subtract(grandTotal).setScale(2, RoundingMode.HALF_UP);
        }
    }
