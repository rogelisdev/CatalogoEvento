-- Initial schema for venues and events

CREATE TABLE IF NOT EXISTS venues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE,
    address VARCHAR(30) NOT NULL,
    city VARCHAR(50) NOT NULL,
    country VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    CONSTRAINT chk_venue_capacity CHECK (capacity > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(40) NOT NULL,
    date DATETIME NOT NULL,
    capacity INT NOT NULL,
    price DOUBLE NOT NULL,
    venue_id BIGINT NOT NULL,
    CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES venues(id) ON DELETE CASCADE,
    CONSTRAINT chk_event_capacity CHECK (capacity > 0),
    CONSTRAINT chk_event_price CHECK (price > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_event_venue ON events(venue_id);
CREATE INDEX idx_event_date ON events(date);
