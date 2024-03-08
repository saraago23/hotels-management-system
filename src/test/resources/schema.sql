create table roles
(
    id          bigint auto_increment primary key,
    title       varchar(25) not null,
    description varchar(255) ,
    deleted     boolean default false not null
);


create table users
(
    id        bigint auto_increment primary key,
    role_id   bigint                  not null,
    first_name varchar(25)          not null,
    last_name  varchar(25)          not null,
    birth_date date                 not null,
    gender     char(1)      not null,
    phone      varchar(20)          ,
    email      varchar(50)          not null,
    address    varchar(50)         ,
    username  varchar(25)          not null,
    password  varchar(50)          not null,
    deleted   boolean default false not null,
    constraint users_role_id_roles_id
        foreign key (role_id) references roles (id)
);

create table  hotels
(
    id          bigint auto_increment primary key,
    hotel_name  varchar(25)          not null,
    address     varchar(50)          ,
    post_code   bigint                  ,
    city        varchar(50)          not null,
    country     varchar(50)          ,
    rooms_nr    int                  not null,
    phone       varchar(20)          not null,
    star_rating int                  ,
    deleted   boolean default false not null
);

create table room_types
(
    id        bigint auto_increment primary key,
    type      varchar(25)          not null,
    price     int                  not null,
    room_desc varchar(255)         null,
    deleted   boolean default false not null
);

create table  rooms
(
    id           bigint auto_increment primary key,
    room_type_id bigint                  not null,
    hotel_id     bigint                  not null,
    is_booked    boolean default false not null,
    deleted      boolean default false not null,
    constraint rooms_room_type_id_room_types_id
        foreign key (room_type_id) references room_types (id),
    constraint rooms_hotel_id_hotels_id
        foreign key (hotel_id) references hotels (id)
);

create table  bookings
(
    id             bigint auto_increment  primary key,
    user_id        bigint                  not null,
    total_price    int                  not null,
    booking_time   timestamp            not null,
    check_in_time  timestamp            ,
    check_out_time timestamp            ,
    num_adults     int                  ,
    num_children   int                  ,
    special_req    varchar(255)         ,
    deleted        boolean default false not null,
    constraint bookings_user_id_users_id
        foreign key (user_id) references users (id)
);

create table rooms_booked
(
    id         bigint auto_increment  primary key,
    room_id    bigint                  not null,
    booking_id bigint                  not null,
    price      int                  not null,
    deleted    boolean default false not null,
    constraint rooms_booked_room_id_rooms_id
        foreign key (room_id) references rooms(id),
    constraint rooms_booked_booking_id_bookings_id
        foreign key (booking_id) references bookings (id)
);


