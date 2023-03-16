package com.example.testtaskproxysellers.repositories;

import com.example.testtaskproxysellers.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

}
