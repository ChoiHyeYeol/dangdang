package com.cx.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cx.board.entity.UserEntity;
import com.cx.board.repository.UserRepository;

@Service
public class UserService {
 
	
	@Autowired
	private UserRepository userRepository;
	
	public void register(UserEntity entity) {
		userRepository.save(entity);
	}
	
	
		public boolean checkId(String user_id) {
			return userRepository.existsByUserId(user_id);
			
		}
		
		
		 
		public Optional<UserEntity> login(String id, String pw) {
		
			
			Optional<UserEntity> entity =  userRepository.findByUserIdAndPw(id, pw);
			
			return entity;
			
		}
	
}
