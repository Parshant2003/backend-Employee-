package com.MyOrganization.MyOrganization.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String gender;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String password;

    @NotNull
    private String organizationId; 
}

