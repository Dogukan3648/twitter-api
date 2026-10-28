package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;

public interface RetweetService {
    Retweet save(Retweet retweet);
    Retweet delete(long id);
}
