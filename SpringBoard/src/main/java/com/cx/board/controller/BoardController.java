package com.cx.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cx.board.config.FileUploadConfig;
import com.cx.board.entity.BoardEntity;
import com.cx.board.entity.UserEntity;
import com.cx.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	
	private final FileUploadConfig fileUploadConfig;
	
	BoardController(FileUploadConfig fileUploadConfig) {
		this.fileUploadConfig = fileUploadConfig;
	}
	
	@PostMapping("/write")
	public String write(@RequestParam String title, @RequestParam String content, HttpSession session,
			@RequestParam MultipartFile image) {
		
		String imgPath = "";
		
		if (!image.isEmpty()) {
			String img_name = image.getOriginalFilename();
			
			String file_name = UUID.randomUUID() + "_" + img_name;
			
			String uploadDir = fileUploadConfig.getUploadDir();
			
			String filePath = Paths.get(uploadDir, file_name).toString();
			System.out.println(filePath);
			try {
				image.transferTo(new File(filePath));
				
				imgPath = "/uploads/" + file_name;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		BoardEntity entity = new BoardEntity();
		entity.setTitle(title);
		entity.setContent(content);
		entity.setImgPath(imgPath);
		
		
		UserEntity user = (UserEntity)session.getAttribute("user");
		
		String writer = user.getUserId();
		
		entity.setWriter(writer);
		
		BoardEntity result = boardService.write(entity);
		if(result != null) {
			return "redirect:/";
		}else {
			return "redirect:/board/write";
		}
		
		
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		System.out.println(id);
		
		Optional<BoardEntity> entity = boardService.detail(id);
		model.addAttribute("detail", entity.get());
		System.out.println(entity.get().getImgPath());
		return "detail";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Optional<BoardEntity> entity = boardService.detail(id);
		
		model.addAttribute("edit", entity.get());
		
		return "edit";
	}
	
	@PostMapping("/update")
	public String update(@RequestParam Long id, @RequestParam String title,
			@RequestParam String content, @RequestParam String oldImgPath,
			@RequestParam MultipartFile image) {
		
		Optional<BoardEntity> board = boardService.detail(id);
		BoardEntity entity = board.get();
		
		String uploadDir = fileUploadConfig.getUploadDir();
		
		if(!image.isEmpty()) {
			if(oldImgPath != null && !oldImgPath.isEmpty()) {
				
				String oldFile = oldImgPath.replace("/uploads/", "");
				
				Path oldFilePath = Paths.get(uploadDir, oldFile);
				
				try {
					Files.deleteIfExists(oldFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			try {
		               String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
		               Path newFilePath = Paths.get(uploadDir, newFileName);
		               image.transferTo(newFilePath.toFile());
		               entity.setImgPath("/uploads/" + newFileName);
		            } catch (IllegalStateException e) {
		               // TODO Auto-generated catch block
		               e.printStackTrace();
		            } catch (IOException e) {
		               // TODO Auto-generated catch block
		               e.printStackTrace();
		            }
				
		}
		
			entity.setTitle(title);
			entity.setContent(content);
			
			boardService.write(entity);
		return "redirect:/board/detail/"+ id;
	}
	
}
