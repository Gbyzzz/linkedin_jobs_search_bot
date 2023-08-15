CREATE TYPE bot_state AS ENUM ('NA', 'ADD_KEYWORDS', 'ADD_LOCATION', 'ADD_EXPERIENCE',
    'ADD_JOB_TYPE', 'ADD_WORKPLACE', 'ADD_FILTER_INCLUDE', 'ADD_FILTER_EXCLUDE', 'ADD_FILTER_JOB_TYPE',
    'ADD_FILTER_WORKPLACE', 'NEW', 'APPLIED', 'SEARCHES');
CREATE TYPE reply_state AS ENUM ('NEW_JOB', 'APPLIED', 'INTERVIEWING_IN_PROGRESS',
    'REJECTED', 'DELETED');
CREATE TYPE search_state AS ENUM ('NEW', 'SUBSCRIBED');

CREATE TABLE user_profiles
(
    chat_id       bigint                 NOT NULL PRIMARY KEY,
    username      varchar(255)           NOT NULL,
    bot_state     bot_state NOT NULL DEFAULT 'NA',
    registered_at timestamp              NOT NULL
);

INSERT INTO user_profiles
VALUES (346235582, 'Gbyzzz', 'NEW', '2023-07-29');

CREATE TABLE search_params
(
    search_params_id bigserial PRIMARY KEY,
    user_chat_id     bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    keywords         text                                                        NOT NULL,
    location         varchar(255)                                                NOT NULL,
    search_state     search_state                                                NOT NULL DEFAULT 'NEW'
);

-- INSERT INTO search_params
-- VALUES (1, 346235582, 'java', 'Israel', 'SUBSCRIBED');
-- INSERT INTO search_params
-- VALUES (2, 346235582, 'java', 'Haifa', 'SUBSCRIBED');
-- SELECT setval('search_params_search_params_id_seq',
--               (SELECT MAX(search_params_id) from "search_params"));

CREATE TABLE search_filters
(
    search_params_id bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    filter_name      varchar(255)                                                         NOT NULL,
    value            varchar(255)                                                         NOT NULL
);

-- INSERT INTO search_filters
-- VALUES (1, 'experience', '1,2'),
--        (1, 'jobType', 'F,P,C,T,I'),
--        (1, 'workplaceType', '1,2,3'),
--        (2, 'experience', '1,2'),
--        (2, 'jobType', 'F,P,C,T,I'),
--        (2, 'workplaceType', '1,2,3');

CREATE TABLE filter_params
(
    filter_params_id    bigserial PRIMARY KEY,
    search_params_id    bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    include_words_desc  text,
    exclude_words_title text
);
-- INSERT INTO filter_params
-- VALUES (1, 1, 'java,spring',
--         'devops,qa,performance,automation,full,sales,quality,support,android,administrator,' ||
--         'tester,testing,data,customer'),
--        (2, 2, 'java,spring',
--         'devops,qa,performance,automation,full,sales,quality,support,android,administrator,' ||
--         'tester,testing,data,customer');
-- SELECT setval('filter_params_filter_params_id_seq',
--               (SELECT MAX(filter_params_id) from "filter_params"));

CREATE TABLE saved_jobs
(
    job_id       bigint                       NOT NULL UNIQUE,
    reply_state  reply_state NOT NULL DEFAULT 'NEW_JOB',
    date_applied timestamp
);

-- INSERT INTO saved_jobs
-- VALUES (3480776663, 'APPLIED', '2023-06-08'),
--        (3620443007, 'APPLIED', '2023-06-08'),
--        (2765956789, 'APPLIED', '2023-06-08'),
--        (3632106449, 'APPLIED', '2023-06-08'),
--        (3640836991, 'APPLIED', '2023-06-21'),
--        (3635896374, 'REJECTED', '2023-06-21'),
--        (3640001670, 'APPLIED', '2023-06-21'),
--        (3623088971, 'APPLIED', '2023-06-21'),
--        (3532221278, 'APPLIED', '2023-06-21'),
--        (3638421335, 'APPLIED', '2023-06-21'),
--        (3616820456, 'APPLIED', '2023-06-21'),
--        (3559651873, 'APPLIED', '2023-06-21'),
--        (3605154284, 'APPLIED', '2023-06-21'),
--        (3639377244, 'REJECTED', '2023-06-23'),
--        (3567598441, 'APPLIED', '2023-06-23'),
--        (3629188748, 'APPLIED', '2023-06-23'),
--        (3654507617, 'APPLIED', '2023-07-06'),
--        (3658859545, 'APPLIED', '2023-07-09'),
--        (3602342291, 'APPLIED', '2023-07-09'),
--        (3631883487, 'APPLIED', '2023-07-09'),
--        (3599767845, 'APPLIED', '2023-07-09'),
--        (3623806369, 'APPLIED', '2023-07-09'),
--        (3656874635, 'APPLIED', '2023-07-09'),
--        (3485524081, 'APPLIED', '2023-07-09'),
--        (3604148166, 'APPLIED', '2023-07-09'),
--        (3594308547, 'APPLIED', '2023-07-09'),
--        (3655348920, 'APPLIED', '2023-07-09'),
--        (3641783860, 'APPLIED', '2023-07-09'),
--        (3641784687, 'APPLIED', '2023-07-09'),
--        (3659159877, 'APPLIED', '2023-07-09'),
--        (3659177252, 'APPLIED', '2023-07-09'),
--        (3659635232, 'APPLIED', '2023-07-10'),
--        (3486170323, 'APPLIED', '2023-07-11'),
--        (3612002805, 'APPLIED', '2023-07-11'),
--        (3628144740, 'APPLIED', '2023-07-20'),
--        (3665284294, 'APPLIED', '2023-07-20'),
--        (3666459067, 'APPLIED', '2023-07-20'),
--        (3664977942, 'APPLIED', '2023-07-20'),
--        (3662613537, 'APPLIED', '2023-07-20'),
--        (3664497387, 'APPLIED', '2023-07-20'),
--        (3675904158, 'APPLIED', '2023-07-20'),
--        (3676698937, 'APPLIED', '2023-07-20'),
--        (3651370729, 'APPLIED', '2023-08-02'),
--        (3678936459, 'APPLIED', '2023-08-02'),
--        (3667911345, 'APPLIED', '2023-08-02'),
--        (3671563689, 'APPLIED', '2023-08-02');

