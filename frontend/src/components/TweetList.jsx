const TweetList = ({ tweets }) => {
  return (
    <div>
      {tweets.map((tweet) => (
        <div key={tweet.id}>
          <h3>Tweet #{tweet.id}</h3>
          <p>{tweet.content}</p>
        </div>
      ))}
    </div>
  );
};

export default TweetList;
