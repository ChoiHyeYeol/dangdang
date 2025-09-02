package kr.smhrd.movieapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.smhrd.movieapp.entity.Movie;
import kr.smhrd.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;

@Controller  // -> Spring 컨테이너 안에 MovieController라는 객체가 저장(적재)된다!
@RequiredArgsConstructor 
public class MovieController {
	
	@Autowired // 순환 참조 문제가 발생한다! 
	private final MovieService movieService;
	
	@GetMapping("/movies")
	public String movieList(Model model) {
		// model의 역할
		// -> request라는 객체 안에 있는 객체
		
		// model : 화면에 데이터를 전달하기 위해서 사용하는 객체
		// 화면에 전달할 때
		
		// Spring, Spring boot -> 응답 방식 forward
		
		// 타임리프,jstl ${}
		
		// 영화 리스트에 대한 정보를 가지고 화면에 돌아가야한다!
		// 1. DB 접근 기능을 가진 repository를 호출
		// 2. 로직을 처리할 수 있는 service르 호출
		List<Movie> movieList = movieService.findAllMovies();
		
		model.addAttribute("movies", movieList);
		
		return "movieList";
		// templates/movieList.html
		
	}

}
