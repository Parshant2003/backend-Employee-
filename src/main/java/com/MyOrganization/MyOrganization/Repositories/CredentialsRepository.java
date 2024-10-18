package com.MyOrganization.MyOrganization.Repositories;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.MyOrganization.MyOrganization.Model.OrganizationCredential;

@Repository
public interface CredentialsRepository extends MongoRepository<OrganizationCredential, String> {
    Optional<OrganizationCredential> findByEmail(String email);
}
