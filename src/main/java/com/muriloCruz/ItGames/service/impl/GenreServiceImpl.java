package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.dto.genre.GenreRequest;
import com.muriloCruz.ItGames.dto.genre.GenreSaved;
import com.muriloCruz.ItGames.repository.GenreGameRepository;
import com.muriloCruz.ItGames.repository.GenreRepository;
import com.muriloCruz.ItGames.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;

@Service
public class GenreServiceImpl implements GenreService {
	
	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public Genre insert(GenreRequest genreRequest) {
		Genre genre = new Genre();
		genre.setName(genreRequest.getName());
		validateDuplication(genre);
        return genreRepository.save(genre);
	}

	@Override
	public Genre update(GenreSaved genreSaved) {
		Genre genre = genreRepository.searchBy(genreSaved.getId());
		genre.setName(genreSaved.getName());
		validateDuplication(genre);
		return genreRepository.save(genre);
	}

	@Override
	public void updateStatusBy(Long id, Status status) {
		Genre genreFound = genreRepository.searchBy(id);
		Preconditions.checkNotNull(genreFound,
				"No gender was found to be linked to the reported parameters");
		Preconditions.checkArgument(genreFound.getStatus() != status ,
				"The status entered is already assigned");
		this.genreRepository.updateStatusBy(id, status);
	}

	@Override
	public Genre searchBy(Long id) {
		Genre genreFound = genreRepository.searchBy(id);
		Preconditions.checkNotNull(genreFound,
				"No genre was found linked to the parameters entered");
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre informed is inactive");
		return genreFound;
	}

	@Override
	public Page<Genre> listBy(String name, Pageable pagination) {
		return this.genreRepository.listBy(name, pagination);
	}

	@Override
	public void deleteBy(Long id) {
		Genre genreFound = searchBy(id);
		int numberLinkedGenres = genreGameRepository.countByGenre(id);
		Preconditions.checkArgument(numberLinkedGenres <= 1,
				"This genre is linked to '" + numberLinkedGenres + "' games");
		this.genreRepository.deleteBy(genreFound.getId());
	}

	private void validateDuplication(Genre genre) {
		Genre genreFound = genreRepository.searchBy(genre.getName());

		if (genreFound != null) {
			Preconditions.checkArgument(genre.isPersisted() && genreFound.equals(genre),
					"There is already have a genre registered with this name");
		}
	}
}
