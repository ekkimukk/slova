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
    author VARCHAR(45) NOT NULL,
    number_of_copies INT NOT NULL,
    year_of_publication INT NOT NULL
);

CREATE TABLE cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    registration_place_id INT NOT NULL,
    FOREIGN KEY (registration_place_id) REFERENCES libraries (id) ON DELETE CASCADE
);

CREATE TABLE visitors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    card_id INT NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards (id) ON DELETE CASCADE
);

CREATE TABLE card_book (
    card_id INT NOT NULL,
    book_id INT NOT NULL,
    return_date DATE NOT NULL,
    PRIMARY KEY (card_id, book_id, return_date),
    FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

