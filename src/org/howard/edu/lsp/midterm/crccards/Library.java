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
    /** The maximum number of books that may be borrowed at the same time. */
    private static final int MAX_BORROWED = 3;

    /** All books in the library, keyed by ISBN and kept in sorted order. */
    private final Map<String, Book> books = new TreeMap<>();

    // addBook, findBook, borrowBook, returnBook, getAvailableBooks,
    // getBooksByAuthor, getBooksSortedByIsbn, and getBorrowedBookCount
    // are unchanged; their Javadoc is already complete.

    /**
     * Looks up a book by ISBN and fails if it does not exist. Used internally
     * so that borrowing and returning share the same validation.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book (never null)
     * @throws IllegalArgumentException if no book has that ISBN
     */
    private Book requireBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN: " + isbn);
        }
        return book;
    }
}