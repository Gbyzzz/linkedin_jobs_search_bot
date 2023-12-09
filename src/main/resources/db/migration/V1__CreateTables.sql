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

INSERT INTO user_profiles
VALUES (5024120144, 'evgenii_borozna', 'ADD_KEYWORDS', '08/21/2023 3:06:40 PM'),
       (1316219991, 'rndmgnrtdnmd', 'NEW', '12/06/2023 9:39:12 AM'),
       (346235582, 'Gbyzzz', 'SEARCHES', '08/17/2023 4:30:07 PM');

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
    id bigserial PRIMARY KEY,
    job_ref_id       bigint                                           NOT NULL,
    user_chat_id bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    reply_state  reply_state                                                 NOT NULL DEFAULT 'NEW_JOB',
    date_applied timestamp,
    UNIQUE (job_ref_id, user_chat_id)
);

CREATE TABLE searches_jobs
(
    job_id           bigint REFERENCES saved_jobs (id) ON DELETE CASCADE              NOT NULL,
    search_params_id bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    UNIQUE (job_id, search_params_id)
);