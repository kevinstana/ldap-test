-- Test users
INSERT INTO external_users (username, password) VALUES
    ('admin', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),

    ('student1', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student2', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student3', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student4', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('student5', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    
    ('professor1', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor2', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor3', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor4', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('professor5', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),

    ('secretary1', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('secretary2', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('secretary3', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('secretary4', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe'),
    ('secretary5', '$2a$10$KM.4rAp55xBGoN36cFtysOpaolHZQO9.qeNmhavqe0LCStn4WXLBe');

INSERT INTO users (username, email, role_id, first_name, last_name, created_at, is_enabled, last_modified, last_modified_by) VALUES
    ('admin', 'admin@test.com', 4, 'Admin', 'One', NOW(), true, NOW(), null),

    ('student1', 'student1@test.com', 1, 'Student', 'One', NOW(), true, NOW(), 1),
    ('student2', 'student2@test.com', 1, 'Student', 'Two', NOW(), true, NOW(), 1),
    ('student3', 'student3@test.com', 1, 'Student', 'Three', NOW(), true, NOW(), 1),
    ('student4', 'student4@test.com', 1, 'Student', 'Four', NOW(), true, NOW(), 1),
    ('student5', 'student5@test.com', 1, 'Student', 'Five', NOW(), true, NOW(), 1),
    
    ('professor1', 'professor1@test.com', 2, 'Professor', 'One', NOW(), true, NOW(), 1),
    ('professor2', 'professor2@test.com', 2, 'Professor', 'Tow', NOW(), true, NOW(), 1),
    ('professor3', 'professor3@test.com', 2, 'Professor', 'Three', NOW(), true, NOW(), 1),
    ('professor4', 'professor4@test.com', 2, 'Professor', 'Four', NOW(), true, NOW(), 1),
    ('professor5', 'professor5@test.com', 2, 'Professor', 'Five', NOW(), true, NOW(), 1),

    ('secretary1', 'secretary1@test.com', 3, 'Secretary', 'One', NOW(), true, NOW(), 1),
    ('secretary2', 'secretary2@test.com', 3, 'Secretary', 'Two', NOW(), true, NOW(), 1),
    ('secretary3', 'secretary3@test.com', 3, 'Secretary', 'Three', NOW(), true, NOW(), 1),
    ('secretary4', 'secretary4@test.com', 3, 'Secretary', 'Four', NOW(), true, NOW(), 1),
    ('secretary5', 'secretary5@test.com', 3, 'Secretary', 'Five', NOW(), true, NOW(), 1);
