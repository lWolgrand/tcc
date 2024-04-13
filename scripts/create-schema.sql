CREATE TABLE users (
                       id VARCHAR(36) PRIMARY KEY,
                       name VARCHAR(50),
                       surname VARCHAR(50),
                       email VARCHAR(50),
                       birthdate VARCHAR(50),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);