package com.inside.ddf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inside.ddf.entity.TB_USER;
import com.inside.ddf.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

record SignupRequest(
    String userID, String userPassword
) {}

record ApiResponse(String message) {}


@RestController
@RequestMapping("/api/userauth")
@RequiredArgsConstructor
public class UserRestController {

	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody SignupRequest req, HttpSession session) {
		TB_USER user =  userService.findByIdAndPassword(req.userID(), req.userPassword());
		System.out.println(user.getNickNm());
		session.setAttribute("user", user); // 유저 정보 기억
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session) {
//		session.removeAttribute("user");
		session.invalidate(); // 세션 전체 무효화
		System.out.println("로그아웃되었습니다.");
	    return ResponseEntity.noContent().build(); // 204 No Content
	}
	
	/** 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody com.inside.ddf.dto.req.SignupRequest req) {
        userService.signup(req);
        return ResponseEntity.status(201).body(new ApiResponse("회원가입이 완료되었습니다."));
    }
}
