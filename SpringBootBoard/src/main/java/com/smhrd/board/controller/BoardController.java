package com.smhrd.board.controller;

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
import com.smhrd.board.config.FileUploadConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board") // controller에 requestmapping 진행 시 default url 변경
public class BoardController {
   
   @Autowired
   BoardService boardService;
   

   private final FileUploadConfig fileUploadConfig;

   BoardController(FileUploadConfig fileUploadConfig) {
      this.fileUploadConfig = fileUploadConfig;
   }

   // 글쓰기 기능
   @PostMapping("/write")
   public String write(@RequestParam String title, @RequestParam String content, HttpSession session,
         @RequestParam MultipartFile image) {
      // 필요한거 --> 제목, 작성자, 내용, 이미지(번호,작성일은 생략)
      // - 작성자 -> session에 담긴 값을 가지고 오는 방법
      // - 이미지 -> 이미지 파일을 가지고 와서 서버에 저장
      // --> 이미지 경로를 DB에 저장하기 위해
      // ㄴ 이미지를 서버에 저장(이미지 저장을 위한 환경설정 코드)

      String imgPath = "";
      
      if (!image.isEmpty()) {
         // 이미지의 이름
         String img_name = image.getOriginalFilename();

         // java 안에 고유 번호를 만드는 객체 --> UUID
         // 이미지의 고유 이름 부여
         String file_name = UUID.randomUUID() + "_" + img_name;
         // random값_이미지이름

         // C:/upload 폴더에 저장할 예정
         // --> 업로드 할 경로를 변수로 가지고 오기
         String uploadDir = fileUploadConfig.getUploadDir();

         // 예시) C:upload/123_1.jpg로 저장됨
         // 앞부분이 uploadDir, 뒷부분이 file_name
         String filePath = Paths.get(uploadDir, file_name).toString();         
         // uploadDir + file_name으로 작성 시 os에 따라 경로 못잡음

         // 파일 경로 확인 후 이미지 저장
         try {
            image.transferTo(new File(filePath));
            
            // 경로를 별도의 변수에 저장
            imgPath = "/uploads/" + file_name;
            
         } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }


      }

      // DB 저장
      // service 객체를 통해
      // BoardService -> BoardRepository
      // save()
      BoardEntity entity = new BoardEntity();
      entity.setTitle(title);
      entity.setContent(content);
      entity.setImgPath(imgPath);
      
      // writer -- session에서 가지고 오기 --> down casting
      UserEntity user = (UserEntity)session.getAttribute("user");
      
      String writer = user.getUserId();
      
      entity.setWriter(writer);
      
      BoardEntity result = boardService.write(entity);
      if(result != null) {
         // 글이 작성이 될 시 index 페이지로 이동
         return "redirect:/";
      }else {
         return "redirect:/board/write";
      }

   }
   
   // 게시글 상세페이지 이동
   @GetMapping("/detail/{id}") // {} url의 변수
   public String detail(@PathVariable Long id, Model model) { // url의 변수 가지고 오는 법
      
      System.out.println(id);
      // id를 바탕으로 select 진행
      // 게시글의 상세 정보 출력
      
      //db접근 --> service 객체 기능 구현
      Optional<BoardEntity> entity = boardService.detail(id);
      
      model.addAttribute("detail", entity.get());
      
      return "detail";
   }
   
   //게시글 수정 페이지 이동
   @GetMapping("/edit/{id}")
   public String edit(@PathVariable Long id, Model model) {
	   
	   // id를 바탕으로 데이터 select
	   Optional<BoardEntity> entity = boardService.detail(id);
	   
	   model.addAttribute("edit", entity.get());
	   
	   return "edit";
   }
   
   @PostMapping("/update")
   public String update(@RequestParam Long id, @RequestParam String title,
		   @RequestParam String content, @RequestParam String oldImgPath,
		   @RequestParam MultipartFile image) {
	   // 필요한거 정의 --> title, id, content, imgPath,
	   
	   // 데이터 불러오기
	   Optional<BoardEntity> board =  boardService.detail(id);
	   BoardEntity entity = board.get();
	   
	   String uploadDir = fileUploadConfig.getUploadDir();
	   
	   // 새로운 이미지 저장 시 기존 이미지 삭제
	   if(!image.isEmpty()) {
		   //기존이미지가 있는지 여부 판단
		   if(oldImgPath != null && !oldImgPath.isEmpty()) {
			   // 삭제 -- 서버에서 이미지 삭제
			   
			   // 파일명만 남기는 코드
			   String oldFile = oldImgPath.replace("/uploads/", "");
			   // 서버에 저장되어있는 경로 + 파일명
			   Path oldFilePath = Paths.get(uploadDir, oldFile);
			   
			   // 삭제코드 
			   try {
				Files.deleteIfExists(oldFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			   // 새로운 이미지 저장 
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
	   }
	   
	   	  entity.setTitle(title);
	      entity.setContent(content);
	      
	      //update 문 실행
	      // JPA에서 update문이 없는게 아니라 save() 함수가 update문 실행
	      // save() --> 2가지
	      // save가 update문을 실행하는 조건
	      //findById() 데이터를 불러 오는 것 (select) 이후 데이터는 영속상태(수정상태)
	      // save함수 사용 시 update 문을 실행
	      
	      // 복잡한 update문은 실행 --> @Query() 활용 하여 update 실행
	      
	      boardService.write(entity);
	      
	   
	   return "redirect:/board/detail/"+ id;
   }
   
}
