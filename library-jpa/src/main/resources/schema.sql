drop table if exists books;
drop table if exists authors;
drop table if exists genres;

create table authors(id bigint auto_increment primary key,
                     name varchar(255),
                     country varchar(255)
                     );

create table genres(id bigint auto_increment primary key,
                    name varchar(255)
                    );

create table books(id bigint auto_increment primary key,
                   name varchar(255),
                   author_id bigint not null,
                   genre_id bigint not null,
                   quantity_pages integer,
                   publishing varchar(255)
                   );

alter table books add constraint fk_genre_id foreign key (genre_id) references genres (id) on delete cascade;
alter table books add constraint fk_author_id foreign key (author_id) references authors (id) on delete cascade;