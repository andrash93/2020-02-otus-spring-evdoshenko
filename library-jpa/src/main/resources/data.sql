insert into authors (name, country) values ('автор1', 'страна1');
insert into authors (name, country) values ('автор2', 'страна2');
insert into authors (name, country) values ('автор 3', 'страна 3');

insert into genres (name) values ('жанр1');

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('книга1',
                                            select id from genres where name = 'жанр1',
                                            select id from authors where name = 'автор2',
                                            160,
                                            'Издательство1'
                                            );

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('книга2',
                                            select id from genres where name = 'жанр1',
                                            select id from authors where name = 'автор1',
                                            10,
                                            'Издательство2'
                                            );

insert into books (name, genre_id, author_id, quantity_pages, publishing)
                                            values ('книга3',
                                            select id from genres where name = 'жанр1',
                                            select id from authors where name = 'автор2',
                                            10,
                                            'Издательство3'
                                            );