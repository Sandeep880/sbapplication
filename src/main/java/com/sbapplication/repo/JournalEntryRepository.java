package com.sbapplication.repo;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbapplication.entity.JournalEntry;

public interface JournalEntryRepository  extends MongoRepository<JournalEntry, ObjectId> {
}
