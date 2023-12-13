package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.enterprise.EnterpriseRequest;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseSaved;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.impl.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceProxy {

    @Autowired
    private EnterpriseService service;

    
    public Enterprise insert(EnterpriseRequest enterprise) {
        return service.insert(enterprise);
    }

    public Enterprise update(EnterpriseSaved enterprise) {
        return service.update(enterprise);
    }

    public Enterprise searchBy(Integer id) {
        return service.searchBy(id);
    }

    public Page<Enterprise> listBy(String name, Pageable pagination) {
        return service.listBy(name, pagination);
    }

    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    public Enterprise excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
