package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.dto.enterprise.EnterpriseRequest;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseSaved;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnterpriseService {

    public Enterprise insert(EnterpriseRequest enterprise);

    public Enterprise update(EnterpriseSaved enterprise);

    public Enterprise searchBy(Long id);

    public Page<Enterprise> listBy(String name, Pageable pagination);

    public void updateStatusBy(Long id, Status status);

    public Enterprise deleteBy(Long id);
}
