package org.hl.wirtualnyregalbackend.security.permission_checker;

import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.security.exception.PermissionDeniedException;
import org.hl.wirtualnyregalbackend.security.model.AuthorityType;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.stereotype.Component;

@Component
public class BookPermissionChecker implements PermissionChecker {

    private final BookRepository bookRepository;

    public BookPermissionChecker(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void hasPermission(Object resourceId, ActionType actionType, User user) throws PermissionDeniedException {
        if(actionType.equals(ActionType.CREATE)) {
            return;
        }

        boolean isAdmin = PermissionUtils.hasUserAuthority(user, AuthorityType.ADMIN);
        if (isAdmin) {
            return;
        }

//        Long bookshelfId = (Long) resourceId;
//        boolean isAuthor = bookshelfRepository.isUserBookshelfAuthor(bookshelfId, user.getId());
//        if(!isAuthor) {
//            throw new PermissionDeniedException(ResourceType.BOOKSHELF, actionType);
//        }
    }
}
