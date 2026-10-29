const TweetSearchForm = ({ userId, setUserId, onSubmit }) => {
  return (
    <form
      onSubmit={onSubmit}
      className="mx-auto w-full max-w-lg space-y-4 rounded-2xl border border-slate-800 bg-slate-900 p-6"
    >
      <h2 className="text-xl font-bold text-white">Tweetleri Keşfet</h2>

      <input
        type="number"
        min="1"
        placeholder="Kullanıcı ID"
        value={userId}
        onChange={(event) => setUserId(event.target.value)}
        className="w-full rounded-lg border border-slate-700 bg-slate-800 p-3 text-white outline-none focus:border-sky-500"
        required
      />

      <button
        type="submit"
        className="w-full cursor-pointer rounded-lg bg-sky-500 p-3 font-semibold text-white hover:bg-sky-600"
      >
        Tweetleri Getir
      </button>
    </form>
  );
};

export default TweetSearchForm;
