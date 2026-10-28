package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;

import java.util.List;

public interface TweetService {
    Tweet save(Tweet tweet);
    List<Tweet> findByUserId(long userId);
    Tweet findById(long id);
    Tweet update(long id,Tweet tweet);
    Tweet delete(long id);
}
