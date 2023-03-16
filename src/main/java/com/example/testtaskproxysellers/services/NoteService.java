package com.example.testtaskproxysellers.services;

import com.example.testtaskproxysellers.dto.NoteDto;
import com.example.testtaskproxysellers.models.Note;
import com.example.testtaskproxysellers.repositories.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void saveNote(String authorUsername, String text) {
        Note note = Note.builder()
                .id(UUID.randomUUID().toString())
                .text(text)
                .authorUserName(authorUsername)
                .date( new Date(System.currentTimeMillis()))
                .likedUsersNames(Collections.emptySet())
                .build();
        noteRepository.save(note);
        logger.info("saved: "+ note);
    }

    public void deleteNoteById(String noteId) {
        Note note = noteRepository.findById(noteId).orElseGet(() -> {
            logger.info("cant find note by id: "+noteId);
            return null;
        });
        if(note!=null) {
            noteRepository.delete(note);
            logger.info("deleted: "+note);
        }
    }

    public List<Note> getAllSortedNotes() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "date"));
        return mongoTemplate.find(query, Note.class);
    }

    public List<NoteDto> convertNotesToDto(List<Note> notes, String currentUserName) {
        return notes.stream().map(note -> NoteDto.builder()
                        .id(note.getId())
                        .text(note.getText())
                        .likes(note.getLikedUsersNames().size())
                        .canLike(!currentUserName.equals("anonymousUser"))
                        .haveLiked(note.getLikedUsersNames().stream().anyMatch(username -> username.equals(currentUserName)))
                        .currentUserIsAuthor(note.getAuthorUserName().equals(currentUserName))
                        .build())
                .collect(Collectors.toList());
    }

    public void addOrRemoveLike(String noteId, String currentUserName) {
        Note note = getById(noteId);
        Set<String> likesByUserNames = note.getLikedUsersNames();
        if(note.getLikedUsersNames().stream().noneMatch(userName -> userName.equals(currentUserName))) {
            likesByUserNames.add(currentUserName);
        }
        else {
            likesByUserNames.removeIf(e -> e.equals(currentUserName));
        }
        note.setLikedUsersNames(likesByUserNames);
        noteRepository.save(note);
        logger.info("set or removed like in note: "+ note);
    }

    private Note getById(String id) {
        return noteRepository.findById(id).orElseGet(() -> {
            logger.info("cant find note by id: "+id);
            return null;
        });
    }

    public void updateNote(String id, String text) {
        Note note = getById(id);
        if(note!=null){
            note.setText(text);
            noteRepository.save(note);
            logger.info("updated: "+ note);
        }
    }
}
