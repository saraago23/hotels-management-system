INSERT INTO roles (title, description, deleted) VALUES ('ADMIN', 'administrator', DEFAULT);
INSERT INTO roles (title, description, deleted) VALUES ('CLIENT', 'client ', DEFAULT);

INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Sara', 'Ago', '2000-06-23', 'F', '0688032311', 'saraago@hotmail.com', 'Lgj.18', DEFAULT);
INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Anisa', 'Bakiu', '1999-09-02', 'F', '0692345671', 'anisabakiu@gmail.com', 'Lgj.8', DEFAULT);
INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Kristian', 'Hoxha', '1980-11-05', 'M', '0685891432', 'kristian123@yahoo.com', 'Lgj.2', DEFAULT);
INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Stefan', 'Bilali', '1976-03-14', 'M', '0687612890', 'stefbilali23@gmail.com', 'Lgj.1', DEFAULT);
INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Elena', 'Muco', '1996-02-09', 'F', '0692314568', 'elenamuco@yahoo.com', 'Lgj.4', DEFAULT);
INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Sofia', 'Verdho', '1995-08-22', 'F', '0685069873', 'sofiav22@gmail.com', 'Lgj.3', DEFAULT);
INSERT INTO people (first_name, last_name, birth_date, gender, phone, email, address, deleted) VALUES ('Pandeli', 'Kodra', '1970-03-04', 'M', '0692345867', 'pandeli.kodra@yahoo.com', 'Lgj.7', DEFAULT);

INSERT INTO users (person_id, role_id, username, password, deleted) VALUES (1, 1, 'saraago23', 'lalala', DEFAULT);
INSERT INTO users (person_id, role_id, username, password, deleted) VALUES (2, 2, 'anisabakiu', '12345678', DEFAULT);
INSERT INTO users (person_id, role_id, username, password, deleted) VALUES (3, 2, 'kristian123', 'kristi123', DEFAULT);
INSERT INTO users (person_id, role_id, username, password, deleted) VALUES (4, 2, 'stefanbilali', '18765213', DEFAULT);
INSERT INTO users (person_id, role_id, username, password, deleted) VALUES (5, 2, 'elenamuco', 'elenaeli', DEFAULT);

INSERT INTO hotels (hotel_name, address, post_code, city, country, rooms_nr, phone, star_rating, deleted) VALUES ('Dim''s', 'Lgj.1', 7001, 'Korce', 'Albania', 5, '0686012366', 5, DEFAULT);
INSERT INTO hotels (hotel_name, address, post_code, city, country, rooms_nr, phone, star_rating, deleted) VALUES ('Plaza', 'Lgj.12', 1001, 'Tirane', 'Albania', 6, '0682341657', 5, DEFAULT);
INSERT INTO hotels (hotel_name, address, post_code, city, country, rooms_nr, phone, star_rating, deleted) VALUES ('Elba', 'Lgj.10', 1501, 'Durres', 'Albania', 7, '0699012763', 3, DEFAULT);

INSERT INTO room_types (type, price, room_desc, deleted) VALUES ('Twin-Room', 50, 'two single beds', DEFAULT);
INSERT INTO room_types (type, price, room_desc, deleted) VALUES ('Deluxe-Double', 60, 'king sized bed', DEFAULT);
INSERT INTO room_types (type, price, room_desc, deleted) VALUES ('Suite ', 110, 'exclusive suite', DEFAULT);

INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 1, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 1, DEFAULT, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 1, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 1, DEFAULT, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 1, DEFAULT, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (3, 2, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 2, DEFAULT, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 2, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 2, DEFAULT, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 2, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 2, DEFAULT, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 3, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 3, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (1, 3, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 3, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (2, 3, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (3, 3, 1, DEFAULT);
INSERT INTO rooms (room_type_id, hotel_id, is_booked, deleted) VALUES (3, 3, 1, DEFAULT);


INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, num_adults, num_children, special_req, deleted) VALUES (2, 110, '2024-02-29 11:03:33', '2024-02-29 00:00:00', '2024-03-01 00:00:00', 4, 0, null, DEFAULT);
INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, num_adults, num_children, special_req, deleted) VALUES (3, 220, '2024-02-26 07:07:40', '2024-02-28 00:00:00', '2024-02-29 00:00:00', 6, 0, null, DEFAULT);
INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, num_adults, num_children, special_req, deleted) VALUES (4, 300, '2024-01-10 23:17:13', '2024-02-02 00:00:00', '2024-02-04 00:00:00', 6, 0, null, DEFAULT);
INSERT INTO bookings (user_id, total_price, booking_time, check_in_time, check_out_time, num_adults, num_children, special_req, deleted) VALUES (5, 340, '2024-12-17 14:31:19', '2024-02-28 00:00:00', '2024-02-29 00:00:00', 6, 2, null, DEFAULT);


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

INSERT INTO professions (title) VALUES ('Manager');
INSERT INTO professions (title) VALUES ('Receptionist');
INSERT INTO professions (title) VALUES ('Chef');
INSERT INTO professions (title) VALUES ('Economist');
INSERT INTO professions (title) VALUES ('Housekeeper');
INSERT INTO professions (title) VALUES ('Porter');

INSERT INTO employees (person_id, hotel_id, profession_id, salary, deleted) VALUES (1, 1, 1, 60000, DEFAULT);
INSERT INTO employees (person_id, hotel_id, profession_id, salary, deleted) VALUES (6, 1, 2, 40000, DEFAULT);
INSERT INTO employees (person_id, hotel_id, profession_id, salary, deleted) VALUES (7, 1, 3, 70000, DEFAULT);
