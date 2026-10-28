package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.LikeDTO;
import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public Like save(@Valid @RequestBody LikeDTO likeDTO) {
        Like like = new Like();

        Tweet tweet = new Tweet();
        tweet.setId(likeDTO.tweetId());
        like.setTweet(tweet);

        return likeService.save(like);
    }

    @PostMapping("/dislike")
    public Like dislike(@Valid @RequestBody LikeDTO likeDTO) {
        return likeService.dislike(likeDTO.tweetId());
    }
}
