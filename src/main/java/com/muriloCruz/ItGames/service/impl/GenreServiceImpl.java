package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
	
	@Autowired
	private GenreRepository genreRepository;

	@Override
	public Genre insert(Genre genre) {
		Genre genreFound = genreRepository.searchBy(genre.getName());
		if (genreFound != null) {
			if (genre.isPersisted()) {
				Preconditions.checkArgument(genreFound.equals(genre),
						"There is already a registered genre with this name");
			}
		}
		Genre genreSaved = genreRepository.save(genre);
		return genreSaved;
	}

	@Override
	public Genre searchBy(Integer id) {
		Genre genreFound = genreRepository.findById(id).get();
		Preconditions.checkNotNull(genreFound,
				"No genre was found linked to the parameters entered");
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre informed is inactive");
		return genreFound;
	}

	@Override
	public Page<Genre> listBy(String name, Pageable paginacao) {
		return this.genreRepository.listBy(name + "%", paginacao);
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Genre genreFound = genreRepository.findById(id).get();
		Preconditions.checkNotNull(genreFound,
				"No genre was found linked to the parameters entered");
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre informed is inactive");
		this.genreRepository.updateStatusBy(id, status);
	}

	@Override
	public Genre excludeBy(Integer id) {
		Genre genreFound = genreRepository.findById(id).get();
		Preconditions.checkNotNull(genreFound,
				"No genre was found linked to the parameters entered");
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre informed is inactive");
		this.genreRepository.deleteById(genreFound.getId());
		return genreFound;
	}

}
