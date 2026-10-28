package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.CommentDTO;
import com.workintech.twitterapi.dto.CommentUpdateDTO;
import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment save(@Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.content());

        Tweet tweet = new Tweet();
        tweet.setId(commentDTO.tweetId());
        comment.setTweet(tweet);

        return commentService.save(comment);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable long id,@Valid @RequestBody CommentUpdateDTO commentUpdateDTO) {
        Comment comment = new Comment();
        comment.setContent(commentUpdateDTO.content());

        return commentService.update(id,comment);
    }

    @DeleteMapping("/{id}")
    public Comment delete(@PathVariable long id) {
        return commentService.delete(id);
    }
}
