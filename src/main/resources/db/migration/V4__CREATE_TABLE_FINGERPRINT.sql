CREATE TABLE tb_fingerprint(
    id VARCHAR(36) UNIQUE NOT NULL,
    fingerprint TEXT UNIQUE NOT NULL,
    card_id VARCHAR(36) NOT NULL,
    association_date TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
)