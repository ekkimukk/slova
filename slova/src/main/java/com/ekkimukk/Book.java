package com.ekkimukk;

import javax.swing.text.html.HTML;
import java.text.ParseException;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final int year_of_publication;
    private final String library_name;
    private final int number_of_copies;

    public Book(int id, String title, String author, int year_of_publication, String library_name, int number_of_copies) throws ParseException {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year_of_publication = year_of_publication;
        this.library_name = library_name;
        this.number_of_copies = number_of_copies;
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

    public int getYearOfPublication() {
        return year_of_publication;
    }

    public String getLibraryName() {
        return library_name;
    }

    public int getNumberOfCopies() {
        return number_of_copies;
    }
}

