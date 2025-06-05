package com.journalX.Entry;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Journal_entries")
@Data
public class Entry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
}
