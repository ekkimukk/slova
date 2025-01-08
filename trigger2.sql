DELIMITER $$

CREATE TRIGGER prevent_new_lend
BEFORE INSERT ON card_book
FOR EACH ROW
BEGIN
    DECLARE can_borrow_flag BOOLEAN;

    -- Проверяем, можно ли брать книги по текущей карте
    SELECT can_borrow
    INTO can_borrow_flag
    FROM cards
    WHERE id = NEW.card_id;

    -- Если нельзя, запрещаем выдачу
    IF can_borrow_flag = FALSE THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot lend new books: this card is blocked due to overdue books';
    END IF;
END$$

DELIMITER ;

