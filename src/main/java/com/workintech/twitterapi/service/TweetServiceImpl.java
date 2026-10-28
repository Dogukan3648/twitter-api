
package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exception.TwitterException;
import com.workintech.twitterapi.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;
    private RetweetRepository retweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository,
                            UserRepository userRepository,
                            CommentRepository commentRepository,
                            LikeRepository likeRepository,
                            RetweetRepository retweetRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.retweetRepository = retweetRepository;
    }

    @Override
    public Tweet save(Tweet tweet) {
        if (tweet.getId() != null) {
            throw new TwitterException("New tweet ID must be null.",HttpStatus.BAD_REQUEST);
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TwitterException(
                        "User not found.",HttpStatus.NOT_FOUND
                ));

        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findByUserId(long userId) {
        return tweetRepository.findByUserId(userId);
    }

    @Override
    public Tweet findById(long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Tweet not found.", HttpStatus.NOT_FOUND));
    }

    @Override
    public Tweet update(long id, Tweet tweet) {
        Tweet existingTweet = findById(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!existingTweet.getUser().getEmail().equals(email)) {
            throw new TwitterException( "Only the tweet owner can update this tweet.",HttpStatus.FORBIDDEN);
        }

        existingTweet.setContent(tweet.getContent());
        return tweetRepository.save(existingTweet);
    }

    @Override
    @Transactional
    public Tweet delete(long id) {
        Tweet tweet = findById(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new TwitterException("Only the tweet owner can delete this tweet.",HttpStatus.FORBIDDEN);
        }

        commentRepository.deleteByTweetId(id);
        likeRepository.deleteByTweetId(id);
        retweetRepository.deleteByTweetId(id);

        tweetRepository.delete(tweet);
        return tweet;
    }
}
