import { useState } from "react";

const CommentForm = ({ tweetId, onSubmit }) => {
  const [commentContent, setCommentContent] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    const success = await onSubmit(tweetId, commentContent);

    if (success) {
      setCommentContent("");
    }
  };
  return (
    <form onSubmit={handleSubmit} className="mt-5 space-y-3">
      <textarea
        placeholder="Yorumunu yaz..."
        value={commentContent}
        onChange={(event) => setCommentContent(event.target.value)}
        maxLength={280}
        rows={2}
        required
        className="w-full resize-none rounded-lg border border-slate-700 bg-slate-800 p-3 text-white outline-none focus:border-sky-500"
      />

      <button
        type="submit"
        disabled={!commentContent.trim()}
        className="cursor-pointer rounded-lg bg-sky-500 px-4 py-2 text-sm font-semibold text-white hover:bg-sky-600
         disabled:cursor-not-allowed disabled:opacity-50"
      >
        Yorum yap
      </button>
    </form>
  );
};

export default CommentForm;
