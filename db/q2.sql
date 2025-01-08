INSERT INTO library_book (library_id, book_id, number_of_copies)
VALUES
(1, 1, 5),  -- В Центральной библиотеке 5 экземпляров книги "Что делать?"
(1, 2, 8),  -- В Центральной библиотеке 8 экземпляров книги "Идиот"
(1, 3, 12), -- В Центральной библиотеке 12 экземпляров книги "Братья Карамазовы"
(1, 4, 10), -- В Центральной библиотеке 10 экземпляров книги "Война и мир"
(1, 5, 6);  -- В Центральной библиотеке 6 экземпляров книги "Анна Каренина"

INSERT INTO library_book (library_id, book_id, number_of_copies)
VALUES
(2, 1, 3),  -- В Районной библиотеке 3 экземпляра книги "Что делать?"
(2, 2, 2),  -- В Районной библиотеке 2 экземпляра книги "Идиот"
(2, 4, 7);  -- В Районной библиотеке 7 экземпляров книги "Война и мир"

SELECT lb.library_id, l.name AS library_name, b.title, b.author, lb.number_of_copies
FROM library_book lb
JOIN libraries l ON lb.library_id = l.id
JOIN books b ON lb.book_id = b.id
ORDER BY lb.library_id, lb.book_id;
-- Этот запрос выведет список всех книг с указанием, в какой библиотеке они находятся и в каком количестве

