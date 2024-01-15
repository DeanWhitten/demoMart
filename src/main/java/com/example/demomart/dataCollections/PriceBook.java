package com.example.demomart.dataCollections;

import com.example.demomart.models.Product;
import com.example.demomart.utils.TsvParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PriceBook {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static void loadBookData(){
        allProducts.addAll(TsvParser.parseTsvFile());
    }

    public static Product lookupProduct(String barcode){
        if(allProducts.isEmpty()){
            loadBookData();
        }else{
            allProducts.clear();
            loadBookData();
        }

        Product productFound = null ;
        for(Product product: allProducts){
            if(product.getBarcode().equals(barcode)){
                productFound = product;
                product.setQty(1);
            }
        }
        return productFound;
    }
}
