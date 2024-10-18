package com.MyOrganization.MyOrganization.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeCredentials {
    
    @Id
    private String id;

    private String email;

    private String password; 

    @Column(name = "organization_id")
    private String organizationId;

}
