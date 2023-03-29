package com.yash.ems.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.yash.ems.model.Employee;
import com.yash.ems.model.Evaluation;
import com.yash.ems.payloads.ApiResponse;
import com.yash.ems.payloads.EmployeeDto;
import com.yash.ems.repo.EvaluationRepo;
import com.yash.ems.service.EvaluationService;
import com.yash.ems.serviceimpl.EmployeeServiceEmpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/ems/employee")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeServiceEmpl empService;

	@Autowired
	private EvaluationRepo er;

	@Autowired
	private EvaluationService evaluationService;

	DateTimeFormatter dtm = DateTimeFormatter.ofPattern("yyyy-dd-MM");

	@PostMapping("/onboard-new-employee")
	public ResponseEntity<Employee> saveEmpoyee(@Valid @RequestBody Employee employeeDto) {

		LOGGER.info("Create Employee Data...");

		Employee saveEmployee = this.empService.addEmployee(employeeDto);
		return new ResponseEntity<Employee>(saveEmployee, HttpStatus.CREATED);
	}

	@GetMapping("/get-all-onboard-employees-details")
	public ResponseEntity<List<Employee>> getAll() {

		LOGGER.info("Show onboard employees details");
		List<Employee> employee = this.empService.getAllEmp();
		return ResponseEntity.ok(employee);
	}

	@GetMapping("/getDate")
	public void date(Employee emp) {
		empService.setDate(emp);
	}

	@DeleteMapping("/delete-employee/{employeeId}")
	public ResponseEntity<Employee> deleteCategory(@PathVariable Integer employeeId) {
		empService.deleteEmployee(employeeId);

		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get-employee/{employeeId}")
	public ResponseEntity<Employee> getEmployeeDetailsById(@PathVariable(value = "employeeId") Integer employeeId) {
		Employee employee = empService.getEmployeeById(employeeId);
		System.out.println("employee" + employeeId);
		System.out.println(employee);
		return ResponseEntity.ok().body(employee);

	}

	@GetMapping("/employes-by/{employeeId}/evaluation")
	public ResponseEntity<List<Evaluation>> getAllEvaluationByEmployeeId(
			@PathVariable(value = "employeeId") Integer employeeId) {
		Employee employee = empService.getEmployeeById(employeeId);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();

		evaluations.addAll(employee.getEvaluation());

		return new ResponseEntity<>(evaluations, HttpStatus.OK);
	}

	@GetMapping("/employee-byName/{employeeName}/evaluation")
	public ResponseEntity<List<Evaluation>> getAllEvaluationByEmployeeName(
			@PathVariable(value = "employeeName") String employeeName) {
		Employee employee = empService.getEmployeeByName(employeeName);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();

		evaluations.addAll(employee.getEvaluation());

		return new ResponseEntity<>(evaluations, HttpStatus.OK);
	}

	@GetMapping("/evaluation")
	public ResponseEntity<List<Evaluation>> getAllEvaluation() {
		// Employee employee = empService.getEmployeeById(employeeId);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();

		// evaluations.addAll(employee.getEvaluation());
		List<Evaluation> findAll = er.findAll();

		return new ResponseEntity<>(findAll, HttpStatus.OK);
	}

	@GetMapping("/find-by-id-name/{employeeId}/{employeeName}/evaluation")
	public ResponseEntity<Employee> findByEmployeeIdAndEmployeeName(
			@PathVariable(value = "employeeId") Integer employeeId,
			@PathVariable(value = "employeeName") String employeeName) {
		Employee employee = empService.findByEmployeeIdAndEmployeeName(employeeId, employeeName);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();
		if (employee == empService.getAllEmp())
			evaluations.addAll(employee.getEvaluation());

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping("/serarch-by-id-name/{employeeId}/{employeeName}/evaluation")
	public ResponseEntity<EmployeeDto> searchByEmployeeIdAndEmployeeName(
			@PathVariable(value = "employeeId") Integer employeeId,
			@PathVariable(value = "employeeName") String employeeName) {
		EmployeeDto employee = empService.searchByEmployeeIdAndEmployeeName(employeeId, employeeName);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();

		evaluations.addAll(employee.getEvaluation());

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping("/serarch-id-name/{employeeId}/{employeeName}/evaluation")
	public ResponseEntity<EmployeeDto> getByEmployeeIdAndEmployeeName(
			@PathVariable(value = "employeeId") Integer employeeId,
			@PathVariable(value = "employeeName") String employeeName) {
		EmployeeDto employee = empService.searchByEmployeeIdAndEmployeeName(employeeId, employeeName);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();

		evaluations.addAll(employee.getEvaluation());

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping("/idorname/{employeeName} /{employeeId}/evaluation")
	public ResponseEntity<Employee> searchByEmployeeNameOrEmployeeId(
			@PathVariable(value = "employeeName") String employeeName,
			@PathVariable(value = "employeeId") Integer employeeId) {
		Employee employee = empService.searchByEmployeeNameOrEmployeeId(employeeName, employeeId);

		List<Evaluation> evaluations = new ArrayList<Evaluation>();

		evaluations.addAll(employee.getEvaluation());

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@PutMapping("/addEvaluation/{employeeId}")
	public Evaluation addEval(@PathVariable Integer employeeId, @RequestBody Evaluation eval) {
		System.out.println(eval);
		return empService.addEvatuationDetailsById(employeeId, eval);
	}

	@PostMapping("/addEvaluation-employeeName/{employeeName}")
	public Evaluation addEvalByName(@PathVariable String employeeName, @RequestBody Evaluation eval) {
		System.out.println(eval);
		return empService.addEvatuationDetailsByName(employeeName, eval);
	}

	@PutMapping("/updateEmpById/{employeeId}")
	public Employee updateById(@PathVariable Integer employeeId, @RequestBody Employee employee) {
		return empService.updateEmployee(employeeId, employee);
	}

	@GetMapping("/{employeeName}/employee")
	public Employee getByEmployeeName(@PathVariable(value="employeeName") String employeeName ) {
			
			Employee employeeByName = empService.getEmployeeByName(employeeName);
			
			System.out.println(employeeByName);
			return employeeByName;
		
	}


	@GetMapping("evaluation/{employeeId}/{evaluationId}")
	public ResponseEntity<Evaluation> getEvaluationById(
			@PathVariable(value = "employeeId") Integer employeeId,
			@PathVariable (value = "evaluationId") Integer evaluationId){
		
		Employee employeeById = empService.getEmployeeById(employeeId);
		Evaluation id = evaluationService.findByEvaluationId(evaluationId);
		
		
		
				return new ResponseEntity<Evaluation>(id,HttpStatus.OK);
		
	}

}

/*
 * { "employeeId":1001, "employeeName":"ekta marathe", "grade":"E1",
 * "dob":"1970-12-11", "exprience":"2 years", "exprience_range":"ee",
 * "base_location":"pune", "current_location":"pune" }
 */
