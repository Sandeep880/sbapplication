package com.sbapplication.service;


import org.bson.types.ObjectId;

import com.sbapplication.entity.JournalEntry;

import java.util.List;
import java.util.Optional;


public interface JournalEntryService {

    public void saveEntry(JournalEntry journalEntry, String username);

    public void saveEntry(JournalEntry journalEntry);

    public List<JournalEntry> getAll();

    public Optional<JournalEntry> getJournalEntryId(ObjectId objectId);

    public boolean deleteJournalEntryId(ObjectId id, String username);

}
