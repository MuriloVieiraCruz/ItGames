package com.muriloCruz.ItGames.service.impl;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.repository.UserRepository;
import com.muriloCruz.ItGames.security.AccessCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccessCredentialsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = repository.searchBy(userLogin);
        Preconditions.checkNotNull(user,
                "No user was found linked to the reported parameters");
        return new AccessCredentials(user);
    }
}
