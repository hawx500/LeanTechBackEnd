package com.leantech.system.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leantech.system.entity.Person;

public interface IPersonService extends JpaRepository<Person, Long>{

	public Person findByNameAndLastNameAndCellPhone(String name, String lastName, String cellPhone);
}
