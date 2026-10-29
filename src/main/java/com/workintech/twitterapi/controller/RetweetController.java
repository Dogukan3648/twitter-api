package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.RetweetDTO;
import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.RetweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public Retweet save(@Valid @RequestBody RetweetDTO retweetDTO) {
        Retweet retweet = new Retweet();

        Tweet tweet = new Tweet();
        tweet.setId(retweetDTO.tweetId());
        retweet.setTweet(tweet);

        return retweetService.save(retweet);
    }

    @DeleteMapping("/{id}")
    public Retweet delete(@PathVariable long id) {
        return retweetService.delete(id);
    }

    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<Retweet> findByTweetId(@PathVariable long tweetId) {
        return retweetService.findByTweetId(tweetId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
