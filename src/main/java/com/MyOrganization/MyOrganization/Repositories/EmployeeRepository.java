package com.MyOrganization.MyOrganization.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.MyOrganization.MyOrganization.Model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
   List<Employee> findByOrganizationId(String organizationId);
}