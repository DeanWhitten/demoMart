package com.example.demomart.dataCollections;

import com.example.demomart.models.Transaction;
import com.example.demomart.models.VirtualJournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionLog {
    private static ObservableList<Transaction> allTransactions = FXCollections.observableArrayList();

    public static void logTransaction(Transaction transaction){
        allTransactions.add(transaction);
        VirtualJournalEntry transactionEntry = VirtualJournalEntry.builder()
                .event("Transaction Completed")
                .eventDetails("Transaction Details: " + transaction.toString())
                .build();
        VirtualJournal.logJournalEntry(transactionEntry);
    }
}
