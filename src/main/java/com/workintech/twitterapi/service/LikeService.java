package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Like;

public interface LikeService {
    Like save(Like like);
    Like dislike(long tweetId);
    boolean isLiked(long tweetId);
}
