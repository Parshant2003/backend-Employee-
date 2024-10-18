package com.MyOrganization.MyOrganization.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MyOrganization.MyOrganization.Model.EmployeeCredentials;

public interface EmployeeCredentialRepository extends MongoRepository<EmployeeCredentials,String> {
    
}
