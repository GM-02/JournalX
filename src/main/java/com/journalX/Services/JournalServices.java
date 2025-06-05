package com.journalX.Services;

import com.journalX.Entry.Entry;
import com.journalX.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class JournalServices {
     @Autowired
    private JournalRepository journalRepository;

    public List<Entry> getAllEntries() {
        return journalRepository.findAll();
    }

    public void saveEntry( Entry entry) {
        journalRepository.save(entry);
    }
    public Optional<Entry> findById(ObjectId id) {
        return journalRepository.findById(id);
    }
    public void deleteById(ObjectId id) {
        journalRepository.deleteById(id);
    }
}
