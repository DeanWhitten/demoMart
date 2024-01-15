package com.example.demomart.models;

import lombok.*;
import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private String barcode;
    private String name;
    private BigDecimal unitPrice;
    private int qty;
}
