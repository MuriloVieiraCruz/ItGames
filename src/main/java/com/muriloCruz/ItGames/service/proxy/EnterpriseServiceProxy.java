package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.enterprise.EnterpriseRequest;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseSaved;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.EnterpriseService;
import com.muriloCruz.ItGames.service.impl.EnterpriseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceProxy implements EnterpriseService {

    @Autowired
    @Qualifier("enterpriseServiceImpl")
    private EnterpriseService service;


    @Override
    public Enterprise insert(EnterpriseRequest enterprise) {
        return service.insert(enterprise);
    }

    @Override
    public Enterprise update(EnterpriseSaved enterprise) {
        return service.update(enterprise);
    }

    @Override
    public Enterprise searchBy(Long id) {
        return service.searchBy(id);
    }

    @Override
    public Page<Enterprise> listBy(String name, Pageable pagination) {
        return service.listBy(name, pagination);
    }

    @Override
    public void updateStatusBy(Long id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    @Override
    public Enterprise deleteBy(Long id) {
        return service.deleteBy(id);
    }
}
