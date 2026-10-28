package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet,Long> {
    Optional<Retweet> findByUserIdAndTweetId(long userId,long tweetId);
    void deleteByTweetId(long tweetId);
}
