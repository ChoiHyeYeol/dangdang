package kr.smhrd.movieapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.movieapp.entity.Movie;
import kr.smhrd.movieapp.repository.MovieRepository;

@Service
public class MovieService {
	
		@Autowired
		MovieRepository movieRepository; //  = Hibernate
		
		public List<Movie> findAllMovies() {
			
			// 데이터를 처리하는 공간
			// repository만 호출
			// 영화의 리스트를 반환
			
			List<Movie> movieList = movieRepository.findAll();
			
			return movieList;
			
			
		}
	
}
