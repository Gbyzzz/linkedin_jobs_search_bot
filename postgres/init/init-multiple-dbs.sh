#!/bin/bash
set -e

echo "Creating databases..."

psql -v ON_ERROR_STOP=1 --username postgres <<-EOSQL
    CREATE DATABASE linkedin_jobs_bot;
    CREATE DATABASE bar_db;
EOSQL

echo "Databases created."