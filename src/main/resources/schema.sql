create table roles
(
    id          int auto_increment primary key,
    title       varchar(25)           not null,
    description varchar(255),
    deleted     boolean default false not null
);


create table people
(
    id         int auto_increment primary key,
    first_name varchar(25)           not null,
    last_name  varchar(25)           not null,
    birth_date date                  not null,
    gender     char(1)       not null,
    phone      varchar(20),
    email      varchar(50)           not null,
    address    varchar(50),
    deleted    boolean default false not null
);

create table users
(
    id        int auto_increment primary key,
    person_id int                   not null,
    role_id   int                   not null,
    username  varchar(25)           not null,
    password  varchar(50)           not null,
    deleted   boolean default false not null,
    constraint users_person_id_people_id
        foreign key (person_id) references people (id),
    constraint users_role_id_roles_id
        foreign key (role_id) references roles (id)
);

create table hotels
(
    id          int auto_increment primary key,
    hotel_name  varchar(25)           not null,
    address     varchar(50),
    post_code   int,
    city        varchar(50)           not null,
    country     varchar(50),
    rooms_nr    int                   not null,
    phone       varchar(20)           not null,
    star_rating int,
    deleted     boolean default false not null
);

create table room_types
(
    id        int auto_increment primary key,
    type      varchar(25)           not null,
    price     int                   not null,
    room_desc varchar(255)          null,
    deleted   boolean default false not null
);

create table rooms
(
    id           int auto_increment
        primary key,
    room_type_id int                   not null,
    hotel_id     int                   not null,
    is_booked    boolean default false not null,
    deleted      boolean default false not null,
    constraint rooms_room_type_id_room_types_id
        foreign key (room_type_id) references room_types (id),
    constraint rooms_hotel_id_hotels_id
        foreign key (hotel_id) references hotels (id)
);

create table bookings
(
    id             int auto_increment primary key,
    user_id        int                   not null,
    total_price    int                   not null,
    booking_time   timestamp             not null,
    check_in_time  timestamp,
    check_out_time timestamp,
    num_adults     int,
    num_children   int,
    special_req    varchar(255),
    deleted        boolean default false not null,
    constraint bookings_user_id_users_id
        foreign key (user_id) references users (id)
);

create table rooms_booked
(
    id         int auto_increment primary key,
    room_id    int                   not null,
    booking_id int                   not null,
    price      int                   not null,
    deleted    boolean default false not null,
    constraint rooms_booked_room_id_rooms_id
        foreign key (room_id) references rooms (id),
    constraint rooms_booked_booking_id_bookings_id
        foreign key (booking_id) references bookings (id)
);

create table professions
(
    id      int auto_increment primary key,
    title    varchar(25)                   not null,
    job_desc varchar(250)
);

create table employees
(
    id         int auto_increment primary key,
    person_id  int                   not null,
    hotel_id   int                   not null,
    profession_id int,
    salary     int,
    deleted    boolean default false not null,
    constraint employees_person_id_people_id
        foreign key (person_id) references people (id),
    constraint employees_hotel_id_hotels_id
        foreign key (hotel_id) references hotels (id),
    constraint employees_profession_id_hotels_id
        foreign key (profession_id) references professions (id)
);

