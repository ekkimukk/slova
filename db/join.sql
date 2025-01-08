SELECT 
    visitors.name AS visitor_name,
    books.title AS book_title,
    books.author AS book_author,
    card_book.lend_date AS lend_date,
    libraries.name AS library_name
FROM 
    card_book
JOIN 
    books ON card_book.book_id = books.id
JOIN 
    cards ON card_book.card_id = cards.id
JOIN 
    visitors ON cards.id = visitors.card_id
JOIN 
    libraries ON cards.registration_place_id = libraries.id;
