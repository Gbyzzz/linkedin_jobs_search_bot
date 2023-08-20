CREATE TYPE bot_state AS ENUM ('NA', 'ADD_KEYWORDS', 'ADD_LOCATION', 'ADD_EXPERIENCE',
    'ADD_JOB_TYPE', 'ADD_WORKPLACE', 'ADD_FILTER_INCLUDE', 'ADD_FILTER_EXCLUDE', 'ADD_FILTER_JOB_TYPE',
    'ADD_FILTER_WORKPLACE', 'NEW', 'APPLIED', 'SEARCHES');
CREATE TYPE reply_state AS ENUM ('NEW_JOB', 'APPLIED', 'INTERVIEWING_IN_PROGRESS',
    'REJECTED', 'DELETED');
CREATE TYPE search_state AS ENUM ('NEW', 'SUBSCRIBED');

CREATE TABLE user_profiles
(
    chat_id       bigint       NOT NULL PRIMARY KEY,
    username      varchar(255) NOT NULL,
    bot_state     bot_state    NOT NULL DEFAULT 'NA',
    registered_at timestamp    NOT NULL
);

CREATE TABLE search_params
(
    search_params_id bigserial PRIMARY KEY,
    user_chat_id     bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    keywords         text                                                        NOT NULL,
    location         varchar(255)                                                NOT NULL,
    search_state     search_state                                                NOT NULL DEFAULT 'NEW'
);

CREATE TABLE search_filters
(
    search_params_id bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    filter_name      varchar(255)                                                         NOT NULL,
    value            varchar(255)                                                         NOT NULL
);

CREATE TABLE filter_params
(
    filter_params_id    bigserial PRIMARY KEY,
    search_params_id    bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    include_words_desc  text,
    exclude_words_title text
);

CREATE TABLE saved_jobs
(
    job_id       bigint PRIMARY KEY NOT NULL,
    reply_state  reply_state        NOT NULL DEFAULT 'NEW_JOB',
    date_applied timestamp
);

CREATE TABLE users_jobs
(
    job_id       bigint REFERENCES saved_jobs (job_id) ON DELETE CASCADE     NOT NULL,
    user_chat_id bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    UNIQUE (job_id, user_chat_id)
);

CREATE TABLE searches_jobs
(
    job_id           bigint REFERENCES saved_jobs (job_id) ON DELETE CASCADE              NOT NULL,
    search_params_id bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    UNIQUE (job_id, search_params_id)
);
