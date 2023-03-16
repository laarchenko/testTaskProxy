package com.example.testtaskproxysellers.dto;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class NoteDto {

    String id;

    String text;

    boolean canLike;

    boolean haveLiked;

    int likes;

    boolean currentUserIsAuthor;
}
