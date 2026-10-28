
package com.workintech.twitterapi;

import com.workintech.twitterapi.dto.LoginDTO;
import com.workintech.twitterapi.entity.*;
import com.workintech.twitterapi.exception.TwitterException;
import com.workintech.twitterapi.repository.*;
import com.workintech.twitterapi.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TwitterApiServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private RetweetRepository retweetRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private TweetServiceImpl tweetService;

    @InjectMocks
    private CommentServiceImpl commentService;

    @InjectMocks
    private LikeServiceImpl likeService;

    @InjectMocks
    private RetweetServiceImpl retweetService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private void authenticate(String email) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, "password")
        );
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("1 - Kullanıcı başarıyla kaydedilmeli")
    void registerTest() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Test12345");

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("Test12345"))
                .thenReturn("encodedPassword");

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.register(user);

        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("2 - Tweet bulunamadığında 404 hatası verilmeli")
    void findTweetByIdTest() {
        when(tweetRepository.findById(99L))
                .thenReturn(Optional.empty());

        TwitterException exception = assertThrows(
                TwitterException.class,
                () -> tweetService.findById(99L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    @DisplayName("3 - Başka kullanıcı Tweet güncelleyememeli")
    void updateTweetTest() {
        authenticate("other@example.com");

        User owner = new User();
        owner.setEmail("owner@example.com");

        Tweet existingTweet = new Tweet();
        existingTweet.setId(1L);
        existingTweet.setUser(owner);

        when(tweetRepository.findById(1L))
                .thenReturn(Optional.of(existingTweet));

        Tweet updatedTweet = new Tweet();
        updatedTweet.setContent("Updated tweet");

        TwitterException exception = assertThrows(
                TwitterException.class,
                () -> tweetService.update(1L, updatedTweet)
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
        verify(tweetRepository, never()).save(any(Tweet.class));
    }

    @Test
    @DisplayName("4 - Tweet başarıyla silinmeli")
    void deleteTweetTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setUser(user);

        when(tweetRepository.findById(1L))
                .thenReturn(Optional.of(tweet));

        Tweet result = tweetService.delete(1L);

        assertEquals(1L, result.getId());
        verify(commentRepository).deleteByTweetId(1L);
        verify(likeRepository).deleteByTweetId(1L);
        verify(retweetRepository).deleteByTweetId(1L);
        verify(tweetRepository).delete(tweet);
    }

    @Test
    @DisplayName("5 - Yorum başarıyla oluşturulmalı")
    void saveCommentTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        Tweet tweet = new Tweet();
        tweet.setId(1L);

        Comment comment = new Comment();
        comment.setContent("Test comment");
        comment.setTweet(tweet);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(tweetRepository.findById(1L))
                .thenReturn(Optional.of(tweet));

        when(commentRepository.save(comment))
                .thenReturn(comment);

        Comment result = commentService.save(comment);

        assertEquals("Test comment", result.getContent());
        assertEquals(user, result.getUser());
        assertEquals(tweet, result.getTweet());
        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("6 - Yetkisiz kullanıcı yorumu silememeli")
    void deleteCommentTest() {
        authenticate("other@example.com");

        User commentOwner = new User();
        commentOwner.setEmail("commentowner@example.com");

        User tweetOwner = new User();
        tweetOwner.setEmail("tweetowner@example.com");

        Tweet tweet = new Tweet();
        tweet.setUser(tweetOwner);

        Comment comment = new Comment();
        comment.setUser(commentOwner);
        comment.setTweet(tweet);

        when(commentRepository.findById(1L))
                .thenReturn(Optional.of(comment));

        TwitterException exception = assertThrows(
                TwitterException.class,
                () -> commentService.delete(1L)
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
        verify(commentRepository, never()).delete(any(Comment.class));
    }

    @Test
    @DisplayName("7 - Aynı Tweet tekrar beğenilememeli")
    void saveLikeTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Tweet tweet = new Tweet();
        tweet.setId(1L);

        Like like = new Like();
        like.setTweet(tweet);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(tweetRepository.findById(1L))
                .thenReturn(Optional.of(tweet));

        when(likeRepository.findByUserIdAndTweetId(1L, 1L))
                .thenReturn(Optional.of(new Like()));

        TwitterException exception = assertThrows(
                TwitterException.class,
                () -> likeService.save(like)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    @DisplayName("8 - Aynı Tweet tekrar retweet edilememeli")
    void saveRetweetTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Tweet tweet = new Tweet();
        tweet.setId(1L);

        Retweet retweet = new Retweet();
        retweet.setTweet(tweet);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(tweetRepository.findById(1L))
                .thenReturn(Optional.of(tweet));

        when(retweetRepository.findByUserIdAndTweetId(1L, 1L))
                .thenReturn(Optional.of(new Retweet()));

        TwitterException exception = assertThrows(
                TwitterException.class,
                () -> retweetService.save(retweet)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        verify(retweetRepository, never()).save(any(Retweet.class));
    }

    @Test
    @DisplayName("9 - Tweet başarıyla oluşturulmalı")
    void saveTweetTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        Tweet tweet = new Tweet();
        tweet.setContent("Test tweet");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(tweetRepository.save(tweet))
                .thenReturn(tweet);

        Tweet result = tweetService.save(tweet);

        assertEquals("Test tweet", result.getContent());
        assertEquals(user, result.getUser());
        verify(tweetRepository).save(tweet);
    }

    @Test
    @DisplayName("10 - Kullanıcının Tweetleri listelenmeli")
    void findByUserIdTest() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Test tweet");

        when(tweetRepository.findByUserId(1L))
                .thenReturn(List.of(tweet));

        List<Tweet> result = tweetService.findByUserId(1L);

        assertEquals(1, result.size());
        assertEquals("Test tweet", result.get(0).getContent());
        verify(tweetRepository).findByUserId(1L);
    }

    @Test
    @DisplayName("11 - Yorum sahibi yorumunu güncelleyebilmeli")
    void updateCommentTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        Comment existingComment = new Comment();
        existingComment.setUser(user);
        existingComment.setContent("Old comment");

        Comment updatedComment = new Comment();
        updatedComment.setContent("Updated comment");

        when(commentRepository.findById(1L))
                .thenReturn(Optional.of(existingComment));

        when(commentRepository.save(existingComment))
                .thenReturn(existingComment);

        Comment result = commentService.update(1L, updatedComment);

        assertEquals("Updated comment", result.getContent());
        verify(commentRepository).save(existingComment);
    }

    @Test
    @DisplayName("12 - Kullanıcı beğenisini kaldırabilmeli")
    void dislikeTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Like like = new Like();
        like.setId(1L);

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        when(likeRepository.findByUserIdAndTweetId(1L, 2L))
                .thenReturn(Optional.of(like));

        Like result = likeService.dislike(2L);

        assertEquals(1L, result.getId());
        verify(likeRepository).delete(like);
    }

    @Test
    @DisplayName("13 - Retweet sahibi retweetini silebilmeli")
    void deleteRetweetTest() {
        authenticate("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        Retweet retweet = new Retweet();
        retweet.setId(1L);
        retweet.setUser(user);

        when(retweetRepository.findById(1L))
                .thenReturn(Optional.of(retweet));

        Retweet result = retweetService.delete(1L);

        assertEquals(1L, result.getId());
        verify(retweetRepository).delete(retweet);
    }

    @Test
    @DisplayName("14 - Hatalı girişte 401 hatası verilmeli")
    void loginTest() {
        LoginDTO loginDTO = new LoginDTO(
                "test@example.com",
                "wrongpassword"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        TwitterException exception = assertThrows(
                TwitterException.class,
                () -> authenticationService.login(loginDTO)
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
    }
}
