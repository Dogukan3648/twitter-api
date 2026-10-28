package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }
    @PostMapping("/like")
    public Like save(@RequestBody Like like){
        return likeService.save(like);
    }
    @PostMapping("/dislike")
    public Like dislike(@RequestBody Like like){
        return likeService.dislike(like.getUser().getId(),
                like.getTweet().getId());
    }
}
