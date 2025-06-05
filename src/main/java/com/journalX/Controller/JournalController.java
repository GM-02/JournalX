package com.journalX.Controller;


import com.journalX.Entry.Entry;
import com.journalX.Services.JournalServices;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalServices journalServices;

    @GetMapping
    public List<Entry> getAllEntries() {
        return new ArrayList<>(journalServices.getAllEntries());
    }
    // Additional methods for creating, updating, and deleting entries can be added here
    @PostMapping
    public Entry CreateEntry(@RequestBody Entry entry) {
        entry.setDateTime(java.time.LocalDateTime.now());
        journalServices.saveEntry(entry);
        return entry;
    }
    @GetMapping("id/{myid}")
    public Entry getEntryById(@PathVariable ObjectId myid){
 return journalServices.findById(myid).orElse(null);
    }

    @DeleteMapping("id/{myid}")
    public boolean deleteEntryById(@PathVariable ObjectId myid){
            journalServices.deleteById(myid);
        return true;
    }
 @PutMapping("id/{myid}")
public Entry updateEntry(@PathVariable ObjectId myid, @RequestBody Entry newEntry) {
    Entry existingEntry = journalServices.findById(myid).orElse(null);
    if (existingEntry != null) {
       existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : existingEntry.getContent());
        existingEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : existingEntry.getTitle());

    }
        journalServices.saveEntry(existingEntry);
    return existingEntry;
    }

}
