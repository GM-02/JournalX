package com.journalX.Repository;

import com.journalX.Entry.Entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Entry, ObjectId> {
    // This interface will automatically provide CRUD operations for Entry objects
    // No additional methods are needed unless custom queries are required
}
// Controller ---> Service ---> Repository
