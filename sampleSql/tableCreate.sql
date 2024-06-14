CREATE TABLE keysTable (
                           id VARCHAR(36) PRIMARY KEY,
                           created_at TIMESTAMP NOT NULL,
                           blocked_at TIMESTAMP NULL,
                           kept_alive_at TIMESTAMP NOT NULL,
                           is_blocked BOOLEAN NOT NULL,
                           INDEX idx_is_blocked (is_blocked),
                           INDEX idx_created_at (created_at),
                           INDEX idx_keep_alive_at (kept_alive_at),
                           INDEX idx_id (id)
);
