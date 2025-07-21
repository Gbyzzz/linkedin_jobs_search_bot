ALTER TABLE saved_jobs ADD llama_job_grade smallint;
ALTER TABLE saved_jobs ADD mistral_job_grade smallint;
ALTER TABLE saved_jobs ADD gemma_job_grade smallint;

ALTER TABLE user_profiles DROP COLUMN password;
ALTER TABLE user_profiles DROP COLUMN user_pic;