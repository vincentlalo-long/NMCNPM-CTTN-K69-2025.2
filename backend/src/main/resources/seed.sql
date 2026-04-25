SET
    NAMES 'utf8mb4';

-- =========================
-- CLEAR DATA
-- =========================
DELETE FROM `booking_payments`;

DELETE FROM `pitch_reviews`;

DELETE FROM `bookings`;

DELETE FROM `services`;

DELETE FROM `price_rules`;

DELETE FROM `pitches`;

DELETE FROM `venues`;

DELETE FROM `users`;

-- reset auto increment
ALTER TABLE `users` AUTO_INCREMENT = 1;

ALTER TABLE `venues` AUTO_INCREMENT = 1;

ALTER TABLE `pitches` AUTO_INCREMENT = 1;

ALTER TABLE `price_rules` AUTO_INCREMENT = 1;

ALTER TABLE `services` AUTO_INCREMENT = 1;

ALTER TABLE `bookings` AUTO_INCREMENT = 1;

ALTER TABLE `pitch_reviews` AUTO_INCREMENT = 1;

ALTER TABLE `booking_payments` AUTO_INCREMENT = 1;

-- =========================
-- 1. USERS
-- =========================
INSERT INTO
    `users` (
        `id`,
        `username`,
        `email`,
        `password`,
        `role`,
        `created_at`,
        `phone_number`,
        `avatar_url`
    )
VALUES
    (
        1,
        'manager01',
        'manager@example.com',
        '$2a$10$examplehashedpassword',
        'OWNER',
        NOW (),
        '0900000001',
        NULL
    ),
    (
        2,
        'player01',
        'player@example.com',
        '$2a$10$examplehashedpassword',
        'PLAYER',
        NOW (),
        '0900000002',
        NULL
    );

-- =========================
-- 2. VENUE
-- =========================
INSERT INTO
    `venues` (
        `id`,
        `name`,
        `address`,
        `description`,
        `image_url`,
        `manager_id`,
        `open_time`,
        `close_time`
    )
VALUES
    (
        1,
        'Cum san Bong Da Yen Hoa',
        '123 Nguyen Chi Thanh, Cau Giay, Ha Noi',
        'Cum san phuc vu cho phong trao va giai dau ban chuyen',
        'https://images.unsplash.com/photo-1574629810360-7efbbe195018',
        1,
        '06:30:00',
        '23:00:00'
    );

-- =========================
-- 3. PITCHES
-- =========================
INSERT INTO
    `pitches` (
        `id`,
        `name`,
        `pitch_type`,
        `is_active`,
        `base_price`,
        `venue_id`
    )
VALUES
    (1, 'San 5A', 'SAN_5', TRUE, 350000.00, 1),
    (2, 'San 7B', 'SAN_7', TRUE, 500000.00, 1),
    (3, 'San 11C', 'SAN_11', TRUE, 900000.00, 1);

-- =========================
-- 4. PRICE RULES
-- =========================
INSERT INTO
    `price_rules` (`pitch_id`, `slot_number`, `is_weekend`, `price`)
VALUES
    (1, 1, FALSE, 350000.00),
    (1, 1, TRUE, 420000.00),
    (2, 1, FALSE, 500000.00),
    (2, 1, TRUE, 580000.00),
    (3, 1, FALSE, 900000.00),
    (3, 1, TRUE, 1000000.00);

-- =========================
-- 5. SERVICES
-- =========================
INSERT INTO
    `services` (`pitch_id`, `name`, `price`, `unit`)
VALUES
    (1, 'Nuoc khoang', 10000.00, 'chai'),
    (2, 'Thue ao bib', 25000.00, 'bo'),
    (3, 'Bong thi dau', 150000.00, 'qua');

-- =========================
-- 6. BOOKINGS
-- =========================
INSERT INTO
    `bookings` (
        `player_id`,
        `pitch_id`,
        `booking_date`,
        `start_time`,
        `end_time`,
        `status`,
        `booking_type`,
        `total_price`,
        `created_at`
    )
VALUES
    (
        2,
        1,
        CURRENT_DATE,
        '17:00:00',
        '18:30:00',
        'RESERVED',
        'MATCH',
        500000.00,
        NOW ()
    ),
    (
        2,
        2,
        DATEADD ('DAY', 1, CURRENT_DATE),
        '18:30:00',
        '20:00:00',
        'RESERVED',
        'TOUR',
        900000.00,
        NOW ()
    ),
    (
        2,
        3,
        DATEADD ('DAY', -1, CURRENT_DATE),
        '14:00:00',
        '15:30:00',
        'PLAYING',
        'MATCH',
        900000.00,
        NOW ()
    );

-- =========================
-- 7. REVIEWS
-- =========================
INSERT INTO
    `pitch_reviews` (
        `pitch_id`,
        `player_id`,
        `rating`,
        `content`,
        `created_at`
    )
VALUES
    (1, 2, 5, 'San dep, chat luong tot', NOW ()),
    (2, 2, 4, 'Gia hop ly, anh sang on', NOW ());