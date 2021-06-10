package com.leantech.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CANDIDATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "ADDRESS", nullable = false)
	private String address;
	
	@Column(name = "CELLPHONE", nullable = false)
	private String cellPhone;
	
	@Column(name = "CITY_NAME", nullable = false)
	private String cityName;

}
