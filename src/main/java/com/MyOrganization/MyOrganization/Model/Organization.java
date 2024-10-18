package com.MyOrganization.MyOrganization.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organization {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String contact;

    @NotNull
    private String organizationSeries;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "organization_employees", joinColumns = @JoinColumn(name = "organization_id"))
    @Column(name = "employee_id")
    
    private List<String> employees = new ArrayList<>(); 

    // One-to-One relationship with OrganizationCredential
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", referencedColumnName = "id")
    private OrganizationCredential credential;

    public Organization() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void generateOrganizationSeries() {
        String prefix = name.substring(0, Math.min(name.length(), 4)).toUpperCase();
        this.organizationSeries = prefix + "_0001"; 
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOrganizationSeries() {
        return organizationSeries;
    }

    public void setOrganizationSeries(String organizationSeries) {
        this.organizationSeries = organizationSeries;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }

    public OrganizationCredential getCredential() {
        return credential;
    }

    public void setCredential(OrganizationCredential credential) {
        this.credential = credential;
    }
}