CREATE TABLE users_jobs
(
    job_id       bigint REFERENCES saved_jobs (job_id) ON DELETE CASCADE     NOT NULL,
    user_chat_id bigint REFERENCES user_profiles (chat_id) ON DELETE CASCADE NOT NULL,
    UNIQUE (job_id, user_chat_id)
);

-- INSERT INTO users_jobs
-- VALUES (3480776663, 346235582),
--        (3620443007, 346235582),
--        (2765956789, 346235582),
--        (3632106449, 346235582),
--        (3640836991, 346235582),
--        (3635896374, 346235582),
--        (3640001670, 346235582),
--        (3623088971, 346235582),
--        (3532221278, 346235582),
--        (3638421335, 346235582),
--        (3616820456, 346235582),
--        (3559651873, 346235582),
--        (3605154284, 346235582),
--        (3639377244, 346235582),
--        (3567598441, 346235582),
--        (3629188748, 346235582),
--        (3654507617, 346235582),
--        (3658859545, 346235582),
--        (3602342291, 346235582),
--        (3631883487, 346235582),
--        (3599767845, 346235582),
--        (3623806369, 346235582),
--        (3656874635, 346235582),
--        (3485524081, 346235582),
--        (3604148166, 346235582),
--        (3594308547, 346235582),
--        (3655348920, 346235582),
--        (3641783860, 346235582),
--        (3641784687, 346235582),
--        (3659159877, 346235582),
--        (3659177252, 346235582),
--        (3659635232, 346235582),
--        (3486170323, 346235582),
--        (3612002805, 346235582),
--        (3628144740, 346235582),
--        (3665284294, 346235582),
--        (3666459067, 346235582),
--        (3664977942, 346235582),
--        (3662613537, 346235582),
--        (3664497387, 346235582),
--        (3675904158, 346235582),
--        (3676698937, 346235582),
--        (3651370729, 346235582),
--        (3678936459, 346235582),
--        (3667911345, 346235582),
--        (3671563689, 346235582);


CREATE TABLE searches_jobs
(
    job_id           bigint REFERENCES saved_jobs (job_id) ON DELETE CASCADE              NOT NULL,
    search_params_id bigint REFERENCES search_params (search_params_id) ON DELETE CASCADE NOT NULL,
    UNIQUE (job_id, search_params_id)
);

-- INSERT INTO searches_jobs
-- VALUES (3480776663, 1),
--        (3620443007, 1),
--        (2765956789, 1),
--        (3632106449, 1),
--        (3640836991, 1),
--        (3635896374, 1),
--        (3640001670, 1),
--        (3623088971, 1),
--        (3532221278, 1),
--        (3638421335, 1),
--        (3616820456, 1),
--        (3559651873, 1),
--        (3605154284, 1),
--        (3639377244, 1),
--        (3567598441, 1),
--        (3629188748, 1),
--        (3654507617, 1),
--        (3658859545, 1),
--        (3602342291, 1),
--        (3631883487, 1),
--        (3599767845, 1),
--        (3623806369, 1),
--        (3656874635, 1),
--        (3485524081, 1),
--        (3604148166, 1),
--        (3594308547, 1),
--        (3655348920, 1),
--        (3641783860, 1),
--        (3641784687, 1),
--        (3659159877, 1),
--        (3659177252, 1),
--        (3659635232, 1),
--        (3486170323, 1),
--        (3612002805, 1),
--        (3628144740, 1),
--        (3665284294, 1),
--        (3666459067, 1),
--        (3664977942, 1),
--        (3662613537, 1),
--        (3664497387, 1),
--        (3675904158, 1),
--        (3676698937, 1),
--        (3651370729, 1),
--        (3678936459, 1),
--        (3667911345, 1),
--        (3671563689, 1);
