package com.MyOrganization.MyOrganization.Services;

import com.MyOrganization.MyOrganization.DTO.LoginDTO;
import com.MyOrganization.MyOrganization.DTO.OrganizationDTO;
import com.MyOrganization.MyOrganization.Model.Organization;
import com.MyOrganization.MyOrganization.Model.OrganizationCredential; 
import com.MyOrganization.MyOrganization.Repositories.CredentialsRepository;
import com.MyOrganization.MyOrganization.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    
    @Autowired
    private CredentialsRepository credentialRepo; 
  
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(String id) {
        return organizationRepository.findById(id);
    }

    public Optional<OrganizationCredential> login(LoginDTO loginDto){
        Optional<OrganizationCredential>credential=credentialRepo.findByEmail(loginDto.getEmail());
        if(credential.isPresent() && passwordEncoder.matches(loginDto.getPassword(),credential.get().getPassword())){
            return credential;
        }
        return Optional.empty();
    }

    public Organization createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();

       
        organization.setName(organizationDTO.getName());
        organization.setAddress(organizationDTO.getAddress());
        organization.setContact(organizationDTO.getContact());

        Organization savedOrganization = organizationRepository.save(organization);

        savedOrganization.generateOrganizationSeries();

        OrganizationCredential credential = new OrganizationCredential();
        credential.setEmail(organizationDTO.getEmail());

        
        String hashedPassword = passwordEncoder.encode(organizationDTO.getPassword());
        credential.setPassword(hashedPassword);

        credential.setOrganizationId(savedOrganization.getId());
        OrganizationCredential savedCredential = credentialRepo.save(credential);

        savedOrganization.setCredential(savedCredential);

      
        savedOrganization = organizationRepository.save(savedOrganization);
        
        return savedOrganization; 
    }

   

    

    public void resetPassword(String email, String newPassword) {
        Optional<OrganizationCredential> optionalCredential = credentialRepo.findByEmail(email);
        if (optionalCredential.isPresent()) {
            OrganizationCredential credentials = optionalCredential.get();
            
            String hashedPassword = passwordEncoder.encode(newPassword);
    
            credentials.setPassword(hashedPassword);
            credentialRepo.save(credentials);
        } else {
            throw new IllegalArgumentException("Credentials not found for email: " + email);
        }
    }

    public void deleteOrganization(String id) {
        Optional<Organization> organization = organizationRepository.findById(id); 
        if (organization.isPresent()) {
            organizationRepository.deleteById(id);  
        } else {
            throw new IllegalArgumentException("Organization not found for ID: " + id);
        }
    }
}


