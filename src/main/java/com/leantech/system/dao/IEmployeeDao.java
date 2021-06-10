package com.leantech.system.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leantech.system.entity.Employee;
import com.leantech.system.entity.Person;
import com.leantech.system.entity.Position;

@Repository
@Transactional
public interface IEmployeeDao extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{	
	
	public Employee findByPerson(Person person);	
	
	@Query("SELECT emp FROM Employee emp, Person per, Position pos WHERE emp.person.id = per.id AND emp.position.id = pos.id "
			+ "AND (per.name = :personName OR :personName IS NULL) "
			+ "AND (pos.name = :positionName OR :positionName IS NULL)")
	public List<Employee> findByPersonNameOrPositionName(String personName, String positionName);
	
	public List<Employee> findByPositionIn(List<Position> id);

}
