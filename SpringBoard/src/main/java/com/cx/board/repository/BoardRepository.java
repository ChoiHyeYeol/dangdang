package com.cx.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cx.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	List<BoardEntity> findByTitleContaining(String keyword);
	
	@Query("select b from BoardEntity b where b.content like %:keyword%")
	List<BoardEntity> seaechContent(String keyword);
	
	List<BoardEntity> findByWriter(String keyword);
}
