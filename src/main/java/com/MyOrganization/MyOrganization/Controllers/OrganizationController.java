package com.MyOrganization.MyOrganization.Controllers;

import com.MyOrganization.MyOrganization.DTO.LoginDTO;
import com.MyOrganization.MyOrganization.DTO.OrganizationDTO;
import com.MyOrganization.MyOrganization.Model.Organization;
import com.MyOrganization.MyOrganization.Model.OrganizationCredential;
import com.MyOrganization.MyOrganization.Services.OrganizationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins = "http://localhost:5173")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        Organization newOrganization = organizationService.createOrganization(organizationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrganization);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String id) {
        Optional<Organization> organization = organizationService.getOrganizationById(id);
        return organization.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDto) {
        Optional<OrganizationCredential> credential = organizationService.login(loginDto);
        if (credential.isPresent()) {
            // Create a response map excluding the password
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");

            OrganizationCredential userCredential = credential.get();

            Map<String, Object> credentialWithoutPassword = new HashMap<>();
            credentialWithoutPassword.put("id", userCredential.getId());
            credentialWithoutPassword.put("email", userCredential.getEmail());
            credentialWithoutPassword.put("organizationId", userCredential.getOrganizationId());

            response.put("credential", credentialWithoutPassword);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @PutMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");

        try {
            organizationService.resetPassword(email, newPassword);
            return ResponseEntity.ok("Password has been successfully reset.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while resetting the password.");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable String id) {
        try {
            organizationService.deleteOrganization(id);
            return ResponseEntity.ok("Organization with ID " + id + " has been deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization with ID " + id + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the organization.");
        }
    }

}
