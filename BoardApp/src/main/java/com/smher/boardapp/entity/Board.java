package com.smher.boardapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int num;
	private String title;
	private String writer;
	private String content;
	private String filePath;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", 
			insertable = false,
			updatable = false)
	private LocalDateTime b_date;
}
