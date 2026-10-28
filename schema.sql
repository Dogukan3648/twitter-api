--
-- PostgreSQL database dump
--

\restrict R5xSFxnILzyEJT3MBIm35NvfQef3dp47JyzhruQJ0JCbRDnj8UeejkGvu8vssOs

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

-- Started on 2026-10-29 01:48:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 224 (class 1259 OID 16944)
-- Name: comments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.comments (
    id bigint NOT NULL,
    content character varying(280) NOT NULL,
    user_id bigint NOT NULL,
    tweet_id bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 16943)
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.comments ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 226 (class 1259 OID 16956)
-- Name: likes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.likes (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    tweet_id bigint NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16955)
-- Name: likes_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.likes ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.likes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 228 (class 1259 OID 16967)
-- Name: retweets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.retweets (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    tweet_id bigint NOT NULL
);


--
-- TOC entry 227 (class 1259 OID 16966)
-- Name: retweets_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.retweets ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.retweets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 16933)
-- Name: tweets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tweets (
    id bigint NOT NULL,
    content character varying(280) NOT NULL,
    user_id bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16932)
-- Name: tweets_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.tweets ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tweets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 220 (class 1259 OID 16918)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(40) NOT NULL,
    last_name character varying(40) NOT NULL,
    password character varying(255) NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 16917)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 4885 (class 2606 OID 16954)
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 4887 (class 2606 OID 16963)
-- Name: likes likes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (id);


--
-- TOC entry 4889 (class 2606 OID 16965)
-- Name: likes likes_user_tweet_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_user_tweet_unique UNIQUE (user_id, tweet_id);


--
-- TOC entry 4891 (class 2606 OID 16974)
-- Name: retweets retweets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.retweets
    ADD CONSTRAINT retweets_pkey PRIMARY KEY (id);


--
-- TOC entry 4893 (class 2606 OID 16976)
-- Name: retweets retweets_user_tweet_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.retweets
    ADD CONSTRAINT retweets_user_tweet_unique UNIQUE (user_id, tweet_id);


--
-- TOC entry 4883 (class 2606 OID 16942)
-- Name: tweets tweets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tweets
    ADD CONSTRAINT tweets_pkey PRIMARY KEY (id);


--
-- TOC entry 4879 (class 2606 OID 16931)
-- Name: users users_email_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_unique UNIQUE (email);


--
-- TOC entry 4881 (class 2606 OID 16929)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4895 (class 2606 OID 16987)
-- Name: comments comments_tweet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_tweet_id_fkey FOREIGN KEY (tweet_id) REFERENCES public.tweets(id) NOT VALID;


--
-- TOC entry 4896 (class 2606 OID 16982)
-- Name: comments comments_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- TOC entry 4897 (class 2606 OID 16997)
-- Name: likes likes_tweet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_tweet_id_fkey FOREIGN KEY (tweet_id) REFERENCES public.tweets(id) NOT VALID;


--
-- TOC entry 4898 (class 2606 OID 16992)
-- Name: likes likes_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- TOC entry 4899 (class 2606 OID 17007)
-- Name: retweets retweets_tweet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.retweets
    ADD CONSTRAINT retweets_tweet_id_fkey FOREIGN KEY (tweet_id) REFERENCES public.tweets(id) NOT VALID;


--
-- TOC entry 4900 (class 2606 OID 17002)
-- Name: retweets retweets_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.retweets
    ADD CONSTRAINT retweets_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- TOC entry 4894 (class 2606 OID 16977)
-- Name: tweets tweets_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tweets
    ADD CONSTRAINT tweets_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


-- Completed on 2026-10-29 01:48:45

--
-- PostgreSQL database dump complete
--

\unrestrict R5xSFxnILzyEJT3MBIm35NvfQef3dp47JyzhruQJ0JCbRDnj8UeejkGvu8vssOs

