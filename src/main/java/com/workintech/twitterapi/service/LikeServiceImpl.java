package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exception.TwitterException;
import com.workintech.twitterapi.repository.LikeRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import com.workintech.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (like.getId() != null) {
            throw new TwitterException("New like ID must be null.", HttpStatus.BAD_REQUEST);
        }


        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TwitterException("User not found.",HttpStatus.NOT_FOUND));

        Tweet tweet = tweetRepository.findById(like.getTweet().getId())
                .orElseThrow(() -> new TwitterException("Tweet not found.",HttpStatus.NOT_FOUND));

        if (likeRepository.findByUserIdAndTweetId(user.getId(),tweet.getId()).isPresent()){
            throw new TwitterException("Tweet already liked.",HttpStatus.CONFLICT);
        }


        like.setUser(user);
        like.setTweet(tweet);

        return likeRepository.save(like);
    }

    @Override
    public Like dislike(long tweetId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TwitterException("User not found.",HttpStatus.NOT_FOUND));


        Like like = likeRepository.findByUserIdAndTweetId(user.getId(), tweetId)
                .orElseThrow(() -> new TwitterException("Like not found.",HttpStatus.NOT_FOUND));

        likeRepository.delete(like);
        return like;

    }

    @Override
    public boolean isLiked(long tweetId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(()
        -> new TwitterException("User not found.",HttpStatus.NOT_FOUND));

        return likeRepository.findByUserIdAndTweetId(user.getId(),tweetId).isPresent();

    }
}
