package ru.otus.graduation.orders.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
public class JwtUser implements UserDetails {

    private final ObjectId id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String email;
    private final Date registrationDate;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public ObjectId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
