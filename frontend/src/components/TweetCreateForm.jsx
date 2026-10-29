const TweetCreateForm = ({ content, setContent, onSubmit }) => {
  return (
    <form
      onSubmit={onSubmit}
      className="mx-auto w-full max-w-lg space-y-4 rounded-2xl border border-slate-800 bg-slate-900 p-6"
    >
      <h2 className="text-xl font-bold text-white">Yeni Tweet</h2>

      <textarea
        placeholder="Neler Oluyor?"
        value={content}
        onChange={(event) => setContent(event.target.value)}
        maxLength={280}
        required
        rows={4}
        className="w-full resize-none rounded-lg border border-slate-700 bg-slate-800 p-3 text-white outline-none focus:border-sky-500"
      />

      <button
        type="submit"
        className="w-full cursor-pointer rounded-lg bg-sky-500 p-3 font-semibold text-white hover:bg-sky-600"
      >
        Tweet Paylaş
      </button>
    </form>
  );
};

export default TweetCreateForm;
