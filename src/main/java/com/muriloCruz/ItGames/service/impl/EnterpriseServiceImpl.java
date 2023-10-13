package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EnterpriseRepository;
import com.muriloCruz.ItGames.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;

	@Override
	public Enterprise insert(Enterprise enterprise) {
		Enterprise enterpriseFound = enterpriseRepository.searchBy(enterprise.getName());
		if (enterpriseFound != null) {
			if (enterprise.isPersisted()) {
				Preconditions.checkArgument(enterpriseFound.equals(enterprise),
						"There is already a enterprise registered with this name");
			}
		}
		Enterprise enterpriseSaved = enterpriseRepository.saveAndFlush(enterprise);
		return enterpriseSaved;
	}

	@Override
	public Enterprise searchBy(Integer id) {
		Enterprise enterpriseFound = enterpriseRepository.findById(id).get();
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found linked to the parameters entered");
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The enterprise informed is inactive");
		return enterpriseFound;
	}

	@Override
	public Page<Enterprise> listBy(String name, Pageable pagination) {
		return this.enterpriseRepository.listBy(name + "%", pagination);
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Enterprise enterpriseFound = this.enterpriseRepository.findById(id).get();
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found linked to the parameters entered");
		Preconditions.checkArgument(enterpriseFound.getStatus() != status ,
				"The status entered is already assigned");
		this.enterpriseRepository.updateStatusBy(id, status);
	}

	@Override
	public Enterprise excludeBy(Integer id) {
		Enterprise enterpriseFound = enterpriseRepository.findById(id).get();
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found linked to the parameters entered");
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The enterprise informed is inactive");
		this.enterpriseRepository.deleteById(enterpriseFound.getId());
		return enterpriseFound;
	}

}
