
package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.CommentRepository;
import com.workintech.twitterapi.repository.TweetRepository;
import com.workintech.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow();

        Tweet tweet = tweetRepository.findById(comment.getTweet().getId())
                .orElseThrow();

        comment.setUser(user);
        comment.setTweet(tweet);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow();

        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment);
    }

    @Override
    public Comment delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow();

        commentRepository.delete(comment);
        return comment;
    }
}
