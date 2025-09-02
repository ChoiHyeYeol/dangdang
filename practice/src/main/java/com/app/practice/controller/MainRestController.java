package com.app.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.app.practice.entity.MealItemEntity;
import com.app.practice.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class MainRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	
	
//	public String start(HttpSession session, String time) {
//		String userDate = "";
//		UserEntity user = session.getAttribute("user");
//		userDate = user.getUserTime();
//		return userDate;
//		
//	}
//	
//	public String start2() {
//		String time = userService.showDate();
//	}
	
	public List<MealItemEntity> newDiet() {
		return result;
	}
	
	public List<String> idToName() {
		List<MealItemEntity> diet = newDiet();
		List<String> result;
		for (int i=0;i<diet.size();i++) {
			String name;
			if(diet[i].getFoodId()==null) {
				name = recipeService.getRecipeName();
			}
			else {
				name = foodService.getFoodName();
			}
			result.add(name);
		}
		return result;
	}
	
}
