package com.javaLive.springmvc.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
 
import com.javaLive.springmvc.model.Employee;
import com.javaLive.springmvc.service.EmployeeService;
 
@RestController
public class AppRESTController {
	@Autowired
	EmployeeService employeeService;  //Service which will do all data retrieval/manipulation work
	
	//-------------------Retrieve All Users--------------------------------------------------------
    
    @RequestMapping(value = "/employeeList/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> users = employeeService.findAllEmployees();
        if(users.isEmpty()){
        	System.out.println("No data found");
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Employee>>(users, HttpStatus.OK);
    }
    
    //-------------------Retrieve Single User--------------------------------------------------------
    
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getemployee(@PathVariable("id") int id) {
        System.out.println("Fetching Employee with id " + id);
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
    
  //-------------------Create a User--------------------------------------------------------
    
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Employee employee,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + employee.getName());
 
        if (employeeService.findById((employee.getId()))!=null){
            System.out.println("A User with name " + employee.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        employeeService.saveEmployee(employee);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
