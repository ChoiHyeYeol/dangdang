package com.smher.boardapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smher.boardapp.entity.Board;
										// Entity, Entity의 pk자료형
public interface BoardRepository extends JpaRepository<Board, Integer> {
	

}
