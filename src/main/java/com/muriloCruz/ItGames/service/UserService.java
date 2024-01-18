package com.muriloCruz.ItGames.service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.user.UserRequestDto;
import com.muriloCruz.ItGames.dto.user.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public User insert(UserRequestDto userRequestDto);

    public User update(UserSavedDto userSavedDto);

    public Page<User> listBy(String name, Pageable paginacao);

    public User searchBy(Long id);

    public void updateStatusBy(Long id, Status status);

}
