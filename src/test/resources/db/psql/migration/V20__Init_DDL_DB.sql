CREATE SEQUENCE hibernate_sequence start 1 increment 1;

CREATE TABLE message (
        primary key (id),
        id          int4		    not null,
        body        varchar(255)    not null,
        date_time   TIMESTAMP
                        WITHOUT TIME ZONE
					                not null,
        id_ext      int4		    not null
);
