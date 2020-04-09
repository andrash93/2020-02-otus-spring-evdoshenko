insert into authors (name, country) values ('автор тест1', 'страна1');
insert into authors (name, country) values ('автор тест2', 'страна2');
insert into authors (name, country) values ('автор тест3', 'страна 3');

insert into genres (name) values ('жанр тест1');
insert into genres (name) values ('жанр тест2');

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('книга тест1',
                                            select id from genres where name = 'жанр тест1',
                                            select id from authors where name = 'автор тест1',
                                            160,
                                            'Издательство1'
                                            );

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('книга тест2',
                                            select id from genres where name = 'жанр тест1',
                                            select id from authors where name = 'автор тест1',
                                            10,
                                            'Издательство2'
                                            );

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('книга тест3',
                                            select id from genres where name = 'жанр тест1',
                                            select id from authors where name = 'автор тест3',
                                            10,
                                            'Издательство3'
                                            );