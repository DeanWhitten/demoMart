package com.example.demomart.controllers;

import com.example.demomart.dataCollections.Cart;
import com.example.demomart.models.Drawer;
import com.example.demomart.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class POSController implements Initializable {
    @FXML
    private TableView<Product> transactionTable;
    @FXML
    private TableColumn<Product, Integer> productQtyCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, BigDecimal> productPriceCol;

    @FXML
    private Label subtotalText;
    @FXML
    private Label taxText;
    @FXML
    private Label totalText;
    @FXML
    private Label dueAmountText;
    @FXML
    private Label dueAmountLabel;

    @FXML
    private Button drawerBtn;
    Drawer drawer = new Drawer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        productQtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        transactionTable.setItems(Cart.getAllCartItems());
    }

    public void onVoidItemsClick() {
        if(!Cart.getAllCartItems().isEmpty()){
            Product productSelected = transactionTable.getSelectionModel().getSelectedItem();
            try {
                if (productSelected != null) {
                    Cart.voidProduct(productSelected.getBarcode());
                    updateTotals();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }

    public void onVoidTransactionClick() {
        if(!Cart.getAllCartItems().isEmpty()) {
            try {
                if (!Cart.getAllCartItems().isEmpty()) {
                    Cart.voidCart();
                    updateTotals();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void onScan(KeyEvent e) {
        updateTotals();
    }

    public void onNextDollarClick() {
        if(!Cart.getAllCartItems().isEmpty()) {
            BigDecimal total = Cart.getTotal();
            BigDecimal cash = total.setScale(0, RoundingMode.CEILING); // Round up to the next dollar

            handleDrawer(true);
            BigDecimal change = Cart.getCalculatedChange(cash);
            updateDueAmount(change);
            Cart.completeTransaction(cash);
        }
    }

    public void onExactDollarClick() {
        if(!Cart.getAllCartItems().isEmpty()) {
            BigDecimal total = Cart.getTotal();
            BigDecimal cash = total;

            handleDrawer(true);
            BigDecimal change = Cart.getCalculatedChange(cash);
            updateDueAmount(change);
            Cart.completeTransaction(cash);
        }
    }

    private void updateDueAmount(BigDecimal change) {
        dueAmountText.setText("$" + String.format("%.2f", change));
    }

    public void onDrawerBtnClick() {
        handleDrawer(false);
        updateTotals();
    }

    private void handleDrawer(boolean isOpen) {
        drawer.setIsOpen(isOpen);
        drawerBtn.setDisable(!isOpen);
        drawerBtn.setVisible(isOpen);
        dueAmountLabel.setVisible(isOpen);
        dueAmountText.setVisible(isOpen);
        drawerBtn.setText(isOpen ? "Close Drawer" : "Drawer Is Closed");
        drawer.logDrawerEvent(isOpen);
    }

    public void updateTotals() {
        BigDecimal subtotal = Cart.getCartSubtotal();
        BigDecimal taxAmount = Cart.getTaxAmount();
        BigDecimal total = Cart.getTotal();

        // Update UI labels
        subtotalText.setText("$" + String.format("%.2f", subtotal));
        taxText.setText("$" + String.format("%.2f", taxAmount));
        totalText.setText("$" + String.format("%.2f", total));

    }
}
