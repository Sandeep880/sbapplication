package com.sbapplication.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@Builder
public class User {
    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String username;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;
}
