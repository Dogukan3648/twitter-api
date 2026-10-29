import { useState } from "react";

const CommentItem = ({ comment, onUpdate, onDelete }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState(comment.content);

  const handleSave = async () => {
    const success = await onUpdate(comment.id, content);

    if (success) {
      setIsEditing(false);
    }
  };

  return (
    <div className="mt-3 rounded-lg border border-slate-700 bg-slate-800 p-3">
      {isEditing ? (
        <textarea
          value={content}
          onChange={(event) => setContent(event.target.value)}
          maxLength={280}
          rows={2}
          className="w-full rounded-lg border border-slate-600 bg-slate-900 p-3 text-white outline-none focus:border-sky-500"
        />
      ) : (
        <p className="mb-3 text-sm text-slate-200">{comment.content}</p>
      )}

      <div className="mt-3 flex gap-2">
        {isEditing ? (
          <>
            <button
              type="button"
              onClick={handleSave}
              disabled={!content.trim()}
              className="cursor-pointer rounded-lg bg-green-500 px-3 py-2 text-sm text-white disabled:opacity-50"
            >
              Kaydet
            </button>

            <button
              type="button"
              onClick={() => {
                setContent(comment.content);
                setIsEditing(false);
              }}
              className="cursor-pointer rounded-lg bg-slate-600 px-3 py-2 text-sm text-white"
            >
              İptal
            </button>
          </>
        ) : (
          <>
            <button
              type="button"
              onClick={() => setIsEditing(true)}
              className="cursor-pointer rounded-lg bg-sky-500 px-3 py-2 text-sm text-white"
            >
              Düzenle
            </button>

            <button
              type="button"
              onClick={() => onDelete(comment.id)}
              className="cursor-pointer rounded-lg bg-red-500 px-3 py-2 text-sm text-white"
            >
              Sil
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default CommentItem;
