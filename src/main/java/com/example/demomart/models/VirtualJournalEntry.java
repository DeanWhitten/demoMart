package com.example.demomart.models;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class VirtualJournalEntry {
    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
    @Builder.Default
    private String cashier = "Joe Demo";
    @Builder.Default
    private String location = "Demo City";
    @Builder.Default
    private int registerNum = 1;

    private String event;
    private String eventDetails;
}
