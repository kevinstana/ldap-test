-- Test users
INSERT INTO external_users (username, password) VALUES
    ('admin', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),

    ('student-1', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student-2', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student-3', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student-4', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student-5', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    
    ('professor-1', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor-2', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor-3', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor-4', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor-5', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),

    ('secretary-1', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('secretary-2', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe');

INSERT INTO users (username, email, role_id, first_name, last_name, created_at, is_enabled) VALUES
    ('admin', 'admin@test.com', 4, 'Admin', 'One', NOW(), true),

    ('student-1', 'student-1@test.com', 1, 'Student', 'One', NOW(), true),
    ('student-2', 'student-2@test.com', 1, 'Student', 'Two', NOW(), true),
    ('student-3', 'student-3@test.com', 1, 'Student', 'Three', NOW(), true),
    ('student-4', 'student-4@test.com', 1, 'Student', 'Four', NOW(), true),
    ('student-5', 'student-5@test.com', 1, 'Student', 'Five', NOW(), true),
    
    ('professor-1', 'professor-1@test.com', 2, 'Professor', 'One', NOW(), true),
    ('professor-2', 'professor-2@test.com', 2, 'Professor', 'Tow', NOW(), true),
    ('professor-3', 'professor-3@test.com', 2, 'Professor', 'Three', NOW(), true),
    ('professor-4', 'professor-4@test.com', 2, 'Professor', 'Four', NOW(), true),
    ('professor-5', 'professor-5@test.com', 2, 'Professor', 'Five', NOW(), true),

    ('secretary-1', 'secretary-1@test.com', 3, 'Secretary', 'One', NOW(), true),
    ('secretary-2', 'secretary-2@test.com', 3, 'Secretary', 'Two', NOW(), true);
