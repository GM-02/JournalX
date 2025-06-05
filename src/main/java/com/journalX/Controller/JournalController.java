package com.journalX.Controller;


import com.journalX.Entry.Entry;
import com.journalX.Services.JournalServices;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalServices journalServices;

    @GetMapping
    public ResponseEntity<?> getAllEntries() {
        List<Entry> all =journalServices.getAllEntries();
        if (all != null && !all.isEmpty()) {
            return new  ResponseEntity<>(all,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return an empty list if no entries are found
        }
    }
    // Additional methods for creating, updating, and deleting entries can be added here
    @PostMapping
    public ResponseEntity<Entry> CreateEntry(@RequestBody Entry entry) {
      try {
          entry.setDateTime(java.time.LocalDateTime.now());
          journalServices.saveEntry(entry);
          return new ResponseEntity<>(entry, HttpStatus.OK);
      } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

    }
    @GetMapping("id/{myid}")
    public ResponseEntity<Entry> getEntryById(@PathVariable ObjectId myid){
        Optional<Entry> byId = journalServices.findById(myid);
        if (byId.isPresent()){
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);}
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myid){
            journalServices.deleteById(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 @PutMapping("id/{myid}")
public ResponseEntity<?> updateEntry(@PathVariable ObjectId myid, @RequestBody Entry newEntry) {
    Entry existingEntry = journalServices.findById(myid).orElse(null);
    if (existingEntry != null) {
       existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : existingEntry.getContent());
        existingEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : existingEntry.getTitle());
        journalServices.saveEntry(existingEntry);
        return new ResponseEntity<>(existingEntry, HttpStatus.OK);
    }
 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
