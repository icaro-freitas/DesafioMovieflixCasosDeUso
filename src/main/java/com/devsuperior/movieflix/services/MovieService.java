package com.devsuperior.movieflix.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPaged(String genreId, Pageable pageable) {

		List<Long> genreIds = Arrays.asList();
		if (!"0".equals(genreId)) {
			genreIds = Arrays.asList(genreId.split(",")).stream().map(Long::parseLong).toList();
		}

		if (genreIds == null || genreIds.isEmpty()) {
			genreIds = null;
		}

		return repository.searchAll(genreIds, pageable).map(x -> new MovieCardDTO(x));

	}

}
