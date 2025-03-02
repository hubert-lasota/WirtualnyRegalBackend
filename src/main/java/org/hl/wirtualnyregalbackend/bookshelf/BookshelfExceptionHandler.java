package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.exception.InvalidBookshelfTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class BookshelfExceptionHandler {

    @ExceptionHandler(InvalidBookshelfTypeException.class)
    public ResponseEntity<?> handleInvalidBookshelfTypeException(InvalidBookshelfTypeException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problemDetail.setProperty("type", e.getInvalidType());
        problemDetail.setProperty("validTypes", e.getValidTypes());
        return new ResponseEntity<>(problemDetail, status);
    }

}
