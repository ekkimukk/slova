DELIMITER $$

CREATE TRIGGER update_can_borrow
AFTER INSERT OR UPDATE ON card_book
FOR EACH ROW
BEGIN
    DECLARE overdue_books_count INT;

    -- Проверяем, есть ли просроченные книги для текущей карты
    SELECT COUNT(*)
    INTO overdue_books_count
    FROM card_book
    WHERE card_id = NEW.card_id
      AND DATEDIFF(CURDATE(), lend_date) > 30;

    -- Если есть просроченные книги, запрещаем выдачу новых
    IF overdue_books_count > 0 THEN
        UPDATE cards
        SET can_borrow = FALSE
        WHERE id = NEW.card_id;
    ELSE
        UPDATE cards
        SET can_borrow = TRUE
        WHERE id = NEW.card_id;
    END IF;
END$$

DELIMITER ;
