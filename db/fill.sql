USE library_db;
INSERT INTO libraries (id, name) VALUES
(1, 'Центральная городская публичная библиотека имени В.В. Маяковского'),
(2, 'Российская национальная библиотека (РНБ)');

INSERT INTO visitors (id, name, card_id) VALUES 
(1, 'chipolino', 1);

INSERT INTO cards (id, registration_place_id) VALUES
(1, 1);

INSERT INTO books (id, title, author, number_of_copies, year_of_publication)
VALUES
    (1, 'Что делать?', 'Николай Гаврилович Чернышевский', 1, 1863),
    (2, 'Идиот', 'Фёдор Михайлович Достоевский', 8, 1869),
    (3, 'Братья Карамазовы', 'Фёдор Михайлович Достоевский', 12, 1880);

INSERT INTO workers (id, username, password, job_place_id) VALUES 
(1, 'antoneth', 'password', 1),
(2, 'tandraph', 'password', 2);

