-- Create user table
CREATE TABLE merch_user (
    u_create_date TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    u_id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    u_update_date TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    u_email VARCHAR(255) NOT NULL UNIQUE,
    u_password VARCHAR(255) NOT NULL,
    u_role VARCHAR(255) DEFAULT 'USER' NOT NULL CHECK (u_role IN ('ADMIN', 'USER', 'CREATOR', 'MODERATOR')),
    u_username VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (u_id)
);

-- Create function to update the u_update_date column
CREATE OR REPLACE FUNCTION update_timestamp_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.u_update_date = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger to automatically update u_update_date before every update
CREATE TRIGGER set_update_date
BEFORE UPDATE ON merch_user
FOR EACH ROW
EXECUTE FUNCTION update_timestamp_column();
