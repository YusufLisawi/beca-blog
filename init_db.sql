use becaBlog;

-- Create admin user
INSERT INTO user (username, password, isAdmin) VALUES ('admin', '$2a$12$h02dzQNUfMdp9/18KgFf.OHx0SwH5y2mmAdR.HT3L9ynnbR5H5RbC', true);

-- Create normal user
INSERT INTO user (username, password, isAdmin) VALUES ('user1', '$2a$12$h02dzQNUfMdp9/18KgFf.OHx0SwH5y2mmAdR.HT3L9ynnbR5H5RbC', false);
INSERT INTO user (username, password, isAdmin) VALUES ('user2', '$2a$12$h02dzQNUfMdp9/18KgFf.OHx0SwH5y2mmAdR.HT3L9ynnbR5H5RbC', false);

-- Get user ids
SET @adminId = (SELECT id FROM user WHERE username = 'admin');
SET @userId1 = (SELECT id FROM user WHERE username = 'user1');
SET @userId2 = (SELECT id FROM user WHERE username = 'user2');

-- Create post for admin
INSERT INTO post (title, content, user_id) VALUES ('Admin Post 1', 'This is the first post by admin', @adminId);
INSERT INTO post (title, content, user_id) VALUES ('Admin Post 2', 'This is the second post by admin', @adminId);

-- Create post for user1
INSERT INTO post (title, content, user_id) VALUES ('User1 Post 1', 'This is the first post by user1', @userId1);
INSERT INTO post (title, content, user_id) VALUES ('User1 Post 2', 'This is the second post by user1', @userId1);

-- Create post for user2
INSERT INTO post (title, content, user_id) VALUES ('User2 Post 1', 'This is the first post by user2', @userId2);
INSERT INTO post (title, content, user_id) VALUES ('User2 Post 2', 'This is the second post by user2', @userId2);
