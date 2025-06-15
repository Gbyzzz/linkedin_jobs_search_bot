CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

ALTER TABLE user_profiles ADD password varchar(255);
ALTER TABLE user_profiles ADD user_role user_role NOT NULL default 'USER';
ALTER TABLE user_profiles ADD user_pic varchar(255);