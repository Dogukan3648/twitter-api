package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService){
        this.tweetService = tweetService;
    }

    @PostMapping
    public Tweet save(@RequestBody Tweet tweet){
        return tweetService.save(tweet);
    }
    @GetMapping("/findByUserId/{userId}")
    public List<Tweet> findByUserId(@PathVariable long userId){
        return tweetService.findByUserId(userId);
    }

    @GetMapping("/findById/{id}")
    public Tweet findById(@PathVariable long id){
        return tweetService.findById(id);
    }
    @PutMapping("/{id}")
    public Tweet update(@PathVariable long id,@RequestBody Tweet tweet){
        return tweetService.update(id,tweet);
    }
    @DeleteMapping("/{id}")
    public Tweet delete(@PathVariable long id){
        return tweetService.delete(id);
    }



}
