-- Description: Insert root user into the database
INSERT INTO mercheazy_user
(u_first_name, u_last_name, u_email, u_password, u_role, u_username)
VALUES ('MerchEazy', 'Admin', 'hello@mercheazy.com', '$2a$10$7wqg9TkUENcqQUY8IawJTuRf6hrgTA6Gt6cyxiTAA4P/Ct140fwSC', 'ADMIN', 'mercheazy');