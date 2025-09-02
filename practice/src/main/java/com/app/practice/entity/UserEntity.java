package com.app.practice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

	@Id
	private String userID;
	
	private String userPassword;
	
	
}
