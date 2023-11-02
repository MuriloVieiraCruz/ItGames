package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.EnterpriseService;
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
    public Enterprise insert(Enterprise enterprise) {
        return service.insert(enterprise);
    }

    @Override
    public Enterprise searchBy(Integer id) {
        return service.searchBy(id);
    }

    @Override
    public Page<Enterprise> listBy(String name, Pageable pagination) {
        return service.listBy(name, pagination);
    }

    @Override
    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    @Override
    public Enterprise excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
