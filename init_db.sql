-- Create admin user
INSERT INTO users (username, password, isAdmin) VALUES ('admin', '$2a$12$h02dzQNUfMdp9/18KgFf.OHx0SwH5y2mmAdR.HT3L9ynnbR5H5RbC', true);

-- Create normal users
INSERT INTO users (username, password, isAdmin) VALUES ('user1', '$2a$12$h02dzQNUfMdp9/18KgFf.OHx0SwH5y2mmAdR.HT3L9ynnbR5H5RbC', false);
INSERT INTO users (username, password, isAdmin) VALUES ('user2', '$2a$12$h02dzQNUfMdp9/18KgFf.OHx0SwH5y2mmAdR.HT3L9ynnbR5H5RbC', false);

-- Get user ids
SET @adminId = (SELECT id FROM users WHERE username = 'admin');
SET @userId1 = (SELECT id FROM users WHERE username = 'user1');
SET @userId2 = (SELECT id FROM users WHERE username = 'user2');

-- Create posts for admin
INSERT INTO posts (title, content, user_id) VALUES ('Admin Post 1', 'This is the first post by admin', @adminId);
INSERT INTO posts (title, content, user_id) VALUES ('Admin Post 2', 'This is the second post by admin', @adminId);

-- Create posts for user1
INSERT INTO posts (title, content, user_id) VALUES ('User1 Post 1', 'This is the first post by user1', @userId1);
INSERT INTO posts (title, content, user_id) VALUES ('User1 Post 2', 'This is the second post by user1', @userId1);

-- Create posts for user2
INSERT INTO posts (title, content, user_id) VALUES ('User2 Post 1', 'This is the first post by user2', @userId2);
INSERT INTO posts (title, content, user_id) VALUES ('User2 Post 2', 'This is the second post by user2', @userId2);
