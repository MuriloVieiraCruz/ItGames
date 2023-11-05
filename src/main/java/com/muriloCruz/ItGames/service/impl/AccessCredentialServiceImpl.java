package com.muriloCruz.ItGames.service.impl;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.repository.UserRepository;
import com.muriloCruz.ItGames.security.AccessCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccessCredentialServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.searchBy(login);
        Preconditions.checkNotNull(user,
                "The user doens't exist");
        return new AccessCredential(user);
    }
}
