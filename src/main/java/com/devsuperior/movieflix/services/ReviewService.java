package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private AuthService authService;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		Review entity = new Review();

		entity.setText(dto.getText());

		Movie movie = movieRepository.getReferenceById(dto.getMovieId());

		entity.setMovie(movie);

		User user = authService.authenticated();

		entity.setUser(user);

		entity = repository.save(entity);

		return new ReviewDTO(entity);
	}
	
	@Transactional(readOnly = true)		
	public Page<ReviewDTO> findByMovieId(Long id, Pageable pageable) {
		if(!movieRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		Page<Review> page = repository.findByMovieId(id,pageable);
				
		return page.map(x->new ReviewDTO(x));
	}

}
