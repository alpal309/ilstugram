package com.ilstugram.repository;

import com.ilstugram.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepo extends CrudRepository<Comment, String> {
    ArrayList<Comment> findCommentByImageIdAndEnabled(String imageId, int enabled);
}