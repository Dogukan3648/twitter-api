import { Heart, Repeat2 } from "lucide-react";
import { useState } from "react";
import CommentForm from "./CommentForm";
import CommentItem from "./CommentItem";

const TweetList = ({
  tweets,
  comments,
  likedTweets,
  setLikedTweets,
  onDelete,
  onUpdate,
  onComment,
  onUpdateComment,
  onDeleteComment,
  onLike,
  onUnlike,
  retweets,
  onRetweet,
  onDeleteRetweet,
}) => {
  const [editingId, setEditingId] = useState(null);
  const [editContent, setEditContent] = useState("");

  const handleEdit = (tweet) => {
    setEditingId(tweet.id);
    setEditContent(tweet.content);
  };

  const handleSave = async (tweetId) => {
    const success = await onUpdate(tweetId, editContent);

    if (success) {
      setEditingId(null);
      setEditContent("");
    }
  };

  const handleLikeToggle = async (tweetId) => {
    const isLiked = likedTweets[tweetId] === true;

    const success = isLiked ? await onUnlike(tweetId) : await onLike(tweetId);

    if (success) {
      setLikedTweets((prev) => ({
        ...prev,
        [tweetId]: !isLiked,
      }));
    }
  };

  const handleRetweetToggle = async (tweetId) => {
    const retweetId = retweets[tweetId];

    if (retweetId != null) {
      await onDeleteRetweet(tweetId, retweetId);
    } else {
      await onRetweet(tweetId);
    }
  };

  return (
    <div className="mx-auto w-full max-w-lg space-y-4">
      {tweets.map((tweet) => (
        <div
          key={tweet.id}
          className="rounded-2xl border border-slate-800 bg-slate-900 p-6"
        >
          <h3 className="mb-3 font-bold text-sky-400">Tweet #{tweet.id}</h3>

          {editingId === tweet.id ? (
            <textarea
              value={editContent}
              onChange={(event) => setEditContent(event.target.value)}
              maxLength={280}
              rows={3}
              className="mb-4 w-full rounded-lg border border-slate-700 bg-slate-800 p-3 text-white outline-none focus:border-sky-500"
            />
          ) : (
            <p className="mb-5 whitespace-pre-wrap break-words text-slate-200">
              {tweet.content}
            </p>
          )}

          <div className="flex gap-3">
            {editingId === tweet.id ? (
              <>
                <button
                  type="button"
                  onClick={() => handleSave(tweet.id)}
                  disabled={!editContent.trim()}
                  className="cursor-pointer rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white hover:bg-green-600 disabled:opacity-50"
                >
                  Kaydet
                </button>

                <button
                  type="button"
                  onClick={() => setEditingId(null)}
                  className="cursor-pointer rounded-lg bg-slate-600 px-4 py-2 text-sm font-semibold text-white hover:bg-slate-500"
                >
                  İptal
                </button>
              </>
            ) : (
              <>
                <button
                  type="button"
                  onClick={() => handleEdit(tweet)}
                  className="cursor-pointer rounded-lg bg-sky-500 px-4 py-2 text-sm font-semibold text-white hover:bg-sky-600"
                >
                  Düzenle
                </button>

                <button
                  type="button"
                  onClick={() => onDelete(tweet.id)}
                  className="cursor-pointer rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white hover:bg-red-600"
                >
                  Tweet Sil
                </button>

                <button
                  type="button"
                  onClick={() => handleLikeToggle(tweet.id)}
                  aria-label={
                    likedTweets[tweet.id] ? "Beğeniyi kaldır" : "Beğen"
                  }
                  title={likedTweets[tweet.id] ? "Beğeniyi kaldır" : "Beğen"}
                  className="cursor-pointer rounded-full p-2 text-slate-400 transition-colors hover:bg-pink-500/10 hover:text-pink-500"
                >
                  <Heart
                    size={22}
                    className={
                      likedTweets[tweet.id] ? "fill-pink-500 text-pink-500" : ""
                    }
                  />
                </button>
                <button
                  type="button"
                  onClick={() => handleRetweetToggle(tweet.id)}
                  aria-label={
                    retweets[tweet.id] != null ? "Retweet kaldır" : "Retweet"
                  }
                  title={
                    retweets[tweet.id] != null ? "Retweet kaldır" : "Retweet"
                  }
                  className="cursor-pointer rounded-full p-2 text-slate-400 transition-colors hover:bg-green-500/10 hover:text-green-500"
                >
                  <Repeat2
                    size={22}
                    className={
                      retweets[tweet.id] != null ? "text-green-500" : ""
                    }
                  />
                </button>
              </>
            )}
          </div>

          <CommentForm tweetId={tweet.id} onSubmit={onComment} />

          {(comments[tweet.id] || []).map((comment) => (
            <CommentItem
              key={comment.id}
              comment={comment}
              onUpdate={onUpdateComment}
              onDelete={onDeleteComment}
            />
          ))}
        </div>
      ))}
    </div>
  );
};

export default TweetList;
