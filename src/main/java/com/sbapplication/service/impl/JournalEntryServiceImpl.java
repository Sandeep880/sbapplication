package com.sbapplication.service.impl;


import com.sbapplication.entity.JournalEntry;
import com.sbapplication.entity.User;
import com.sbapplication.repo.JournalEntryRepository;
import com.sbapplication.service.JournalEntryService;
import com.sbapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryServiceImpl.class);

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDate.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e) {
            logger.info("user is already created and stored in DB");
            //System.out.println(e);
            log.error("Error ",e);
            throw new RuntimeException("An error occured while saving the entry");
        }
    }

    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry);
    }

    @Override
    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Override
    public Optional<JournalEntry> getJournalEntryId(ObjectId objectId) {
        return journalEntryRepository.findById(objectId);
    }

    @Override
    @Transactional
    public boolean deleteJournalEntryId(ObjectId id, String username) {
        boolean removed =false;
        try {
           User user = userService.findByUsername(username);
           removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
           if(removed)
           {
               userService.saveUser(user);
               journalEntryRepository.deleteById(id);
           }
       }
       catch (Exception e)
       {
           log.error("An error occured while deleted",e);
           //System.out.println(e);
           //System.out.println("An error occured while deleted");
       }
        return removed;
    }
}
