package com.javacourse.courseprojectfx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    private float rating;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Cart chat;

    public Comment(String commentTitle, String commentBody, double rating, User user, Product product) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.rating = (float) rating;
        this.owner = user;
        this.product = product;
    }

    public Comment(String commentTitle, String commentBody, User owner, Comment parentComment) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.owner = owner;
        this.parentComment = parentComment;
    }
}
