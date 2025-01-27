-- Set default value for u_create_date and u_update_date to CURRENT_TIMESTAMP
ALTER TABLE mercheazy_user
    ALTER COLUMN u_create_date SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN u_update_date SET DEFAULT CURRENT_TIMESTAMP;

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
BEFORE UPDATE ON mercheazy_user
FOR EACH ROW
EXECUTE FUNCTION update_timestamp_column();

--------------------------------------------------------------------------------
-- Description: Insert root user into the database
INSERT INTO public.mercheazy_user
(u_email, u_password, u_role, u_username)
VALUES ('hello@mercheazy.com', '$2a$10$7wqg9TkUENcqQUY8IawJTuRf6hrgTA6Gt6cyxiTAA4P/Ct140fwSC', 'ADMIN', 'mercheazy');