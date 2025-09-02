package com.inside.ddf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inside.ddf.code.QuestType;
import com.inside.ddf.entity.TB_SR_QUEST;
import com.inside.ddf.repository.Rep_SR_QUEST;

@Service
public class SurveyService {

	@Autowired
	Rep_SR_QUEST surveyRepository;
	
	public List<TB_SR_QUEST> getAll() {
		return surveyRepository.findAll();
	}
	
	public TB_SR_QUEST add(String questCont, QuestType questType) { //객관식 : O , 주관식 : S
		TB_SR_QUEST quest = new TB_SR_QUEST();
		quest.setQuestCont(questCont);
		quest.setQuestType(questType);
		return surveyRepository.save(quest);
	}
}
