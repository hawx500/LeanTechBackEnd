package com.leantech.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.leantech.system.dao.IEmployeeDao;
import com.leantech.system.entity.Employee;
import com.leantech.system.entity.Person;
import com.leantech.system.entity.Position;
import com.leantech.system.service.IEmployeeService;

@Service
public class EmployeeImpl implements IEmployeeService{
	
	@Autowired
	private IEmployeeDao employeeDao;
	
	public Employee insertEmployee(Employee employee) {
		Employee employeSave = null;
		try {	
			employeSave = employeeDao.save(employee);
		}catch (DataAccessException e) {
			e.getMessage();
			return null;
		}
		return employeSave;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Employee> S updateEmployee(S employee) {
		Employee employeeUpdate = null;
		try {
			employeeUpdate = employeeDao.saveAndFlush(employee);			
		} catch (DataAccessException e) {
			e.getMessage();
			return null;
		}
		return (S)employeeUpdate;
	}

	@Override
	public Optional<Employee> findById(Long idEmployee) {
		Optional<Employee> employeeFind = null;
		try {
			employeeFind = employeeDao.findById(idEmployee);
		}catch (DataAccessException e) {
			e.getMessage();
			return null;			
		}
		return employeeFind;
	}
	
	public void deleteEmployee(Long idEmployee) {
		try {
			employeeDao.deleteById(idEmployee);
		} catch (Exception e) {
			e.getMessage();
		}
	}	
	
	public Employee findByPerson(Person person) {
		Employee employeSearch = null;
		try {
			employeSearch = employeeDao.findByPerson(person);
		}catch (DataAccessException e) {
			e.getMessage();
			return null;
		}
		return employeSearch;
	}
	
	public List<Employee> findByPersonNameOrPositionName(String personName, String positionName) {
		List<Employee> listEmploye = new ArrayList<>(1);
		try {
			listEmploye = employeeDao.findByPersonNameOrPositionName(personName, positionName);
		}catch (DataAccessException e) {
			e.getMessage();
			return null;
		}
		return listEmploye;
	}

	@Override
	public List<Employee> findByPositionIn(List<Position> id) {		
		return employeeDao.findByPositionIn(id);
	}
	

}
