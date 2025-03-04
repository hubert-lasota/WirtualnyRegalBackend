package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.bookshelf.dao.BookshelfRepository;
import org.hl.wirtualnyregalbackend.bookshelf.exception.BookshelfNotFoundException;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final BookService bookService;

    public BookshelfService(BookshelfRepository bookshelfRepository, @Lazy BookService bookService) {
        this.bookshelfRepository = bookshelfRepository;
        this.bookService = bookService;
    }


    public Long createBookshelf(BookshelfRequest bookshelfRequest, User user) {
        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfRequest, user);
        return bookshelfRepository.save(bookshelf).getId();
    }

    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        bookshelvesToSave.add(new Bookshelf("Do przeczytania", "to_read", "", user));
        bookshelvesToSave.add(new Bookshelf("W trakcie czytania", "reading", "", user));
        bookshelvesToSave.add(new Bookshelf("Przeczytane", "read", "", user));
        bookshelfRepository.saveAll(bookshelvesToSave);
    }

    public void addBookToBookshelf(Long bookshelfId, String bookId) {
        Bookshelf bookshelf = bookshelfRepository.findWithBooksById(bookshelfId);
        Book book = bookService.findBookById(bookId);
        bookshelf.addBook(book);
        bookshelfRepository.save(bookshelf);
    }

    public void removeBookFromBookshelf(Long bookshelfId, Long bookId) {
        Bookshelf bookshelf = bookshelfRepository.findWithBooksById(bookshelfId);
        bookshelf.removeBook(bookId);
        bookshelfRepository.save(bookshelf);
    }

    public List<BookshelfResponse> findUserBookshelves(Long userId) {
        List<Bookshelf> bookshelves = bookshelfRepository.findByUserId(userId);
        if(bookshelves.isEmpty()) {
            throw new BookshelfNotFoundException("User with id: %d has no bookshelves".formatted(userId));
        }

        return bookshelves.stream()
                .map(BookshelfMapper::toBookshelfResponse)
                .toList();
    }

    public List<Bookshelf> findUserBookshelvesByBookId(Long bookId, User user) {
        return bookshelfRepository.findUserBookshelvesByBookId(bookId, user.getId());
    }

}
