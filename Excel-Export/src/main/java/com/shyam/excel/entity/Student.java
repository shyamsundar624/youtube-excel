package com.shyam.excel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String branch;
	private String college;
	private double fees;
	
	public Student(String name, String branch, String college, double fees) {
		super();
		this.name = name;
		this.branch = branch;
		this.college = college;
		this.fees = fees;
	}
	
	
}
