package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService){
        this.retweetService = retweetService;
    }
    @PostMapping
    public Retweet save(@RequestBody Retweet retweet){
        return retweetService.save(retweet);
    }
    @DeleteMapping("/{id}")
    public Retweet delete(@PathVariable long id){
        return retweetService.delete(id);
    }
}
