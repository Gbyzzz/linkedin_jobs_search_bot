CREATE TYPE bot_state AS ENUM ('NA', 'ADD_KEYWORDS', 'ADD_LOCATION', 'ADD_EXPERIENCE',
    'ADD_JOB_TYPE', 'ADD_WORKPLACE', 'ADD_FILTER_INCLUDE', 'ADD_FILTER_EXCLUDE', 'ADD_FILTER_JOB_TYPE',
    'ADD_FILTER_WORKPLACE', 'NEW', 'APPLIED', 'SEARCHES');
CREATE TYPE reply_state AS ENUM ('NEW_JOB', 'APPLIED', 'INTERVIEWING_IN_PROGRESS',
    'REJECTED', 'DELETED');

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
    location         varchar(255)                                                NOT NULL
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


INSERT INTO user_profiles
VALUES (1, 'admin', 'NA', '2024-07-29'),
       (2, 'ton', 'NA', '2024-07-29');

INSERT INTO search_params
VALUES (1, 1, 'java', 'Haifa'),
       (2, 1, 'java', 'Tel_Aviv'),
       (3, 2, 'C++', 'Haifa'),
       (4, 2, 'C++', 'Tel_Aviv');

INSERT INTO search_filters
VALUES (1, 'jobType', 'F,P,C,T,I'),
       (1, 'workplaceType', '1,2,3'),
       (2, 'jobType', 'F,P,C,T,I'),
       (2, 'workplaceType', '1,2,3'),
       (3, 'jobType', 'F,P,C,T,I'),
       (3, 'workplaceType', '1,2,3'),
       (4, 'jobType', 'F,P,C,T,I'),
       (4, 'workplaceType', '1,2,3');

INSERT INTO filter_params
VALUES (1, 1, 'java,spring', 'devops,qa,performance,automation,sales,quality,support,android,administrator,tester,testing,data,customer,lead'),
       (2, 2, 'java,spring', 'devops,qa,performance,automation,sales,quality,support,android,administrator,tester,testing,data,customer,lead'),
       (3, 3, 'C++', 'devops,qa,performance,automation,sales,quality,support,android,administrator,tester,testing,data,customer,lead'),
       (4, 4, 'C++', 'devops,qa,performance,automation,sales,quality,support,android,administrator,tester,testing,data,customer,lead');


insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (1, 1, 1, 'APPLIED', '12/5/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (2, 2, 1, 'APPLIED', '5/31/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (3, 3, 2, 'NEW_JOB', '10/22/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (4, 4, 2, 'NEW_JOB', '2/12/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (5, 5, 2, 'APPLIED', '7/9/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (6, 6, 1, 'NEW_JOB', '3/8/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (7, 7, 2, 'NEW_JOB', '7/27/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (8, 8, 2, 'APPLIED', '1/18/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (9, 9, 2, 'APPLIED', '12/1/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (10, 10, 1, 'NEW_JOB', '7/13/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (11, 11, 2, 'APPLIED', '1/3/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (12, 12, 2, 'NEW_JOB', '3/24/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (13, 13, 2, 'APPLIED', '5/2/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (14, 14, 2, 'NEW_JOB', '11/5/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (15, 15, 1, 'NEW_JOB', '9/20/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (16, 16, 2, 'APPLIED', '8/27/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (17, 17, 2, 'NEW_JOB', '10/14/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (18, 18, 2, 'NEW_JOB', '6/5/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (19, 19, 2, 'APPLIED', '11/29/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (20, 20, 2, 'NEW_JOB', '5/7/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (21, 21, 1, 'NEW_JOB', '11/29/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (22, 22, 2, 'APPLIED', '10/4/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (23, 23, 1, 'APPLIED', '8/25/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (24, 24, 1, 'APPLIED', '4/20/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (25, 25, 2, 'NEW_JOB', '10/20/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (26, 26, 1, 'APPLIED', '1/10/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (27, 27, 2, 'NEW_JOB', '11/13/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (28, 28, 1, 'NEW_JOB', '6/12/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (29, 29, 1, 'NEW_JOB', '10/7/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (30, 30, 1, 'APPLIED', '12/6/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (31, 31, 1, 'APPLIED', '2/27/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (32, 32, 1, 'APPLIED', '6/29/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (33, 33, 1, 'NEW_JOB', '12/10/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (34, 34, 1, 'NEW_JOB', '6/15/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (35, 35, 2, 'NEW_JOB', '5/15/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (36, 36, 1, 'NEW_JOB', '11/6/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (37, 37, 1, 'APPLIED', '4/17/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (38, 38, 1, 'APPLIED', '6/14/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (39, 39, 1, 'NEW_JOB', '7/18/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (40, 40, 1, 'NEW_JOB', '5/1/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (41, 41, 1, 'NEW_JOB', '1/20/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (42, 42, 2, 'NEW_JOB', '1/8/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (43, 43, 1, 'APPLIED', '3/31/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (44, 44, 1, 'NEW_JOB', '2/26/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (45, 45, 1, 'NEW_JOB', '9/21/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (46, 46, 1, 'APPLIED', '7/1/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (47, 47, 1, 'NEW_JOB', '5/20/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (48, 48, 1, 'NEW_JOB', '2/4/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (49, 49, 2, 'APPLIED', '2/3/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (50, 50, 1, 'NEW_JOB', '3/10/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (51, 51, 2, 'APPLIED', '5/8/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (52, 52, 2, 'NEW_JOB', '10/6/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (53, 53, 2, 'NEW_JOB', '1/2/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (54, 54, 2, 'APPLIED', '1/1/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (55, 55, 1, 'APPLIED', '5/28/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (56, 56, 2, 'APPLIED', '1/18/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (57, 57, 1, 'NEW_JOB', '12/20/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (58, 58, 1, 'NEW_JOB', '3/17/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (59, 59, 1, 'NEW_JOB', '7/30/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (60, 60, 1, 'APPLIED', '12/10/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (61, 61, 2, 'NEW_JOB', '5/4/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (62, 62, 1, 'NEW_JOB', '10/19/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (63, 63, 1, 'NEW_JOB', '2/18/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (64, 64, 1, 'NEW_JOB', '6/7/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (65, 65, 1, 'NEW_JOB', '2/10/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (66, 66, 1, 'NEW_JOB', '1/2/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (67, 67, 2, 'APPLIED', '5/29/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (68, 68, 1, 'APPLIED', '8/22/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (69, 69, 1, 'NEW_JOB', '1/9/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (70, 70, 1, 'APPLIED', '11/19/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (71, 71, 2, 'NEW_JOB', '6/30/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (72, 72, 1, 'NEW_JOB', '2/23/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (73, 73, 1, 'NEW_JOB', '4/5/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (74, 74, 2, 'APPLIED', '9/16/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (75, 75, 2, 'APPLIED', '6/21/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (76, 76, 1, 'APPLIED', '5/5/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (77, 77, 2, 'NEW_JOB', '9/26/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (78, 78, 2, 'APPLIED', '6/20/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (79, 79, 1, 'APPLIED', '2/24/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (80, 80, 2, 'NEW_JOB', '1/12/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (81, 81, 2, 'APPLIED', '7/20/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (82, 82, 1, 'APPLIED', '6/28/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (83, 83, 1, 'NEW_JOB', '11/12/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (84, 84, 2, 'APPLIED', '8/11/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (85, 85, 2, 'APPLIED', '4/4/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (86, 86, 1, 'APPLIED', '3/15/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (87, 87, 1, 'NEW_JOB', '6/19/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (88, 88, 1, 'NEW_JOB', '7/2/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (89, 89, 1, 'NEW_JOB', '8/11/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (90, 90, 1, 'APPLIED', '9/23/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (91, 91, 1, 'APPLIED', '10/11/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (92, 92, 1, 'APPLIED', '2/10/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (93, 93, 2, 'APPLIED', '2/13/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (94, 94, 1, 'NEW_JOB', '10/16/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (95, 95, 2, 'APPLIED', '11/6/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (96, 96, 2, 'APPLIED', '9/2/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (97, 97, 2, 'NEW_JOB', '6/19/2024');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (98, 98, 1, 'APPLIED', '9/6/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (99, 99, 1, 'APPLIED', '9/26/2023');
insert into saved_jobs (id, job_ref_id, user_chat_id, reply_state, date_applied) values (100, 100, 1, 'APPLIED', '3/21/2024');

insert into searches_jobs (job_id, search_params_id) values (1, 4);
insert into searches_jobs (job_id, search_params_id) values (2, 3);
insert into searches_jobs (job_id, search_params_id) values (3, 1);
insert into searches_jobs (job_id, search_params_id) values (4, 1);
insert into searches_jobs (job_id, search_params_id) values (5, 2);
insert into searches_jobs (job_id, search_params_id) values (6, 2);
insert into searches_jobs (job_id, search_params_id) values (7, 1);
insert into searches_jobs (job_id, search_params_id) values (8, 4);
insert into searches_jobs (job_id, search_params_id) values (9, 1);
insert into searches_jobs (job_id, search_params_id) values (10, 4);
insert into searches_jobs (job_id, search_params_id) values (11, 1);
insert into searches_jobs (job_id, search_params_id) values (12, 3);
insert into searches_jobs (job_id, search_params_id) values (13, 4);
insert into searches_jobs (job_id, search_params_id) values (14, 2);
insert into searches_jobs (job_id, search_params_id) values (15, 1);
insert into searches_jobs (job_id, search_params_id) values (16, 2);
insert into searches_jobs (job_id, search_params_id) values (17, 3);
insert into searches_jobs (job_id, search_params_id) values (18, 3);
insert into searches_jobs (job_id, search_params_id) values (19, 1);
insert into searches_jobs (job_id, search_params_id) values (20, 2);
insert into searches_jobs (job_id, search_params_id) values (21, 1);
insert into searches_jobs (job_id, search_params_id) values (22, 1);
insert into searches_jobs (job_id, search_params_id) values (23, 4);
insert into searches_jobs (job_id, search_params_id) values (24, 4);
insert into searches_jobs (job_id, search_params_id) values (25, 3);
insert into searches_jobs (job_id, search_params_id) values (26, 1);
insert into searches_jobs (job_id, search_params_id) values (27, 3);
insert into searches_jobs (job_id, search_params_id) values (28, 3);
insert into searches_jobs (job_id, search_params_id) values (29, 3);
insert into searches_jobs (job_id, search_params_id) values (30, 4);
insert into searches_jobs (job_id, search_params_id) values (31, 1);
insert into searches_jobs (job_id, search_params_id) values (32, 1);
insert into searches_jobs (job_id, search_params_id) values (33, 3);
insert into searches_jobs (job_id, search_params_id) values (34, 3);
insert into searches_jobs (job_id, search_params_id) values (35, 4);
insert into searches_jobs (job_id, search_params_id) values (36, 4);
insert into searches_jobs (job_id, search_params_id) values (37, 3);
insert into searches_jobs (job_id, search_params_id) values (38, 3);
insert into searches_jobs (job_id, search_params_id) values (39, 1);
insert into searches_jobs (job_id, search_params_id) values (40, 4);
insert into searches_jobs (job_id, search_params_id) values (41, 2);
insert into searches_jobs (job_id, search_params_id) values (42, 2);
insert into searches_jobs (job_id, search_params_id) values (43, 4);
insert into searches_jobs (job_id, search_params_id) values (44, 1);
insert into searches_jobs (job_id, search_params_id) values (45, 1);
insert into searches_jobs (job_id, search_params_id) values (46, 2);
insert into searches_jobs (job_id, search_params_id) values (47, 3);
insert into searches_jobs (job_id, search_params_id) values (48, 4);
insert into searches_jobs (job_id, search_params_id) values (49, 1);
insert into searches_jobs (job_id, search_params_id) values (50, 1);
insert into searches_jobs (job_id, search_params_id) values (51, 1);
insert into searches_jobs (job_id, search_params_id) values (52, 4);
insert into searches_jobs (job_id, search_params_id) values (53, 2);
insert into searches_jobs (job_id, search_params_id) values (54, 3);
insert into searches_jobs (job_id, search_params_id) values (55, 2);
insert into searches_jobs (job_id, search_params_id) values (56, 3);
insert into searches_jobs (job_id, search_params_id) values (57, 3);
insert into searches_jobs (job_id, search_params_id) values (58, 1);
insert into searches_jobs (job_id, search_params_id) values (59, 3);
insert into searches_jobs (job_id, search_params_id) values (60, 4);
insert into searches_jobs (job_id, search_params_id) values (61, 3);
insert into searches_jobs (job_id, search_params_id) values (62, 4);
insert into searches_jobs (job_id, search_params_id) values (63, 3);
insert into searches_jobs (job_id, search_params_id) values (64, 2);
insert into searches_jobs (job_id, search_params_id) values (65, 4);
insert into searches_jobs (job_id, search_params_id) values (66, 1);
insert into searches_jobs (job_id, search_params_id) values (67, 3);
insert into searches_jobs (job_id, search_params_id) values (68, 4);
insert into searches_jobs (job_id, search_params_id) values (69, 2);
insert into searches_jobs (job_id, search_params_id) values (70, 4);
insert into searches_jobs (job_id, search_params_id) values (71, 4);
insert into searches_jobs (job_id, search_params_id) values (72, 4);
insert into searches_jobs (job_id, search_params_id) values (73, 2);
insert into searches_jobs (job_id, search_params_id) values (74, 3);
insert into searches_jobs (job_id, search_params_id) values (75, 2);
insert into searches_jobs (job_id, search_params_id) values (76, 3);
insert into searches_jobs (job_id, search_params_id) values (77, 2);
insert into searches_jobs (job_id, search_params_id) values (78, 3);
insert into searches_jobs (job_id, search_params_id) values (79, 3);
insert into searches_jobs (job_id, search_params_id) values (80, 4);
insert into searches_jobs (job_id, search_params_id) values (81, 2);
insert into searches_jobs (job_id, search_params_id) values (82, 3);
insert into searches_jobs (job_id, search_params_id) values (83, 4);
insert into searches_jobs (job_id, search_params_id) values (84, 3);
insert into searches_jobs (job_id, search_params_id) values (85, 2);
insert into searches_jobs (job_id, search_params_id) values (86, 4);
insert into searches_jobs (job_id, search_params_id) values (87, 4);
insert into searches_jobs (job_id, search_params_id) values (88, 4);
insert into searches_jobs (job_id, search_params_id) values (89, 3);
insert into searches_jobs (job_id, search_params_id) values (90, 3);
insert into searches_jobs (job_id, search_params_id) values (91, 4);
insert into searches_jobs (job_id, search_params_id) values (92, 3);
insert into searches_jobs (job_id, search_params_id) values (93, 4);
insert into searches_jobs (job_id, search_params_id) values (94, 2);
insert into searches_jobs (job_id, search_params_id) values (95, 3);
insert into searches_jobs (job_id, search_params_id) values (96, 2);
insert into searches_jobs (job_id, search_params_id) values (97, 2);
insert into searches_jobs (job_id, search_params_id) values (98, 2);
insert into searches_jobs (job_id, search_params_id) values (99, 1);
insert into searches_jobs (job_id, search_params_id) values (100, 1);
