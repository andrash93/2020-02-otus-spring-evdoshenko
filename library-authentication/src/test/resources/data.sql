insert into authors (name, country) values ('Пушкин', 'Россия');
insert into authors (name, country) values ('Беккет', 'Англия');
insert into authors (name, country) values ('Лермонтов', 'Россия');

insert into genres (name) values ('Стихи');
insert into genres (name) values ('Сказки');

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('Стихи',
                                            select id from genres where name = 'Стихи',
                                            select id from authors where name = 'Пушкин',
                                            160,
                                            'Дрофа'
                                            );

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('Книга Беккета',
                                            select id from genres where name = 'Сказки',
                                            select id from authors where name = 'Беккет',
                                            45,
                                            'Бкс'
                                            );

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('Книга Лермонтова',
                                            select id from genres where name = 'Стихи',
                                            select id from authors where name = 'Лермонтов',
                                            48,
                                            'Дрофа'
                                            );

insert into user (id, username, password, roles) values
(1, 'user', '123', 'USER'),
(2, 'admin', 'sa', 'ADMIN'),
(3, 'superUser', '123', 'ADMIN');
