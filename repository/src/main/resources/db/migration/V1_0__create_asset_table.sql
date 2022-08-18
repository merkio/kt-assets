CREATE TABLE assets.asset
(
    id         BIGSERIAL PRIMARY KEY,
    key        VARCHAR NOT NULL,
    url        VARCHAR,
    tags       JSONB,
    username   VARCHAR,
    metadata   JSONB,
    created_at TIMESTAMP with time zone,
    updated_at TIMESTAMP with time zone
);