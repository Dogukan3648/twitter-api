function TweetSearchForm({
  userId,
  setUserId,
  email,
  setEmail,
  password,
  setPassword,
  onSubmit,
}) {
  return (
    <form onSubmit={onSubmit}>
      <input
        type="number"
        placeholder="User ID"
        value={userId}
        onChange={(event) => setUserId(event.target.value)}
        required
      />

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(event) => setEmail(event.target.value)}
        required
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(event) => setPassword(event.target.value)}
        required
      />

      <button type="submit">Tweetleri Getir</button>
    </form>
  );
}
export default TweetSearchForm;
