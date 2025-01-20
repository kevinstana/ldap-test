package gr.hua.it21774.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.entities.ExternalUser;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.requests.CreateExternalUserRequest;
import gr.hua.it21774.respository.ExternalUserRepository;
import gr.hua.it21774.respository.RoleRepository;
import gr.hua.it21774.respository.UserRepository;

@Service
public class ExternalUserService {

    private final ExternalUserRepository externalUserRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ExternalUserService(UserRepository userRepository, ExternalUserRepository externalUserRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.externalUserRepository = externalUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void checkIfExists(String username, String email) {
        if (externalUserRepository.existsByUsername(username)) {
            throw new GenericException(HttpStatus.CONFLICT, "Username already in use");
        }

        if (userRepository.existsByEmail(email)) {
            throw new GenericException(HttpStatus.CONFLICT, "Email already in use");
        }
    }

    @Transactional
    public void createExternalUser(CreateExternalUserRequest request) {
        ERole role;
        try {
            role = ERole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GenericException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid role");
        }

        Long roleId = roleRepository.findIdByRole(role).get();

        externalUserRepository
                .save(new ExternalUser(0L, request.getUsername(), passwordEncoder.encode(request.getPassword())));

        userRepository.save(new User(0L, request.getUsername(), request.getEmail(), request.getFirstName(),
                request.getLastName(), Instant.now(), null, null, true, roleId));
    }

    public void handleMyPasswordChange(String username, String requestCurrentPassword, String requestNewPassword) {
        String currentPassword = externalUserRepository
                .findPasswordByUsername(username).orElse("");

        if (!passwordEncoder.matches(requestCurrentPassword, currentPassword)) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Invalid current password");
        }

        externalUserRepository.updatePasswordByUsername(username, passwordEncoder.encode(requestNewPassword));
    }

    public void changePasswordAsAdmin(String username, String requestNewPassword) {
        if (!externalUserRepository.existsByUsername(username)) {
            throw new GenericException(HttpStatus.NOT_FOUND, "External user not found");
        }

        externalUserRepository.updatePasswordByUsername(username, passwordEncoder.encode(requestNewPassword));
    }

    public Page<CommonUserDTO> getPagedUsers(Integer pageNumber, Integer pageSize, List<ERole> roles, Boolean enabled) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        
        return externalUserRepository.customFindAll(pageable, roles, enabled);
    }
}
