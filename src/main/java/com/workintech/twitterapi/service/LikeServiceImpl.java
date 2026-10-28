package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.LikeRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import com.workintech.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Like save(Like like) {
        User user = userRepository.findById(like.getUser().getId()).orElseThrow();

        Tweet tweet = tweetRepository.findById(like.getTweet().getId()).orElseThrow();

        like.setUser(user);
        like.setTweet(tweet);

        return likeRepository.save(like);
    }

    @Override
    public Like dislike(long userId, long tweetId) {
        Like like = likeRepository.findByUserIdAndTweetId(userId,tweetId).orElseThrow();

        likeRepository.delete(like);
        return like;

    }
}
