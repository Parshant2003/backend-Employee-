package com.MyOrganization.MyOrganization.Services;


import com.MyOrganization.MyOrganization.DTO.EmployeeDTO;
import com.MyOrganization.MyOrganization.Model.Employee;
import com.MyOrganization.MyOrganization.Model.EmployeeCredentials;
import com.MyOrganization.MyOrganization.Model.Organization;
import com.MyOrganization.MyOrganization.Repositories.EmployeeCredentialRepository;
import com.MyOrganization.MyOrganization.Repositories.EmployeeRepository;
import com.MyOrganization.MyOrganization.Repositories.OrganizationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCredentialRepository employeeCredentialsRepository; 

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Employee addEmployee(EmployeeDTO employeeDTO) {
        
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setGender(employeeDTO.getGender());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setAddress(employeeDTO.getAddress());

        String hashedPassword = passwordEncoder.encode(employeeDTO.getPassword());

        EmployeeCredentials credentials = new EmployeeCredentials();
        credentials.setEmail(employeeDTO.getEmail());
        credentials.setPassword(hashedPassword);
        credentials.setOrganizationId(employeeDTO.getOrganizationId());

        EmployeeCredentials savedCredentials = employeeCredentialsRepository.save(credentials);
        employee.setCredentialId(savedCredentials.getId()); 

        employee.setOrganizationId(employeeDTO.getOrganizationId());
        Employee savedEmployee = employeeRepository.save(employee);

        Organization organization = organizationRepository.findById(employeeDTO.getOrganizationId()).orElse(null);
        if (organization != null) {
            organization.getEmployees().add(savedEmployee.getId());
            organizationRepository.save(organization); 
        }

        return savedEmployee;
    }

    public List<Employee> OrganizationEmployee(String organizationId) {
        return employeeRepository.findByOrganizationId(organizationId);
    }


   
public void deleteEmployee(String employeeId) {
    Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> 
        new RuntimeException("Employee not found with ID: " + employeeId));
    Organization organization = organizationRepository.findById(employee.getOrganizationId())
        .orElseThrow(() -> new RuntimeException("Organization not found with ID: " + employee.getOrganizationId()));
    organization.getEmployees().remove(employeeId);
    organizationRepository.save(organization); 
    employeeRepository.deleteById(employeeId);
}

}

