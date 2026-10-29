import axios from "axios";
import { useState } from "react";
import AuthForm from "./components/AuthForm";
import TweetCreateForm from "./components/TweetCreateForm";
import TweetList from "./components/TweetList";
import TweetSearchForm from "./components/TweetSearchForm";

const App = () => {
  const [userId, setUserId] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [tweets, setTweets] = useState([]);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [content, setContent] = useState("");
  const [comments, setComments] = useState({});
  const [likedTweets, setLikedTweets] = useState({});
  const [retweets, setRetweets] = useState({});

  const handleAuth = async ({ mode, firstName, lastName, email, password }) => {
    setError("");
    setMessage("");

    try {
      if (mode === "register") {
        await axios.post("http://localhost:3000/register", {
          firstName,
          lastName,
          email,
          password,
        });

        setMessage("Kayıt başarılı! Şimdi giriş yapabilirsin.");
      } else {
        await axios.post("http://localhost:3000/login", {
          email,
          password,
        });

        setEmail(email);
        setPassword(password);
        setIsAuthenticated(true);
      }
    } catch (error) {
      setError(error.response?.data?.message || error.message);
    }
  };

  const createTweet = async (event) => {
    event.preventDefault();
    setError("");
    setMessage("");

    try {
      await axios.post(
        "http://localhost:3000/tweet",
        { content },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setContent("");
      setMessage("Tweet başarıyla paylaşıldı!");
    } catch (error) {
      setError(error.response?.data?.message || error.message);
    }
  };

  const getTweets = async (event) => {
    event.preventDefault();
    setError("");
    setTweets([]);
    setComments({});
    setLikedTweets({});
    setRetweets({});

    try {
      const auth = {
        username: email,
        password: password,
      };

      const response = await axios.get(
        `http://localhost:3000/tweet/findByUserId/${userId}`,
        { auth },
      );

      const tweetList = response.data;

      const commentResults = await Promise.all(
        tweetList.map(async (tweet) => {
          const commentResponse = await axios.get(
            `http://localhost:3000/comment/tweet/${tweet.id}`,
            { auth },
          );

          return [tweet.id, commentResponse.data];
        }),
      );

      const likeResults = await Promise.all(
        tweetList.map(async (tweet) => {
          const likeResponse = await axios.get(
            `http://localhost:3000/like/status/${tweet.id}`,
            { auth },
          );

          return [tweet.id, likeResponse.data];
        }),
      );

      const retweetResults = await Promise.all(
        tweetList.map(async (tweet) => {
          const retweetResponse = await axios.get(
            `http://localhost:3000/retweet/tweet/${tweet.id}`,
            { auth },
          );

          return [
            tweet.id,
            retweetResponse.status === 200 ? retweetResponse.data.id : null,
          ];
        }),
      );

      setTweets(tweetList);
      setComments(Object.fromEntries(commentResults));
      setLikedTweets(Object.fromEntries(likeResults));
      setRetweets(Object.fromEntries(retweetResults));
    } catch (error) {
      setError(error.response?.data?.message || error.message);
    }
  };

  const deleteTweet = async (tweetId) => {
    setError("");
    setMessage("");

    try {
      await axios.delete(`http://localhost:3000/tweet/${tweetId}`, {
        auth: {
          username: email,
          password: password,
        },
      });

      setTweets((prevTweets) =>
        prevTweets.filter((tweet) => tweet.id !== tweetId),
      );

      setMessage("Tweet başarıyla silindi");
    } catch (error) {
      setError(error.response?.data?.message || error.message);
    }
  };

  const updateTweet = async (tweetId, updatedContent) => {
    setError("");
    setMessage("");

    try {
      const response = await axios.put(
        `http://localhost:3000/tweet/${tweetId}`,
        { content: updatedContent },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setTweets((prevTweets) =>
        prevTweets.map((tweet) =>
          tweet.id === tweetId ? response.data : tweet,
        ),
      );

      setMessage("Tweet başarıyla güncellendi!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  const createComment = async (tweetId, commentContent) => {
    setError("");
    setMessage("");

    try {
      const response = await axios.post(
        "http://localhost:3000/comment",
        {
          tweetId,
          content: commentContent,
        },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setComments((prevComments) => ({
        ...prevComments,
        [tweetId]: [...(prevComments[tweetId] || []), response.data],
      }));

      setMessage("Yorum başarıyla eklendi!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  const updateComment = async (commentId, updatedContent) => {
    setError("");
    setMessage("");

    try {
      await axios.put(
        `http://localhost:3000/comment/${commentId}`,
        {
          content: updatedContent,
        },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setComments((prevComments) =>
        Object.fromEntries(
          Object.entries(prevComments).map(([tweetId, tweetComments]) => [
            tweetId,
            tweetComments.map((comment) =>
              comment.id === commentId
                ? { ...comment, content: updatedContent }
                : comment,
            ),
          ]),
        ),
      );

      setMessage("Yorum başarıyla güncellendi!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  const deleteComment = async (commentId) => {
    setError("");
    setMessage("");

    try {
      await axios.delete(`http://localhost:3000/comment/${commentId}`, {
        auth: {
          username: email,
          password: password,
        },
      });

      setComments((prevComments) =>
        Object.fromEntries(
          Object.entries(prevComments).map(([tweetId, tweetComments]) => [
            tweetId,
            tweetComments.filter((comment) => comment.id !== commentId),
          ]),
        ),
      );

      setMessage("Yorum başarıyla silindi!");
    } catch (error) {
      setError(error.response?.data?.message || error.message);
    }
  };

  const likeTweet = async (tweetId) => {
    setError("");
    setMessage("");

    try {
      await axios.post(
        "http://localhost:3000/like",
        { tweetId },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setMessage("Tweet beğenildi!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  const unlikeTweet = async (tweetId) => {
    setError("");
    setMessage("");

    try {
      await axios.post(
        "http://localhost:3000/dislike",
        { tweetId },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setMessage("Beğeni kaldırıldı!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  const createRetweet = async (tweetId) => {
    setError("");
    setMessage("");

    try {
      const response = await axios.post(
        "http://localhost:3000/retweet",
        { tweetId },
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setRetweets((prev) => ({
        ...prev,
        [tweetId]: response.data.id,
      }));

      setMessage("Retweet başarıyla oluşturuldu!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  const deleteRetweet = async (tweetId, retweetId) => {
    setError("");
    setMessage("");

    try {
      await axios.delete(`http://localhost:3000/retweet/${retweetId}`, {
        auth: {
          username: email,
          password: password,
        },
      });

      setRetweets((prev) => ({
        ...prev,
        [tweetId]: null,
      }));

      setMessage("Retweet başarıyla kaldırıldı!");
      return true;
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      return false;
    }
  };

  return (
    <div className="min-h-screen bg-slate-950 px-4 py-10 text-slate-100">
      <div className="mx-auto max-w-4xl">
        <h1 className="mb-10 text-center text-4xl font-bold text-sky-500">
          Twitter API
        </h1>

        {!isAuthenticated ? (
          <AuthForm onSubmit={handleAuth} />
        ) : (
          <div className="space-y-8">
            <TweetCreateForm
              content={content}
              setContent={setContent}
              onSubmit={createTweet}
            />

            <TweetSearchForm
              userId={userId}
              setUserId={setUserId}
              onSubmit={getTweets}
            />

            <TweetList
              tweets={tweets}
              comments={comments}
              likedTweets={likedTweets}
              setLikedTweets={setLikedTweets}
              retweets={retweets}
              setRetweets={setRetweets}
              onDelete={deleteTweet}
              onUpdate={updateTweet}
              onComment={createComment}
              onUpdateComment={updateComment}
              onDeleteComment={deleteComment}
              onLike={likeTweet}
              onUnlike={unlikeTweet}
              onRetweet={createRetweet}
              onDeleteRetweet={deleteRetweet}
            />
          </div>
        )}

        {message && (
          <p className="mt-6 text-center text-green-400">{message}</p>
        )}

        {error && (
          <p className="mt-6 text-center text-red-400">Hata: {error}</p>
        )}
      </div>
    </div>
  );
};

export default App;
