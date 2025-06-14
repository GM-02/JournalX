package com.journalX.Services;

import com.journalX.Entry.Entry;
import com.journalX.Entry.User;
import com.journalX.Repository.JournalRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalServices {
     @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserServices userServices;

    public List<Entry> getAllEntries() {
        return journalRepository.findAll();
    }

    public void saveEntry(Entry entry, String userName) {

            User user = userServices.findByUserName(userName);
            entry.setDateTime(LocalDateTime.now());
            Entry saved = journalRepository.save(entry);
            user.getJournalEntries().add(saved);
            userServices.saveUser(user);

    }
    public  void saveEntry(Entry entry) {
        entry.setDateTime(LocalDateTime.now());
        journalRepository.save(entry);
    }
    public Optional<Entry> findById(ObjectId id) {
        return journalRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName) {
        User user = userServices.findByUserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userServices.saveUser(user);
        journalRepository.deleteById(id);
    }
}
