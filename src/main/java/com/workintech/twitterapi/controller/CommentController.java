package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @PostMapping
    public Comment save(@RequestBody Comment comment){
        return commentService.save(comment);
    }
    @PutMapping("/{id}")
    public Comment update(@PathVariable long id,@RequestBody Comment comment){
        return commentService.update(id,comment);
    }
    @DeleteMapping("/{id}")
    public Comment delete(@PathVariable long id){
        return commentService.delete(id);
    }
}
