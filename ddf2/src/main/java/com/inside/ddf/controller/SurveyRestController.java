package com.inside.ddf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inside.ddf.service.SurveyService;

@RestController
public class SurveyRestController {

    private final MainController mainController;

	@Autowired
	SurveyService surveyService;

    SurveyRestController(MainController mainController) {
        this.mainController = mainController;
    }
	
	@GetMapping("/test")
	public void test() {
		System.out.println("결과");
//		System.out.println(surveyService.add("혈당 측정 방법을 골라주세요","O"));
	}
}
