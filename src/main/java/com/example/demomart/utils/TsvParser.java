package com.example.demomart.utils;

import com.example.demomart.models.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TsvParser {
    public static List<Product> parseTsvFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("/Users/deanfwhitten/Desktop/demoMart/src/main/resources/pricebook.tsv"))) {
            return reader.lines()
                    .map(TsvParser::parseTsvLine)
                    .filter(product -> product != null)
                    .collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    private static Product parseTsvLine(String line){
        String[] parts = line.split("\t");

        if(parts.length == 3){
            try {
                String barcode = parts[0];
                String name = parts[1].trim();
                BigDecimal unitPrice = BigDecimal.valueOf(Double.parseDouble(parts[2]));

                return new Product(barcode,name,unitPrice.setScale(2), 1);
            }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
