package com.kennymaness.apprenticeshipproject.data;

import com.kennymaness.apprenticeshipproject.models.BlogPost;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlogPostData {

    private static final Map<Integer, BlogPost> blogposts = new HashMap<>();

    public static Collection<BlogPost> getAll() {return blogposts.values();}

    public static BlogPost getById(int id) {return blogposts.get(id);}

    public static void add(BlogPost blogpost) {blogposts.put(blogpost.getId(), blogpost);}

    public static void remove(int id) {blogposts.remove(id);}

}
