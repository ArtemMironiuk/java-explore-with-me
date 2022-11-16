drop table if EXISTS requests cascade;
drop table if EXISTS compilation_events cascade;
drop table if EXISTS compilations cascade;
drop table if EXISTS events cascade;
drop table if EXISTS categories cascade;
drop table if EXISTS users cascade;

CREATE TABLE IF NOT EXISTS users (
     user_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
     name VARCHAR(255) NOT NULL,
     email VARCHAR(512) NOT NULL,

     CONSTRAINT pk_user PRIMARY KEY (user_id),
     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,

    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(255) NOT NULL,
    annotation VARCHAR(2500) NOT NULL,
    description VARCHAR(7000),
    category_id BIGINT NOT NULL,
    confirmed_requests INTEGER,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    paid BOOLEAN NOT NULL,
    request_moderation BOOLEAN,
    participant_limit INTEGER,
    initiator_id BIGINT NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    event_state VARCHAR(100),
    views INTEGER,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,

    CONSTRAINT pk_event PRIMARY KEY (event_id),
    CONSTRAINT fk_event_on_initiator FOREIGN KEY (initiator_id) REFERENCES users(user_id),
    CONSTRAINT fk_event_on_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(150),

    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id BIGINT,
    event_id BIGINT,

    CONSTRAINT fk_compilation FOREIGN KEY (compilation_id) REFERENCES compilations(id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events(event_id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    requester BIGINT NOT NULL,
    event BIGINT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(150),

    CONSTRAINT pk_request PRIMARY KEY (id),
    CONSTRAINT fk_request_on_requester FOREIGN KEY (requester) REFERENCES users(user_id),
    CONSTRAINT fk_request_on_event FOREIGN KEY (event) REFERENCES events(event_id)
);