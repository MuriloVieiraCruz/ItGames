package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.repository.GenreGameRepository;
import com.muriloCruz.ItGames.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GenreService;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
	
	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public Genre insert(Genre genre) {
		Genre genreFound = genreRepository.searchBy(genre.getName());
		if (genreFound != null) {
				Preconditions.checkArgument(genre.isPersisted() && genreFound.equals(genre),
						"There is already a registered genre with this name");
		}
		Genre genreSaved = genreRepository.save(genre);
		return genreSaved;
	}

	@Override
	public Genre searchBy(Integer id) {
		Optional<Genre> optionalGenre = genreRepository.findById(id);
		Preconditions.checkArgument(optionalGenre.isPresent(),
				"No genre was found linked to the parameters entered");
		Genre genreFound = optionalGenre.get();
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre informed is inactive");
		return genreFound;
	}

	@Override
	public Page<Genre> listBy(String name, Pageable pagination) {
		return this.genreRepository.listBy(name, pagination);
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Genre genreFound = this.searchBy(id);
		Preconditions.checkNotNull(genreFound,
				"No gender was found to be linked to the reported parameters");
		Preconditions.checkArgument(genreFound.getStatus() != status ,
				"The status entered is already assigned");
		this.genreRepository.updateStatusBy(id, status);
	}

	@Override
	public Genre excludeBy(Integer id) {
		Genre genreFound = searchBy(id);
		int numberLinkedGenres = genreGameRepository.countByGenre(id);
		Preconditions.checkArgument(!(numberLinkedGenres >= 1),
				"This genre is linked to '" + numberLinkedGenres + "' games");
		this.genreRepository.deleteById(genreFound.getId());
		return genreFound;
	}

}
