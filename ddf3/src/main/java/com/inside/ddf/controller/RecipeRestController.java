package com.inside.ddf.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inside.ddf.entity.TB_USER;
import com.inside.ddf.service.RecipeService;
import com.inside.ddf.service.UserService;

@RestController
public class RecipeRestController {

	@Autowired
	RecipeService recipeService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("update/recipe")
	public void updateRecipe() {
		Optional<TB_USER> admin = userService.findById("admin");
		recipeService.updateRecipe(admin.get());
	}
	
	@GetMapping("update/food")
	public void updateFood() {
		String excelPath = "C:\\Users\\dreac\\Desktop\\DevStudy\\fastapi\\ddf\\app\\data\\diet\\filtered_gdm_foods.xlsx";
		try {
			recipeService.importFromPath(excelPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
