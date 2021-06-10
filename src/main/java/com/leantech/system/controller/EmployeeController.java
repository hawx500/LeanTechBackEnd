package com.leantech.system.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leantech.system.commons.ResponseService;
import com.leantech.system.entity.Employee;
import com.leantech.system.entity.Person;
import com.leantech.system.entity.Position;
import com.leantech.system.service.IEmployeeService;
import com.leantech.system.service.IPersonService;
import com.leantech.system.service.IPositionService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("employee/operations")
@RestController
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IPositionService positionService;

	@Autowired
	private MessageSource messageSource;

	@PostMapping("/insertEmployee")
	public ResponseEntity<?> insertEmployee(@RequestBody Employee employee) {
		Employee employeeSave = null;
		Person personSearch = null;

		try {
			personSearch = personService.findByNameAndLastNameAndCellPhone(employee.getPerson().getName(),
					employee.getPerson().getLastName(), employee.getPerson().getCellPhone());

			if (personSearch != null) {
				employee.setPerson(personSearch);
			}

			if (personSearch != null) {
				employeeSave = employeeService.findByPerson(employee.getPerson());
			}

			if (employeeSave == null) {
				employeeSave = employeeService.insertEmployee(employee);
			} else {
				return new ResponseEntity<ResponseService>(new ResponseService(1,
						messageSource.getMessage("text.error.insert.duplicate", null, LocaleContextHolder.getLocale())),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<ResponseService>(
					new ResponseService(1,
							messageSource.getMessage("text.error.insert.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseService>(
				new ResponseService(0,
						messageSource.getMessage("text.insert.success",
								new String[] { employeeSave.getId().toString() }, LocaleContextHolder.getLocale()),
						employeeSave),
				HttpStatus.CREATED);

	}

	@PutMapping("updateEmployee/{id}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,
			@PathVariable(value = "id") long idEmployee) {
		Optional<Employee> employeeUpdate = null;
		try {
			employeeUpdate = employeeService.findById(idEmployee).map(employeeTemp -> {
				employeeTemp.setPerson(employee.getPerson());
				employeeTemp.setPosition(employee.getPosition());
				employeeTemp.setSalary(employee.getSalary());
				return employeeService.updateEmployee(employeeTemp);
			});
		} catch (DataAccessException e) {
			return new ResponseEntity<ResponseService>(
					new ResponseService(1,
							messageSource.getMessage("text.error.update.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseService>(new ResponseService(0,
				messageSource.getMessage("text.update.success",
						new String[] { employeeUpdate.get().getId().toString() }, LocaleContextHolder.getLocale()),
				employeeUpdate), HttpStatus.CREATED);

	}

	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable(value = "id") long idEmployee) {
		try {
			employeeService.deleteEmployee(idEmployee);
		} catch (DataAccessException e) {
			return new ResponseEntity<ResponseService>(
					new ResponseService(1,
							messageSource.getMessage("text.error.delete.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseService>(
				new ResponseService(0, messageSource.getMessage("text.delete.success",
						new String[] { String.valueOf(idEmployee) }, LocaleContextHolder.getLocale()), null),
				HttpStatus.OK);
	}

	@GetMapping("/findByPersonNameOrPositionName")
	public ResponseEntity<?> findByPersonNameOrPositionName(@RequestParam(value = "personName", required = false) String personName,
			@RequestParam(value = "positionName", required = false) String positionName) {
		List<Employee> listEmployee = new ArrayList<Employee>(1);
		try {
			listEmployee = employeeService.findByPersonNameOrPositionName(personName, positionName);
		} catch (DataAccessException e) {
			return new ResponseEntity<ResponseService>(
					new ResponseService(1,
							messageSource.getMessage("text.error.get.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseService>(new ResponseService(0,
				messageSource.getMessage("text.get.success", null, LocaleContextHolder.getLocale()), listEmployee),
				HttpStatus.OK);
	}
	
	@GetMapping("/findInfoByPosition")
	public ResponseEntity<?> findInfoByPosition(){
		List<Position> listPosition = new ArrayList<>(1);
		List<Employee> listEmployee = new ArrayList<>(1);
		Map<Position, List<Employee>> employeesMap;
		try {
			listPosition = positionService.findAll();	
			
			listEmployee = employeeService.findByPositionIn(listPosition);
			
			employeesMap = listEmployee.stream().sorted(Comparator.comparing(Employee::getSalary))
                    .collect(Collectors.groupingBy(Employee::getPosition));
			
		} catch (Exception e) {
			return new ResponseEntity<ResponseService>(
					new ResponseService(1,
							messageSource.getMessage("text.error.get.db", null, LocaleContextHolder.getLocale())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseService>(new ResponseService(0,
				messageSource.getMessage("text.get.success", null, LocaleContextHolder.getLocale()), employeesMap),
				HttpStatus.OK);
	}
}
