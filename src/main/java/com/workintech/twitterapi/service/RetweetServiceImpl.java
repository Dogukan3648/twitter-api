package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exception.TwitterException;
import com.workintech.twitterapi.repository.RetweetRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import com.workintech.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (retweet.getId() != null) {
           throw new TwitterException("New retweet ID must be null.", HttpStatus.BAD_REQUEST);
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TwitterException("User not found.",HttpStatus.NOT_FOUND));

        Tweet tweet = tweetRepository.findById(retweet.getTweet().getId())
                .orElseThrow(() -> new TwitterException("Tweet not found.",HttpStatus.NOT_FOUND));

        if (retweetRepository.findByUserIdAndTweetId(user.getId(),tweet.getId()).isPresent()){
            throw new TwitterException("Tweet already retweeted",HttpStatus.CONFLICT);
        }

        retweet.setUser(user);
        retweet.setTweet(tweet);

        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet delete(long id) {
        Retweet retweet = retweetRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Retweet not found.",HttpStatus.NOT_FOUND));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!retweet.getUser().getEmail().equals(email)) {
            throw new TwitterException("Only the retweet owner can delete this retweet.",HttpStatus.FORBIDDEN);
        }

        retweetRepository.delete(retweet);
        return retweet;
    }
}
