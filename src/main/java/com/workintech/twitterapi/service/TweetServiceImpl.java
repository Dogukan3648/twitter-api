
package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = userRepository.findById(tweet.getUser().getId())
                .orElseThrow();

        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findByUserId(long userId) {
        return tweetRepository.findByUserId(userId);
    }

    @Override
    public Tweet findById(long id) {
        return tweetRepository.findById(id).orElseThrow();
    }

    @Override
    public Tweet update(long id, Tweet tweet) {
        Tweet existingTweet = findById(id);

        existingTweet.setContent(tweet.getContent());
        return tweetRepository.save(existingTweet);
    }

    @Override
    @Transactional
    public Tweet delete(long id) {
        Tweet tweet = findById(id);

        commentRepository.deleteByTweetId(id);
        likeRepository.deleteByTweetId(id);
        retweetRepository.deleteByTweetId(id);

        tweetRepository.delete(tweet);
        return tweet;
    }
}
