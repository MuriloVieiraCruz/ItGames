package com.muriloCruz.ItGames.service.impl;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseRequest;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseSaved;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EnterpriseRepository;
import com.muriloCruz.ItGames.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnterpriseService {
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;

	@Autowired
	private GameRepository gameRepository;
	
	public Enterprise insert(EnterpriseRequest enterpriseRequest) {
		Enterprise enterprise = new Enterprise();
		enterprise.setName(enterpriseRequest.getName());
		this.validatedDuplicity(enterprise);

		Enterprise enterpriseSaved = enterpriseRepository.save(enterprise);
		return enterpriseSaved;
	}
	
	public Enterprise update(EnterpriseSaved enterprise) {
		//Enterprise enterpriseFound = searchBy(enterprise.getId());
		return null;
	}

	public void updateStatusBy(Integer id, Status status) {
		Enterprise enterpriseFound = this.searchBy(id);
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found to be linked to the reported parameters");
		Preconditions.checkArgument(enterpriseFound.getStatus() != status ,
				"The status entered is already assigned");
		this.enterpriseRepository.updateStatusBy(id, status);
	}

	
	public Enterprise searchBy(Integer id) {
		Optional<Enterprise> optionalEnterprise = enterpriseRepository.findById(id);
		Preconditions.checkArgument(optionalEnterprise.isPresent(),
				"No enterprise was found to be linked to the reported parameters");
		Enterprise enterpriseFound = optionalEnterprise.get();
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The enterprise informed is inactive");

		return enterpriseFound;
	}

	
	public Page<Enterprise> listBy(String name, Pageable pagination) {
		return this.enterpriseRepository.listBy(name, pagination);
	}

	
	public Enterprise excludeBy(Integer id) {
		Enterprise enterpriseFound = searchBy(id);
		int numberLinkedGames = gameRepository.countGamesLinkedToThe(id);
		Preconditions.checkArgument(!(numberLinkedGames >= 1),
				"This enterprise is linked to '" + numberLinkedGames + "' games");
		this.enterpriseRepository.deleteById(enterpriseFound.getId());
		return enterpriseFound;
	}

	private void validatedDuplicity(Enterprise enterprise) {
		Enterprise enterpriseFound = enterpriseRepository.searchBy(enterprise.getCnpj());

		if (enterpriseFound != null) {
			Preconditions.checkArgument(enterprise.isPersisted() && enterpriseFound.equals(enterprise),
					"There is already have a enterprise registered with this name");
		}
	}

}
