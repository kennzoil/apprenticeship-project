package com.kennymaness.apprenticeshipproject.data;

import com.kennymaness.apprenticeshipproject.models.Comment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommentData {

    public static final Map<Integer, Comment> comments = new HashMap<>();

    public static Collection<Comment> getAll() {return comments.values();}

    public static Comment getById(int id) {return comments.get(id);}

    public static void add(Comment comment) {comments.put(comment.getId(), comment);}

    public static void remove(int id) { comments.remove(id);}

}
