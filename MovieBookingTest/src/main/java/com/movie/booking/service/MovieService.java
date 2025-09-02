package com.movie.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.booking.entity.Movie;
import com.movie.booking.repository.MovieRepository;



@Service
public class MovieService {
	@Autowired
	MovieRepository movieRepository;
	
	public List<Movie> findAllMovies() {
		
		List<Movie> MovieList = movieRepository.findAll();
		
		return MovieList;
	}
}
