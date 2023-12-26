package com.muriloCruz.ItGames.security;

import com.muriloCruz.ItGames.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class AccessCredentials implements UserDetails {

    private String email;
    private String psswrd;
    private List<GrantedAuthority> roles;

    public AccessCredentials(User user) {
        this.email = user.getEmail();
        this.psswrd = user.getPassword();
        this.roles = new ArrayList<GrantedAuthority>();
        this.roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return getPsswrd();
    }

    @Override
    public String getUsername() {
        return getEmail();
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
}
