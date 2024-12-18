package com.ekkimukk;

import javax.swing.text.html.HTML;
import java.text.ParseException;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int number_of_copies;
    private final int year_of_publication;

    public Book(int id, String title, String author, int number_of_copies, int year_of_publication) throws ParseException {
        this.id = id;
        this.title = title;
        this.author = author;
        this.number_of_copies = number_of_copies;
        this.year_of_publication = year_of_publication;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfCopies() {
        return number_of_copies;
    }

    public int getYearOfPublication() {
        return year_of_publication;
    }
}

