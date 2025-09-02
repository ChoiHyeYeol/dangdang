package kr.smhrd.movieapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.smhrd.movieapp.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	// 전체 영화 리스트 조회
	
	
	// ??? 왜 전부 다 class인데 레파지토리만 인터페이스 인가요?
	// Mapper 인터페이스와 동일한 이유
	
	// JPA, Mybatis -> JDBC 기능을 대신 수행해줄 수 있도록 하는 프레임워크
	// 구현체들이 알아서 인터체이스를 상속 받아 데이터베이스에 접근한다!
	// Hibernate / SqlSessionFactoryBean
}
