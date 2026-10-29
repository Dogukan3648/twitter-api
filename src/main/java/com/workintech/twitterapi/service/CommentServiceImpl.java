
package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exception.TwitterException;
import com.workintech.twitterapi.repository.CommentRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import com.workintech.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              TweetRepository tweetRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() != null){
           throw new TwitterException("New comment ID must be null.", HttpStatus.BAD_REQUEST);
        }


        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TwitterException(
                        "User not found.",HttpStatus.NOT_FOUND
                ));

        Tweet tweet = tweetRepository.findById(comment.getTweet().getId())
                .orElseThrow(() -> new TwitterException(
                        "Tweet not found.",HttpStatus.NOT_FOUND
                ));

        comment.setUser(user);
        comment.setTweet(tweet);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Comment not found.",HttpStatus.NOT_FOUND));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!existingComment.getUser().getEmail().equals(email)){
            throw new TwitterException("Only the comment owner can update this comment.",HttpStatus.FORBIDDEN);
        }

        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment);
    }

    @Override
    public Comment delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Comment not found.",HttpStatus.NOT_FOUND));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isCommentOwner = comment.getUser().getEmail().equals(email);
        boolean isTweetOwner = comment.getTweet().getUser().getEmail().equals(email);

        if (!isCommentOwner && !isTweetOwner){
            throw new TwitterException("You cannot delete this comment.",HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(comment);
        return comment;
    }

    @Override
    public List<Comment> findByTweetId(Long tweetId) {
        return commentRepository.findByTweetId(tweetId);
    }
}
