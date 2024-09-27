package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;

	@Autowired
	private ReviewService reviewService;

	@PreAuthorize("hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findAll(
			@RequestParam(value = "genreId", defaultValue = "0") String genreId, Pageable pageable) {
		Page<MovieCardDTO> dto = service.findAllPaged(genreId, pageable);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
		MovieDetailsDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
	@GetMapping("/{movieId}/reviews")
	public ResponseEntity<Page<ReviewDTO>> getReviews(@PathVariable Long movieId, Pageable pageable) {
		Page<ReviewDTO> reviews = reviewService.findByMovieId(movieId, pageable);
		return ResponseEntity.ok(reviews);
	}

}
