drop table if EXISTS statistics cascade;

CREATE TABLE IF NOT EXISTS statistics (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(255) NOT NULL,
    uri VARCHAR(512) NOT NULL,
    ip VARCHAR(50) NOT NULL,
    timestamp VARCHAR(512) NOT NULL
);