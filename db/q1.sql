USE library_db;

CREATE TABLE libraries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE workers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    job_place_id INT NOT NULL,
    FOREIGN KEY (job_place_id) REFERENCES libraries (id) ON DELETE CASCADE
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    -- number_of_copies INT NOT NULL,
    year_of_publication INT NOT NULL
);

CREATE TABLE cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    registration_place_id INT NOT NULL,
    can_borrow BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (registration_place_id) REFERENCES libraries (id) ON DELETE CASCADE
);

CREATE TABLE visitors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    card_id INT NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards (id) ON DELETE CASCADE
);

CREATE TABLE card_book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    card_id INT NOT NULL,
    lend_date DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

CREATE TABLE library_book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    library_id INT NOT NULL,
    number_of_copies INT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (library_id) REFERENCES libraries(id)
);

CREATE TABLE book_transfers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    from_library_id INT NOT NULL,
    to_library_id INT NOT NULL,
    transfer_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES library_book (id),
    FOREIGN KEY (from_library_id) REFERENCES libraries (id),
    FOREIGN KEY (to_library_id) REFERENCES libraries (id)
);

