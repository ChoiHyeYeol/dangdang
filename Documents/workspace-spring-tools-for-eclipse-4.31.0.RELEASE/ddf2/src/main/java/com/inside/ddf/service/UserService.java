package com.inside.ddf.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inside.ddf.entity.TB_USER;
import com.inside.ddf.repository.Rep_USER;

@Service
public class UserService {

	@Autowired
	Rep_USER rep_user;
	
	public TB_USER findByIdAndPassword(String userID, String userPassword) {
		Optional<TB_USER> entity = rep_user.findById(userID);
		if(!entity.isEmpty()) {
			TB_USER user = rep_user.findById(userID).get();
			if (user.getUserPw().equals(userPassword)) return user;
		}
		return null;
		
		
	}
	
	public Optional<TB_USER> findById(String userID) {
		return rep_user.findById(userID);
	}
}
