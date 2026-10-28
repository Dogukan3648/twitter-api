package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;

public interface CommentService {
    Comment save(Comment comment);
    Comment update(long id,Comment comment);
    Comment delete(long id);
}
