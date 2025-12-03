-- Add security columns to users table for JWT authentication

ALTER TABLE users 
ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT 'password123';

ALTER TABLE users 
ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER';

-- Create index for email and role for faster lookups
CREATE INDEX idx_user_email_role ON users(email, role);
