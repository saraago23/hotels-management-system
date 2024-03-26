INSERT INTO roles (title, description, deleted) VALUES ('ADMIN', 'administrator', DEFAULT);
INSERT INTO roles (title, description, deleted) VALUES ('CLIENT', 'client ', DEFAULT);


INSERT INTO users (role_id,first_name, last_name, birth_date, gender, phone, email, address,username, password, deleted) VALUES (1,'Sara', 'Ago', '2000-06-23', 'F', '0688032311', 'saraago@hotmail.com', 'Lgj.18', 'saraago23', 'lalala', DEFAULT);
INSERT INTO users (role_id,first_name, last_name, birth_date, gender, phone, email, address,username, password, deleted) VALUES (2,'Anisa', 'Bakiu', '1999-09-02', 'F', '0692345671', 'anisabakiu@gmail.com', 'Lgj.8', 'anisabakiu', '12345678', DEFAULT);
INSERT INTO users (role_id,first_name, last_name, birth_date, gender, phone, email, address,username, password, deleted) VALUES (2, 'Kristian', 'Hoxha', '1980-11-05', 'M', '0685891432', 'kristian123@yahoo.com', 'Lgj.2','kristian123', 'kristi123', DEFAULT);
INSERT INTO users (role_id,first_name, last_name, birth_date, gender, phone, email, address,username, password, deleted) VALUES (2,'Stefan', 'Bilali', '1976-03-14', 'M', '0687612890', 'stefbilali23@gmail.com', 'Lgj.1', 'stefanbilali', '18765213', DEFAULT);
INSERT INTO users (role_id,first_name, last_name, birth_date, gender, phone, email, address,username, password, deleted) VALUES (2,'Elena', 'Muco', '1996-02-09', 'F', '0692314568', 'elenamuco@yahoo.com', 'Lgj.4','elenamuco', 'elenaeli', DEFAULT);

INSERT INTO hotels (hotel_name, address, post_code, city, country, rooms_nr, phone, star_rating, deleted) VALUES ('Dim\''s', 'Lgj.1', 7001, 'Korce', 'Albania', 5, '0686012366', 5, DEFAULT);
INSERT INTO hotels (hotel_name, address, post_code, city, country, rooms_nr, phone, star_rating, deleted) VALUES ('Plaza', 'Lgj.12', 1001, 'Tirane', 'Albania', 6, '0682341657', 5, DEFAULT);
INSERT INTO hotels (hotel_name, address, post_code, city, country, rooms_nr, phone, star_rating, deleted) VALUES ('Elba', 'Lgj.10', 1501, 'Durres', 'Albania', 7, '0699012763', 3, DEFAULT);


INSERT INTO room_types (type, price, num_guest, room_desc, deleted) VALUES ('Twin-Room', 50, 2, 'two single beds', DEFAULT);
INSERT INTO room_types (type, price, num_guest, room_desc, deleted) VALUES ('Deluxe-Double', 60, 3, 'king sized bed', DEFAULT);
INSERT INTO room_types (type, price, num_guest, room_desc, deleted) VALUES ('Suite ', 110, 4, 'exclusive suite', DEFAULT);


INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 1,101, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 1,102, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 1,103, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 1,104, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 1,105, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (3, 2,201, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 2,202, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 2,203, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 2,204, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 2,205, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 2,206, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 3,301, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 3,303, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (1, 3,304, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 3,305, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (2, 3,306, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (3, 3, 307, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, room_nr, deleted) VALUES (3, 3, 308, DEFAULT);


INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, total_num_guests, special_req, deleted) VALUES (2, 110, '2024-02-29 11:03:33', '2024-02-29 00:00:00', '2024-03-01 00:00:00', 4, null, DEFAULT);
INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, total_num_guests, special_req, deleted) VALUES (3, 220, '2024-02-26 07:07:40', '2024-02-28 00:00:00', '2024-02-29 00:00:00', 6, null, DEFAULT);
INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, total_num_guests, special_req, deleted) VALUES (4, 300, '2024-01-10 23:17:13', '2024-02-02 00:00:00', '2024-02-04 00:00:00', 6, null, DEFAULT);
INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, total_num_guests, special_req, deleted) VALUES (5, 340, '2024-12-17 14:31:19', '2024-02-28 00:00:00', '2024-02-29 00:00:00', 8,null, DEFAULT);


INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (1, 1, 50, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (3, 1, 60, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (6, 2, 110, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (8, 2, 60, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (10, 2, 50, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (12, 3, 50, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (13, 3, 50, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (14, 3, 50, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (15, 4, 60, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (16, 4, 60, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (17, 4, 110, DEFAULT);
INSERT INTO rooms_booked (room_id, booking_id, price, deleted) VALUES (18, 4, 110, DEFAULT);




