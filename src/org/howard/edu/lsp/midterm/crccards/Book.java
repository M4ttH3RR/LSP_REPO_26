package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a book in the library lending system. A Book stores its
 * identifying information and controls its own availability.
 *
 * @author Matthew Herriman
 */
public class Book {
    /** The book's unique ISBN; it never changes after creation. */
    private final String isbn;

    /** The title of the book. */
    private final String title;

    /** The author of the book. */
    private final String author;

    /** True if the book is on the shelf, false if it is currently borrowed. */
    private boolean available;

    /**
     * Creates a new Book. Every new book starts out available.
     *
     * @param isbn   the unique ISBN identifying the book
     * @param title  the title of the book
     * @param author the author of the book
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns the book's ISBN.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the book's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the book's author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Indicates whether the book can currently be borrowed.
     *
     * @return true if the book is available, false if it is borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Marks the book as borrowed.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        available = false;
    }

    /**
     * Marks the book as returned and available again.
     *
     * @throws IllegalStateException if the book is not currently borrowed
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book " + isbn + " is not borrowed.");
        }
        available = true;
    }

    /**
     * Returns a readable description of the book, including its ISBN, title,
     * author, and current availability status.
     *
     * @return a string such as "123 Dune by Frank Herbert [AVAILABLE]"
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author
                + (available ? " [AVAILABLE]" : " [BORROWED]");
    }
}