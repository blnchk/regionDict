CREATE TABLE IF NOT EXISTS REGIONS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    short_name VARCHAR(10),
    create_at TIMESTAMP NOT NULL,
    update_at TIMESTAMP
);
CREATE INDEX region_id_index ON regions(id);