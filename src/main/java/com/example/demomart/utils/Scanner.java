package com.example.demomart.utils;

import com.example.demomart.dataCollections.Cart;
import com.example.demomart.dataCollections.PriceBook;
import com.example.demomart.dataCollections.VirtualJournal;
import com.example.demomart.models.VirtualJournalEntry;
import javafx.scene.input.KeyEvent;

public class Scanner {
    private StringBuilder scannedBarcodes = new StringBuilder();
    public void handleKeyEvent(KeyEvent event){
        if(event.getCharacter().isBlank()){
            String scannedBarcode = scannedBarcodes.toString().trim();
            if(!scannedBarcode.isEmpty()){
                processScannedBarcode(scannedBarcode);
            }
            scannedBarcodes.setLength(0);
        }else{
            scannedBarcodes.append(event.getCharacter());
        }
    }

    private void processScannedBarcode(String scannedBarcode){
        Cart.addCartItem(PriceBook.lookupProduct(scannedBarcode));

        VirtualJournalEntry scanEntry = VirtualJournalEntry.builder()
                .event("Product Scanned")
                .eventDetails("Product Details: " + PriceBook.lookupProduct(scannedBarcode))
                        .build();

        VirtualJournal.logJournalEntry(scanEntry);

    }
}
