DELIMITER //

CREATE PROCEDURE AddLendRecord(
    IN book_id INT,
    IN card_id INT,
    IN lend_date DATE
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM books WHERE id = book_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Книга с указанным идентификатором не существует.';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM cards WHERE id = card_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Читательский билет с указанным идентификатором не существует.';
    END IF;

    IF (SELECT number_of_copies FROM books WHERE id = book_id) <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Нет доступных экземпляров книги.';
    END IF;

    INSERT INTO card_book (book_id, card_id, lend_date)
    VALUES (book_id, card_id, lend_date);

    UPDATE books
    SET number_of_copies = number_of_copies - 1
    WHERE id = book_id;

    SELECT 'Запись о выдаче книги успешно добавлена.' AS result;
END //

DELIMITER ;
