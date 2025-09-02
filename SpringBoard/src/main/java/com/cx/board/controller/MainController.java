package com.cx.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cx.board.entity.BoardEntity;
import com.cx.board.service.BoardService;

@Controller
public class MainController {
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<BoardEntity> list = boardService.show();
		
		model.addAttribute("boardList", list);
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "register";
	}
	
	@GetMapping("/write")
	public String write() {
		return "write";
	}
}
