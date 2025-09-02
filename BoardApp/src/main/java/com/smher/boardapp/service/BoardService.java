package com.smher.boardapp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smher.boardapp.entity.Board;
import com.smher.boardapp.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;

	public void createBoard(Board board, MultipartFile file) {
		// 랜덤 문자열 -> 중복 방지 
		String ranStr = UUID.randomUUID().toString();
		// 새로운 파일명 = 랜점 문자열 + 파일이름
		String newFileName = ranStr+file.getOriginalFilename();
		
		try {
			file.transferTo(new File(newFileName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		board.setFilePath(newFileName);
		boardRepository.save(board);
		
	}

	public List<Board> getAllBoards() {
		
		return boardRepository.findAll();
	}

}
