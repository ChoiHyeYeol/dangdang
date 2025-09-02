package com.smher.boardapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.smher.boardapp.entity.Board;
import com.smher.boardapp.service.BoardService;

@Controller
@RequestMapping("/api/boards") // http://localhost:8088/api/boards
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	// 게시글 조회 
	@GetMapping 
	public String getAllBoards(Model m) {
		
		// db에 있는 게시글 정보를 불러오기!
		List<Board> boardList = boardService.getAllBoards();
		
		// 객체 바인딩
		m.addAttribute("boardList", boardList);
		return "boardmain"; // viewname  작성
	}
	
	// 게시글 등록 페이지
	@GetMapping("new") // http://localhost:8088/api/boards/new
	public String createBoard() {
		return "boardcreate";
	}
	
	// 게시글 등록
	@PostMapping // http://localhost:8088/api/boards 
	public String createBoard(Board board, // 내성 : title, writer, content
								@RequestPart("file") MultipartFile file) {
		
		boardService.createBoard(board, file);
		
		return "redirect:/api/boards";
	}

}
