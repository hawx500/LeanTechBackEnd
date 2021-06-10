package com.leantech.system.service;

import java.util.List;
import java.util.Optional;

import com.leantech.system.entity.Employee;
import com.leantech.system.entity.Person;
import com.leantech.system.entity.Position;

public interface IEmployeeService {
	
	public Employee insertEmployee(Employee employee);
	
	public <S extends Employee> S updateEmployee(S employee);
	
	public Optional<Employee> findById(Long idEmployee);
	
	public void deleteEmployee(Long idEmployee);
	
	public Employee findByPerson(Person person);		
	
	public List<Employee> findByPersonNameOrPositionName(String personName, String positionName);
	
	public List<Employee> findByPositionIn(List<Position> id);

}
