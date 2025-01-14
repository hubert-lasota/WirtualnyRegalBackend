package org.hl.wirtualnyregalbackend.infrastructure.bookshelf;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.application.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.hl.wirtualnyregalbackend.infrastructure.security.annotation.RequiresPermission;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {

    private final BookshelfService bookshelfService;

    public BookshelfController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @PostMapping
    public ResponseEntity<?> createBookshelfForCurrentUser(@Valid @RequestBody BookshelfRequest bookshelfRequest,
                                                           @AuthenticationPrincipal User user) {
        Long bookshelfId = bookshelfService.createBookshelf(bookshelfRequest, user);
        return ResponseEntity.ok(bookshelfId);
    }

    @PostMapping("/addBook")
    @RequiresPermission(resourceIdParamName = "bookshelfId")
    public ResponseEntity<?> addBookToBookshelf(@RequestParam Long bookshelfId,
                                                @RequestParam String bookId) {
        bookshelfService.addBookToBookshelf(bookshelfId, bookId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/removeBook")
    @RequiresPermission(resourceIdParamName = "bookshelfId")
    public ResponseEntity<?> removeBookFromBookshelf(@RequestParam Long bookshelfId,
                                                     @RequestParam Long bookId) {
        bookshelfService.removeBookFromBookshelf(bookshelfId, bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        Collection<BookshelfResponse> bookshelves = bookshelfService.findUserBookshelves(user.getId());
        return ResponseEntity.ok(bookshelves);
    }

}
