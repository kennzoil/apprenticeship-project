package com.kennymaness.apprenticeshipproject.models;

import org.hibernate.annotations.Type;
import org.hibernate.type.TextType;
import org.hibernate.type.descriptor.sql.LongVarcharTypeDescriptor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Comment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private LocalDateTime submission_date;

    @NotNull
    private String body;

    @ManyToOne
    @JoinColumn(name = "blogpost_id")
    private BlogPost blogpost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {}

    public Comment(LocalDateTime submission_date,
                   String body,
                   BlogPost blogpost,
                   User user)
    {
        this.submission_date = submission_date;
        this.body = body;
        this.blogpost = blogpost;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BlogPost getBlogpost() {
        return blogpost;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(LocalDateTime submission_date) {
        this.submission_date = submission_date;
    }
}
