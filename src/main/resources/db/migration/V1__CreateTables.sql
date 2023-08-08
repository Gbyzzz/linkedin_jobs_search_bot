CREATE TYPE linkedin_job_bot_state AS ENUM ('NA', 'ADD_KEYWORDS', 'ADD_LOCATION', 'ADD_EXPERIENCE',
    'ADD_JOB_TYPE', 'ADD_WORKPLACE', 'ADD_FILTER_INCLUDE', 'ADD_FILTER_EXCLUDE', 'ADD_FILTER_JOB_TYPE',
    'ADD_FILTER_WORKPLACE', 'NEW', 'APPLIED', 'SEARCHES');
CREATE TYPE linkedin_job_bot_reply_state AS ENUM ('NEW_JOB', 'APPLIED', 'INTERVIEWING_IN_PROGRESS',
    'REJECTED', 'DELETED');
CREATE TYPE linkedin_job_bot_filter_name AS ENUM ('EXPERIENCE', 'JOBTYPE', 'WORKPLACETYPE',
    'POSTEDRANGE', 'DISTANCE', 'SORTBY');

CREATE TABLE user_profiles
(
    chat_id       bigint                 NOT NULL PRIMARY KEY,
    username      varchar(255)           NOT NULL,
    bot_state     linkedin_job_bot_state NOT NULL DEFAULT 'NA',
    registered_at timestamp              NOT NULL
);


CREATE TABLE job_user
(
    job_id       bigint                                                      NOT NULL,
    user_chat_id bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    reply_state  linkedin_job_bot_reply_state                                NOT NULL DEFAULT 'APPLIED',
    date_applied timestamp,
    UNIQUE (user_chat_id, job_id)
);

CREATE TABLE search_params
(
    id           bigserial PRIMARY KEY,
    user_chat_id bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    keywords     text                                                        NOT NULL,
    location     varchar(255)                                                NOT NULL
);

CREATE TABLE search_filters
(
    search_id   bigint REFERENCES search_params (id) ON DELETE CASCADE NOT NULL,
    filter_name varchar(255)                                           NOT NULL,
    value       varchar(255)                                           NOT NULL
);


CREATE TABLE filter_params
(
    id        bigserial PRIMARY KEY,
    search_id bigint REFERENCES search_params (id) ON DELETE CASCADE NOT NULL,
    include   text,
    exclude   text
);
