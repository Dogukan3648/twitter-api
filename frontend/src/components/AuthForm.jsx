import { useState } from "react";

const AuthForm = ({ onSubmit }) => {
  const [mode, setMode] = useState("login");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    onSubmit({
      mode,
      firstName,
      lastName,
      email,
      password,
    });
  };

  return (
    <div className="mx-auto w-full max-w-md rounded-2xl border border-slate-800 bg-slate-900 p-8">
      <h2 className="mb-6 text-center text-2xl font-bold text-white">
        {mode === "login" ? "Giriş Yap" : "Kayıt Ol"}
      </h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        {mode === "register" && (
          <>
            <input
              type="text"
              placeholder="Ad"
              value={firstName}
              onChange={(event) => setFirstName(event.target.value)}
              maxLength={40}
              required
              className="w-full rounded-lg border border-slate-700 bg-slate-800 p-3 text-white"
            />

            <input
              type="text"
              placeholder="Soyad"
              value={lastName}
              onChange={(event) => setLastName(event.target.value)}
              maxLength={40}
              required
              className="w-full rounded-lg border border-slate-700 bg-slate-800 p-3 text-white"
            />
          </>
        )}

        <input
          type="email"
          placeholder="E-posta"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
          required
          className="w-full rounded-lg border border-slate-700 bg-slate-800 p-3 text-white"
        />

        <input
          type="password"
          placeholder="Şifre"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
          className="w-full rounded-lg border border-slate-700 bg-slate-800 p-3 text-white"
        />

        <button
          type="submit"
          className="w-full cursor-pointer rounded-lg bg-sky-500 p-3 font-semibold text-white hover:bg-sky-600"
        >
          {mode === "login" ? "Giriş Yap" : "Kayıt Ol"}
        </button>
      </form>

      <button
        type="button"
        onClick={() => setMode(mode === "login" ? "register" : "login")}
        className="mt-5 w-full cursor-pointer text-center text-sm text-sky-400 hover:underline"
      >
        {mode === "login"
          ? "Hesabın yok mu? Kayıt Ol"
          : "Zaten hesabın var mı? Giriş Yap"}
      </button>
    </div>
  );
};

export default AuthForm;
