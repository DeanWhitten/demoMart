package com.example.demomart.models;

import com.example.demomart.models.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
    @Builder.Default
    private String cashier = "Joe Demo";
    @Builder.Default
    private String location = "Demo City";
    @Builder.Default
    private int registerNum = 1;
    @Builder.Default
    private BigDecimal taxRate = BigDecimal.valueOf(0.07);

    private ArrayList<Product> purchasedProducts;
    private int purchasedProductsQty;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal grandTotal;

    private BigDecimal cash;
    private BigDecimal change;
}
