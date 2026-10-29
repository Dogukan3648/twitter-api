package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;

import java.util.Optional;

public interface RetweetService {
    Retweet save(Retweet retweet);
    Retweet delete(long id);
    Optional<Retweet> findByTweetId(long tweetId);
}
