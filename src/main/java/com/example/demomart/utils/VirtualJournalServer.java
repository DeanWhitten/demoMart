package com.example.demomart.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class VirtualJournalServer extends Thread {
        private static final Set<PrintWriter> clientWriters = new HashSet<>();

        public static void main(String[] args) {
                virtualServer();
        }

        public static void virtualServer() {
                new ServerThread().start();
        }

        private static class ServerThread extends Thread {
                @Override
                public void run() {
                        try {
                                ServerSocket ss = new ServerSocket(7777);
                                System.out.println("Server awaiting connection ...");

                                while (true) {
                                        Socket socket = ss.accept();
                                        System.out.println("Connection made from: " + socket);

                                        PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
                                        clientWriters.add(outputWriter); // Add this client's writer to the set

                                        // Create a new thread to handle communication with this client
                                        new Thread(new ClientHandler(socket)).start();
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        private static class ClientHandler implements Runnable {
                private final Socket socket;

                public ClientHandler(Socket socket) {
                        this.socket = socket;
                }

                @Override
                public void run() {
                        try {
                                BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                // Continuous communication
                                while (true) {
                                        // Read data from client
                                        String msg = inputReader.readLine();
                                        if (msg != null) {

                                                // Broadcast the new entry to all connected clients
                                                broadcastNewEntry(msg);
                                        }
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }

                private void broadcastNewEntry(String entry) {
                        for (PrintWriter writer : clientWriters) {
                                try {
                                        // Send new entry to each connected client
                                        writer.println("NEW JOURNAL ENTRY: " + entry);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
}

