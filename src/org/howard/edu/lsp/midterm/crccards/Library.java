package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Maintains a collection of {@link Book} objects and coordinates borrowing
 * and returning, enforcing a limit of three borrowed books at a time.
 * Books are stored in a {@link TreeMap} keyed by ISBN, which gives fast
 * lookup by ISBN, prevents duplicate ISBNs, and keeps keys in ascending
 * lexicographic order.
 *
 * @author Matthew Herriman
 */
public class Library {
    private static final int MAX_BORROWED = 3;

    private final Map<String, Book> books = new TreeMap<>();

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if the book is null or its ISBN already exists
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book, or null if none exists
     */
    public Book findBook(String isbn) {
        if (isbn == null) {
            return null;
        }
        return books.get(isbn);
    }

    /**
     * Borrows a book, enforcing the three-book limit.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is already borrowed or the limit is reached
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        if (getBorrowedBookCount() >= MAX_BORROWED) {
            throw new IllegalStateException("Borrowing limit of " + MAX_BORROWED + " reached.");
        }
        book.borrowBook();
    }

    /**
     * Returns a borrowed book to the library.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        requireBook(isbn).returnBook();
    }

    /**
     * Returns all available books.
     *
     * @return a new list of available books (empty if none)
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.isAvailable()) {
                result.add(b);
            }
        }
        return result;
    }

    /**
     * Returns all books by the given author (exact, case-sensitive match),
     * including borrowed books.
     *
     * @param author the author name
     * @return a new list of matching books (empty if none)
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getAuthor() != null && b.getAuthor().equals(author)) {
                result.add(b);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographic order,
     * regardless of borrowing status. Does not modify the library.
     *
     * @return a new sorted list (empty if the library is empty)
     */
    public List<Book> getBooksSortedByIsbn() {
        return new ArrayList<>(books.values());
    }

    /**
     * Returns the number of books currently borrowed.
     *
     * @return the borrowed book count
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book b : books.values()) {
            if (!b.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    private Book requireBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN: " + isbn);
        }
        return book;
    }
}