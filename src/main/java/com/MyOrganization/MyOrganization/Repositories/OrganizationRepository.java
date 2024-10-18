package com.MyOrganization.MyOrganization.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.MyOrganization.MyOrganization.Model.Organization;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    Organization findByName(String name);
}