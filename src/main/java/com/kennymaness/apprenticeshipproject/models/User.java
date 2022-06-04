package com.kennymaness.apprenticeshipproject.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    private static final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String pwhash;

    @OneToMany(mappedBy = "user")
    private List<BlogPost> blogposts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwhash = encoder.encode(password);
    }

    public int getId() { return id;}

    public String getUsername() {
        return username;
    }

    public Boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwhash);
    }

    public List<BlogPost> getBlogposts() {
        return blogposts;
    }

    public void createBlogpost(BlogPost blogpost) {
        this.blogposts.add(blogpost);
    }

    public void deleteBlogpost(BlogPost blogpost) {
        this.blogposts.remove(blogpost);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void createComment(Comment comment) {
        this.comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }
}
