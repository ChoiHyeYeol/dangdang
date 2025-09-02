package com.inside.ddf.dto;

import lombok.Data;

@Data
public class MainMeal {
	public String mealCategory;
	public Long mealPlanId;
	public Long mealItemId;
	public String mealItemName;
	public boolean hasRecipe;
	public Long recipeId;
	
	
	
}
