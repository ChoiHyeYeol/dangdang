package com.movie.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.movie.booking.entity.Movie;
import com.movie.booking.service.MovieService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {
	
	@Autowired
	private final MovieService movieService;
	
	@GetMapping("/movies")
	public String MovieList(Model model) {

		List<Movie> MovieList = movieService.findAllMovies();
		model.addAttribute("movies", MovieList);
		return "MovieList";

		
	}

}
