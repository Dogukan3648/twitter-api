import axios from "axios";
import { useState } from "react";
import TweetList from "./components/TweetList";
import TweetSearchForm from "./components/TweetSearchForm";

const App = () => {
  const [userId, setUserId] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [tweets, setTweets] = useState([]);
  const [error, setError] = useState("");

  const getTweets = async (event) => {
    event.preventDefault();
    setError("");
    setTweets([]);

    try {
      const response = await axios.get(
        `http://localhost:3000/tweet/findByUserId/${userId}`,
        {
          auth: {
            username: email,
            password: password,
          },
        },
      );

      setTweets(response.data);
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div>
      <h3>Twitter API</h3>

      <TweetSearchForm
        userId={userId}
        setUserId={setUserId}
        email={email}
        setEmail={setEmail}
        password={password}
        setPassword={setPassword}
        onSubmit={getTweets}
      />
      {error && <p>Hata: {error}</p>}

      <TweetList tweets={tweets} />
    </div>
  );
};

export default App;
