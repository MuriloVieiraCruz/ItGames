package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.user.UserRequestDto;
import com.muriloCruz.ItGames.dto.user.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProxy {

    @Autowired
    private UserService service;
    
    public User insert(UserRequestDto userRequestDto) {
        return service.insert(userRequestDto);
    }
    
    public User update(UserSavedDto userSavedDto) {
        return service.update(userSavedDto);
    }
    
    public Page<User> listBy(String login, Pageable pagination) {
        return service.listBy(login, pagination);
    }
    
    public User searchBy(Integer id) {
        return service.searchBy(id);
    }

    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }
}
