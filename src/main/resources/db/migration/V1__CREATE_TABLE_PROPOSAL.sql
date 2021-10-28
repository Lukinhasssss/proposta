CREATE TABLE tb_proposal(
    id VARCHAR(36) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    document VARCHAR(18) UNIQUE NOT NULL,
    salary DOUBLE PRECISION NOT NULL,
    address VARCHAR(150) NOT NULL,
    PRIMARY KEY(id)
);