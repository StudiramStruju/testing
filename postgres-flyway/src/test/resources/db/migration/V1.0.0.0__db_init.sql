create table "user"
(
    id        serial       not null
        constraint table_name_pk
            primary key,
    full_name varchar(100) not null,
    email     varchar(50)  not null,
    password  varchar(20)  not null
);

DEFINE
FUNCTION SOMETHING AS rettype function_name(args)