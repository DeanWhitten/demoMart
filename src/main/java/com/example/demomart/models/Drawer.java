package com.example.demomart.models;

import com.example.demomart.dataCollections.VirtualJournal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Drawer {
    private Boolean isOpen;

    public void logDrawerEvent(Boolean isOpen){
        String drawerState = isOpen ? "Opened" : "Closed";
        VirtualJournalEntry drawerEventEntry = VirtualJournalEntry.builder()
                .event("Drawer Event")
                .eventDetails("Drawer " + drawerState)
                .build();
        VirtualJournal.logJournalEntry(drawerEventEntry);
    }
}
