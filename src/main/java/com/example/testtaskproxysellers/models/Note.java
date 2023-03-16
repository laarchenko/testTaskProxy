package com.example.testtaskproxysellers.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Document
@Builder
@Data
public class Note {

    @Id
    String id;

    String text;

    String authorUserName;

    Date date;

    Set<String> likedUsersNames;

}

