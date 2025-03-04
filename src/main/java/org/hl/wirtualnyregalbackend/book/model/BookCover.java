package org.hl.wirtualnyregalbackend.book.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.ActionResult;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;

import java.util.Objects;

import static org.hl.wirtualnyregalbackend.common.util.ValidationUtils.validateStringAndReturnResult;

@Entity
@Table(name = "book_cover")
public class BookCover extends UpdatableBaseEntity {

    @Column(name = "cover_url")
    private String coverUrl;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    protected BookCover() { }

    public BookCover(String coverUrl, Book book) {
        Objects.requireNonNull(coverUrl, "Cover url cannot be null");
        Objects.requireNonNull(book, "Book cannot be null");
        this.coverUrl = coverUrl;
        this.book = book;
    }

    public ActionResult updateCoverUrl(String coverUrl) {
        ActionResult result = validateStringAndReturnResult(coverUrl, "coverUrl");
        if(result.success()) {
            this.coverUrl = coverUrl;
        }
        return result;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

}
