CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    login_id VARCHAR(20)  NOT NULL,
    name     VARCHAR(20)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE theme
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    name                VARCHAR(20)  NOT NULL,
    description         VARCHAR(255) NOT NULL,
    thumbnail_image_url VARCHAR(500) NOT NULL,
    is_active           TINYINT      NOT NULL DEFAULT 1,
    active_name         VARCHAR(20) AS (CASE WHEN is_active = 1 THEN name ELSE NULL END),
    PRIMARY KEY (id),
    CONSTRAINT uk_active_name UNIQUE (active_name)
);

CREATE TABLE reservation_time
(
    id              BIGINT  NOT NULL AUTO_INCREMENT,
    start_at        TIME    NOT NULL,
    is_active       TINYINT NOT NULL DEFAULT 1,
    active_start_at TIME AS (CASE WHEN is_active = 1 THEN start_at ELSE NULL END),
    PRIMARY KEY (id),
    CONSTRAINT uk_start_at UNIQUE (active_start_at)
);

CREATE TABLE reservation
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    user_id        BIGINT      NOT NULL,
    date           DATE        NOT NULL,
    theme_id       BIGINT      NOT NULL,
    time_id        BIGINT      NOT NULL,
    status         VARCHAR(20) NOT NULL,
    active_time_id BIGINT AS (CASE WHEN status = 'RESERVED' THEN time_id ELSE NULL END),
    PRIMARY KEY (id),
    FOREIGN KEY (theme_id) REFERENCES theme (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id),
    CONSTRAINT uk_reservation_date_theme_active_time UNIQUE (date, theme_id, active_time_id)
);
