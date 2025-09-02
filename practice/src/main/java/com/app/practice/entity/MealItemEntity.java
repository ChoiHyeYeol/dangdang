package com.app.practice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class MealItemEntity {

	@Id
	private int itemId;
	
	@Column(nullable=false)
	private int mealId;
	
	private int foodId;
	
	private int recipeId;
}
