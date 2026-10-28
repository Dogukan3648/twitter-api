package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.RetweetRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import com.workintech.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetweetServiceImpl implements RetweetService {
    private RetweetRepository retweetRepository;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.retweetRepository = retweetRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Retweet save(Retweet retweet) {
        User user = userRepository.findById(retweet.getUser().getId()).orElseThrow();

        Tweet tweet = tweetRepository.findById(retweet.getTweet().getId()).orElseThrow();

        retweet.setUser(user);
        retweet.setTweet(tweet);

        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet delete(long id) {
       Retweet retweet = retweetRepository.findById(id).orElseThrow();

       retweetRepository.delete(retweet);
       return retweet;
    }
}
