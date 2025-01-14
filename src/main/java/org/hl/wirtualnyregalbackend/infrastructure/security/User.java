package org.hl.wirtualnyregalbackend.infrastructure.security;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.hl.wirtualnyregalbackend.application.common.ValidationUtils.baseValidateString;

@Entity
@Table(name = "users")
public class User extends UpdatableBaseEntity implements UserDetails {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();


    protected User() { }

    public User(String username, String password, Authority... authorities) {
        Objects.requireNonNull(authorities, "Authorities cannot be null.");
        this.authorities.addAll(List.of(authorities));
        this.username = baseValidateString(username, "username");
        this.password = baseValidateString(password, "password");
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '}';
    }

}
