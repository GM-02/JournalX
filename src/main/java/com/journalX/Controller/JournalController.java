package com.journalX.Controller;

import com.journalX.Entry.Entry;
import com.journalX.Entry.User;
import com.journalX.Services.JournalServices;
import com.journalX.Services.UserServices;
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

    @Autowired
    private UserServices userServices;
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String userName) {
        User user = userServices.findByUserName(userName);
        List<Entry> all =user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new  ResponseEntity<>(all,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{userName}")
    public ResponseEntity<Entry> CreateEntry(@RequestBody Entry entry,@PathVariable String userName) {
      try {

          journalServices.saveEntry(entry,userName);
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

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myid, @PathVariable String userName) {
            journalServices.deleteById(myid, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 @PutMapping("id/{userName}/{myid}")
public ResponseEntity<?>
 updateEntry(@PathVariable ObjectId myid,
             @RequestBody Entry newEntry,
             @PathVariable String userName
                                     ) {
    Entry existingEntry = journalServices.findById(myid).orElse(null);
    if (existingEntry != null) {
        existingEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")?  newEntry.getTitle() : existingEntry.getTitle());
        existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : existingEntry.getContent());
        journalServices.saveEntry(existingEntry);
        return new ResponseEntity<>(existingEntry, HttpStatus.OK);
    }
 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
