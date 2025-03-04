package org.hl.wirtualnyregalbackend.book.model;

import org.hl.wirtualnyregalbackend.book.exception.InvalidBookIsbnException;

import java.util.Objects;

public class BookIsbn {

    private final String standardizedIsbn;
    private final BookIsbnType isbnType;

    public enum BookIsbnType {
        ISBN_10,
        ISBN_13,
    }


    public BookIsbn(String isbn) {
        if(isbn == null) throw new InvalidBookIsbnException("ISBN is null.");
        String tmpIsbn = removeHyphensFromIsbn(isbn);
        if(tmpIsbn.length() == 10) {
            isbnType = BookIsbnType.ISBN_10;
            standardizedIsbn = tmpIsbn;
        } else if (tmpIsbn.length() == 13) {
            Character c = isbn.charAt(isbn.length() - 1);
            if(c.equals('X')) throw new InvalidBookIsbnException("ISBN13 contains X character.");
            isbnType = BookIsbnType.ISBN_13;
            standardizedIsbn = tmpIsbn;
        } else {
            throw new InvalidBookIsbnException("ISBN must contains 10 or 13 characters.");
        }
    }

    private String removeHyphensFromIsbn(String isbn) {
        return isbn.replace("-", "");
    }

    public String getStandardizedIsbn() {
        return standardizedIsbn;
    }

    public BookIsbnType getIsbnType() {
        return isbnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookIsbn bookIsbn = (BookIsbn) o;
        return Objects.equals(standardizedIsbn, bookIsbn.standardizedIsbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(standardizedIsbn);
    }

    @Override
    public String toString() {
        return "BookIsbn{" +
                "isbn='" + standardizedIsbn + '\'' +
                ", isbnType=" + isbnType +
                '}';
    }

}
