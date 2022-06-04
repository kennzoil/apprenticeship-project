package com.kennymaness.apprenticeshipproject.data;

import com.kennymaness.apprenticeshipproject.models.Comment;
import org.springframework.data.repository.CrudRepository;


public interface CommentRepository extends CrudRepository<Comment, Long> {
}
