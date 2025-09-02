package com.app.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.practice.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

	public String showDate();
}
