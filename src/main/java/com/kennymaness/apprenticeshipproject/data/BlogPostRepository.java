package com.kennymaness.apprenticeshipproject.data;

import com.kennymaness.apprenticeshipproject.models.BlogPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {

    BlogPost findById(int id);
}
