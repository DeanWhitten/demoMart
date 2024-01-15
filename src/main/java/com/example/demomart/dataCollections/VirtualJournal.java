package com.example.demomart.dataCollections;

import com.example.demomart.models.VirtualJournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class VirtualJournal {
    private static ObservableList<VirtualJournalEntry> allJournalEntries = FXCollections.observableArrayList();
    private static Socket serverSocket;

    public static void logJournalEntry(VirtualJournalEntry entry) {
        allJournalEntries.add(entry);
        System.out.println(entry);
        // Send the entry to the server
        sendEntryToServer(entry);
    }

    //maybe move this logic to theVJS??
    private static void sendEntryToServer(VirtualJournalEntry entry) {
        try {
            if (serverSocket == null || serverSocket.isClosed()) {
                serverSocket = new Socket("localhost", 7777);
            }

            PrintWriter outputWriter = new PrintWriter(serverSocket.getOutputStream(), true);
            // Send entry data to the server
            outputWriter.println(entry.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

