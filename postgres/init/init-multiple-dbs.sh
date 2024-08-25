#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username postgres <<-EOSQL
    CREATE DATABASE linkedin_jobs_bot;
    CREATE DATABASE bar_db;
EOSQL