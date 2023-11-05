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
public class AccessCredential implements UserDetails {

    private String login;

    private String password;

    private List<GrantedAuthority> permissions;

    public AccessCredential(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.permissions = new ArrayList<GrantedAuthority>();
        this.permissions.add(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getPermissions();
    }

    @Override
    public String getPassword() {
        return getPassword();
    }

    @Override
    public String getUsername() {
        return getLogin();
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
