package org.hl.wirtualnyregalbackend.user.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.model.BookGenre;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.Objects;
import java.util.Set;

import static org.hl.wirtualnyregalbackend.common.util.ValidationUtils.baseValidateString;

@Entity
@Table(name = "user_profile")
public class UserProfile extends UpdatableBaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id")
    private UserProfilePicture profilePicture;

    @ManyToMany
    @JoinTable(
            name = "user_book_genre_preferences",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "book_genre_id")
    )
    private Set<BookGenre> bookGenrePreferences;


    protected UserProfile() { }

    public UserProfile(String firstName, String lastName, String description, Set<BookGenre> bookGenrePreferences, User user) {
        this.firstName = baseValidateString(firstName, "firstName");
        this.lastName = baseValidateString(lastName, "lastName");
        this.description = description;
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.bookGenrePreferences = Objects.requireNonNull(bookGenrePreferences, "bookGenrePreferences cannot be null.");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public UserProfilePicture getProfilePicture() {
        return profilePicture;
    }

}
