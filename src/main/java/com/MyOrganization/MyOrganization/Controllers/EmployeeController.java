package com.MyOrganization.MyOrganization.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyOrganization.MyOrganization.DTO.EmployeeDTO;
import com.MyOrganization.MyOrganization.Model.Employee;
import com.MyOrganization.MyOrganization.Services.EmployeeService;


@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:5173") 
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee savedEmployee = employeeService.addEmployee(employeeDTO);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("get/{id}")
    public List<Employee> getEmployeesByOrganization(@PathVariable String id) {
        return employeeService.OrganizationEmployee(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
