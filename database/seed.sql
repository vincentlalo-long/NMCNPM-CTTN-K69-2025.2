SET NAMES 'utf8mb4';

-- KHONG seed bang users theo yeu cau.
-- Can dam bao da co users truoc do:
--   manager_id = 1 (ADMIN)
--   player_id  = 2 (PLAYER)

-- Clear du lieu theo thu tu khoa ngoai
DELETE FROM `booking_payments`;
DELETE FROM `pitch_reviews`;
DELETE FROM `bookings`;
DELETE FROM `services`;
DELETE FROM `price_rules`;
DELETE FROM `pitches`;
DELETE FROM `venues`;

-- 1) Seed 1 Venue (manager_id map toi user da dang ky)
INSERT INTO `venues` (
	`id`, `name`, `address`, `description`, `image_url`, `manager_id`, `open_time`, `close_time`
) VALUES
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

-- 2) Seed 3 Pitches
INSERT INTO `pitches` (`id`, `name`, `pitch_type`, `is_active`, `base_price`, `venue_id`) VALUES
(1, 'San 5A',  'SAN_5',  b'1', 350000.00, 1),
(2, 'San 7B',  'SAN_7',  b'1', 500000.00, 1),
(3, 'San 11C', 'SAN_11', b'1', 900000.00, 1);

-- 3) Seed Price Rules (moi san 2 rule: ngay thuong/cuoi tuan cho slot 1)
INSERT INTO `price_rules` (`pitch_id`, `slot_number`, `is_weekend`, `price`) VALUES
(1, 1, b'0', 350000.00),
(1, 1, b'1', 420000.00),
(2, 1, b'0', 500000.00),
(2, 1, b'1', 580000.00),
(3, 1, b'0', 900000.00),
(3, 1, b'1', 1000000.00);

-- 4) Seed Services
INSERT INTO `services` (`pitch_id`, `name`, `price`, `unit`) VALUES
(1, 'Nuoc khoang', 10000.00, 'chai'),
(2, 'Thue ao bib', 25000.00, 'bo'),
(3, 'Bong thi dau', 150000.00, 'qua');

-- 5) Seed >=10 Bookings de demo Dashboard
-- Luu y: status theo BookingStatus enum hien tai: RESERVED, CANCELLED, PLAYING.
INSERT INTO `bookings` (
	`player_id`, `pitch_id`, `booking_date`, `start_time`, `end_time`, `status`, `booking_type`, `total_price`, `created_at`
) VALUES
(2, 1, DATE_SUB(CURDATE(), INTERVAL 7 DAY), '06:30:00', '08:00:00', 'PLAYING',   'MATCH',    350000.00, DATE_SUB(NOW(), INTERVAL 7 DAY)),
(2, 2, DATE_SUB(CURDATE(), INTERVAL 6 DAY), '08:00:00', '09:30:00', 'PLAYING',   'TRAINING', 500000.00, DATE_SUB(NOW(), INTERVAL 6 DAY)),
(2, 3, DATE_SUB(CURDATE(), INTERVAL 5 DAY), '09:30:00', '11:00:00', 'CANCELLED', 'MATCH',    900000.00, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, 1, DATE_SUB(CURDATE(), INTERVAL 4 DAY), '11:00:00', '12:30:00', 'PLAYING',   'FRIENDLY', 350000.00, DATE_SUB(NOW(), INTERVAL 4 DAY)),
(2, 2, DATE_SUB(CURDATE(), INTERVAL 3 DAY), '12:30:00', '14:00:00', 'CANCELLED', 'MATCH',    500000.00, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, 3, DATE_SUB(CURDATE(), INTERVAL 2 DAY), '14:00:00', '15:30:00', 'PLAYING',   'TOUR',     900000.00, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 1, DATE_SUB(CURDATE(), INTERVAL 1 DAY), '15:30:00', '17:00:00', 'PLAYING',   'MATCH',    350000.00, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2, 2, CURDATE(),                            '17:00:00', '18:30:00', 'RESERVED',  'MATCH',    500000.00, NOW()),
(2, 3, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '18:30:00', '20:00:00', 'RESERVED',  'TOUR',     900000.00, NOW()),
(2, 1, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '20:00:00', '21:30:00', 'CANCELLED', 'FRIENDLY', 350000.00, NOW());

-- 6) Seed reviews (optional cho demo)
INSERT INTO `pitch_reviews` (`pitch_id`, `player_id`, `rating`, `content`, `created_at`) VALUES
(1, 2, 5, 'San dep, chat luong tot', NOW()),
(2, 2, 4, 'Gia hop ly, anh sang on', NOW());