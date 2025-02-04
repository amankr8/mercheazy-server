--Add columns to the users table
ALTER TABLE mercheazy_user
ADD COLUMN u_first_name VARCHAR(100) NOT NULL DEFAULT 'User',
ADD COLUMN u_last_name VARCHAR(100);

UPDATE mercheazy_user
SET u_first_name = 'MerchEazy',
    u_last_name = 'Admin'
WHERE u_username = 'mercheazy';
