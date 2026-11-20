-- ============================================================
-- COMP228 - Week 10 Lab
-- database.sql
--
-- Creates the database, table and application user used by the
-- JavaFX job application form.
--
-- Run as an administrator:
--   sudo mysql < database.sql
--
-- The JavaFX application connects as:
--   user     = job_app
--   password = JobApp#2026
-- ============================================================

CREATE DATABASE IF NOT EXISTS job_applications_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE job_applications_db;

CREATE TABLE IF NOT EXISTS applications (
    application_id        INT            NOT NULL AUTO_INCREMENT,
    full_name             VARCHAR(100)   NOT NULL,
    current_address       VARCHAR(255)   NOT NULL,
    contact_number        VARCHAR(30)    NOT NULL,
    highest_edu_att       VARCHAR(100)   NOT NULL,
    email                 VARCHAR(150)   NOT NULL,
    gender                VARCHAR(30)    NOT NULL,
    available_date        DATE           NOT NULL,
    desired_position      VARCHAR(100)   NOT NULL,
    desired_salary        DECIMAL(12, 2) NOT NULL,
    authorized_to_work    BOOLEAN        NOT NULL,
    have_relatives_working BOOLEAN       NOT NULL,
    explain_relatives     VARCHAR(255)   NULL,
    signature             VARCHAR(100)   NOT NULL,
    created_at            TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (application_id)
) ENGINE = InnoDB;

CREATE USER IF NOT EXISTS 'job_app'@'localhost'
    IDENTIFIED BY 'JobApp#2026';

GRANT ALL PRIVILEGES ON job_applications_db.* TO 'job_app'@'localhost';

FLUSH PRIVILEGES;
