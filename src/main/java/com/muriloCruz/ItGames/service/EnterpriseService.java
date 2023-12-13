package com.muriloCruz.ItGames.service;

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
		this.validateDuplication(enterprise);

        return enterpriseRepository.save(enterprise);
	}
	
	public Enterprise update(EnterpriseSaved enterprise) {
		Enterprise enterpriseFound = searchBy(enterprise.getId());
		enterpriseFound.setName(enterprise.getName());
		validateDuplication(enterpriseFound);
		return enterpriseRepository.save(enterpriseFound);
	}

	public void updateStatusBy(
			Long id, Status status) {
		Enterprise enterpriseFound = this.searchBy(id);
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found linked to the reported parameters");
		Preconditions.checkArgument(enterpriseFound.getStatus() != status ,
				"The status entered is already assigned");
		this.enterpriseRepository.updateStatusBy(id, status);
	}

	
	public Enterprise searchBy(Long id) {
		Enterprise enterpriseFound = enterpriseRepository.searchBy(id);
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found linked to the reported parameters");
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The enterprise informed is inactive");

		return enterpriseFound;
	}

	
	public Page<Enterprise> listBy(String name, Pageable pagination) {
		return this.enterpriseRepository.listBy(name, pagination);
	}

	
	public Enterprise deleteBy(Long id) {
		Enterprise enterpriseFound = searchBy(id);
		int numberLinkedGames = gameRepository.countGamesLinkedToThe(id);
		Preconditions.checkArgument(!(numberLinkedGames >= 1),
				"This enterprise is linked to '" + numberLinkedGames + "' games");
		this.enterpriseRepository.deleteBy(enterpriseFound.getId());
		return enterpriseFound;
	}

	private void validateDuplication(Enterprise enterprise) {
		Enterprise enterpriseFound = enterpriseRepository.searchBy(enterprise.getCnpj());

		if (enterpriseFound != null) {
			Preconditions.checkArgument(enterprise.isPersisted() && enterpriseFound.equals(enterprise),
					"There is already have a enterprise registered with this cnpj");
		}
	}

}
