package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.UserRequestDto;
import com.muriloCruz.ItGames.dto.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProxy implements UserService{

    @Autowired
    @Qualifier("userServiceProxy")
    private UserService service;

    @Override
    public User insert(UserRequestDto userRequestDto) {
        return service.insert(userRequestDto);
    }

    @Override
    public User update(UserSavedDto userSavedDto) {
        return service.update(userSavedDto);
    }

    @Override
    public Page<User> listBy(String login, Pageable pagination) {
        return service.listBy(login, pagination);
    }

    @Override
    public User searchBy(Integer id) {
        return service.searchBy(id);
    }

    @Override
    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    @Override
    public User excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
