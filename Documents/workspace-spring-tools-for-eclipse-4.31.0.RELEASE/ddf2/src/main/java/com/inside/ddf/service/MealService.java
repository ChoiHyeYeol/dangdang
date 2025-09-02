package com.inside.ddf.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.inside.ddf.code.CategoryTime;
import com.inside.ddf.dto.req.MealReq;
import com.inside.ddf.dto.res.MealRes;
import com.inside.ddf.entity.TB_FOOD;
import com.inside.ddf.entity.TB_MEAL_ITEM;
import com.inside.ddf.entity.TB_MEAL_PLAN;
import com.inside.ddf.entity.TB_RECIPE;
import com.inside.ddf.entity.TB_USER;
import com.inside.ddf.repository.Rep_FOOD;
import com.inside.ddf.repository.Rep_MEAL_ITEM;
import com.inside.ddf.repository.Rep_MEAL_PLAN;
import com.inside.ddf.repository.Rep_RECIPE;
import com.inside.ddf.repository.Rep_USER;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealService {

	private final WebClient fastApiClient;

	@Autowired
	Rep_RECIPE rep_rcp;
	
	@Autowired
	Rep_FOOD rep_food;
	
	@Autowired
	Rep_MEAL_PLAN rep_meal;
	
	@Autowired
	Rep_MEAL_ITEM rep_item;
	
	@Autowired
	Rep_USER rep_user;
	
	public void addAllUserMeal() {
		List<TB_USER> userList = rep_user.findAll();
		for(int i=0;i<userList.size();i++) {
			TB_USER user = userList.get(i);
			MealReq req = new MealReq();
	    	List<String> allergies = List.of("");
	    	List<String> preferences = List.of("");
	    	req.setAllergies(allergies);
	    	req.setPreferences(preferences);
	    	if (user.getUserType() == null) continue; // 설문조사 완료하지 않은 상태에서 12시가 되면
	    	req.setUser_type(user.getUserType().toString());
	    	addMeal(req,user);
		}
		
	}
	
	public void addMeal(MealReq req ,TB_USER user){
    	MealRes res =  fastApiClient.post()
                .uri("/api/model/diet/weekly")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(MealRes.class)
                .block(); // 간단히 block(), 서비스 내부에서 reactive 유지도 가능
    	System.out.println(res.toString());
    	LocalDate date = LocalDate.now();

    	for (int day=0;day<res.getPlan().size();day++) {
    		for(int time=0;time<res.getPlan().get(day).size();time++) {
    	    	TB_MEAL_PLAN mp = new TB_MEAL_PLAN();
    	    	mp.setUser(user);
    	    	mp.setMealDate(date.plusDays(day)); 
    	    	CategoryTime[] mealType = {CategoryTime.B,CategoryTime.L,CategoryTime.D,CategoryTime.S};
    	    	mp.setMealType(mealType[time]);
    	    	
    	    	mp = rep_meal.save(mp);
    			for(int item=0;item<res.getPlan().get(day).get(time).size();item++) {
    				String content = res.getPlan().get(day).get(time).get(item);
//    				System.out.println();
    				if(content.startsWith("food")) {
    					try {
    						TB_RECIPE recipe = rep_rcp.findById(content).get();
    						
    						TB_MEAL_ITEM entity = new TB_MEAL_ITEM();
    						entity.setMeal(mp);
    						entity.setRecipe(recipe);
    						
    						rep_item.save(entity);
    					}catch(Exception e) {
        					System.out.print(content);
    					}
    				}
    				else {
    					TB_FOOD food = rep_food.findById(content).get();
    					TB_MEAL_ITEM entity = new TB_MEAL_ITEM();
						entity.setMeal(mp);
						entity.setFood(food);
						rep_item.save(entity);
    				}
    					
    			}
    		}
    	}
//    	return res;
    }
	
}
