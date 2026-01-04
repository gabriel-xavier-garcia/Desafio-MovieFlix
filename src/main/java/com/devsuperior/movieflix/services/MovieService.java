package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable){

        Long genreFilter = (genreId == 0) ? null : genreId;

        Page<Movie> page = repository.searchMovieByGenre(genreFilter, pageable);
        return page.map(MovieCardDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById (Long id){

        Movie result = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        return new MovieDetailsDTO(result);
    }
}
