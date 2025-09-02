package com.movie.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.booking.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
}
