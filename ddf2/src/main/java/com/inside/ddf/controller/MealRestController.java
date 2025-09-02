package com.inside.ddf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inside.ddf.dto.req.MealReq;
import com.inside.ddf.entity.TB_USER;
import com.inside.ddf.service.MealService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MealRestController {


	@Autowired
	MealService mealService;
	
	// 관리자가 관리하는 부분 -> 일요일 12시에 자동으로 생성되어서 저장됨. 모든 사용자에 대해.
    @PostMapping("/api/model/recipe/weekly")
    public void recommand() {
    	mealService.addAllUserMeal();
    }
}
