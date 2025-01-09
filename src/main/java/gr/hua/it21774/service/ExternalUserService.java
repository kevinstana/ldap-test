package gr.hua.it21774.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.hua.it21774.entities.ExternalUser;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.exceptions.EntityExistsException;
import gr.hua.it21774.exceptions.EntityNotFoundException;
import gr.hua.it21774.exceptions.InvalidAttributeException;
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
            throw new EntityExistsException("Username already in use");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EntityExistsException("Email already in use");
        }
    }

    @Transactional
    public void createExternalUser(CreateExternalUserRequest request, String password, ERole role) {
        Long roleId = roleRepository.findIdByRole(role).get();

        externalUserRepository
                .save(new ExternalUser(0L, request.getUsername(), passwordEncoder.encode(password)));

        userRepository.save(new User(0L, request.getUsername(), request.getEmail(), request.getFirstnName(),
                request.getLastName(), Instant.now(), null, null, true, roleId));
    }

    public void handleMyPasswordChange(String username, String requestCurrentPassword, String requestNewPassword) {
        String currentPassword = externalUserRepository
                .findPasswordByUsername(username).orElse("");

        if (!passwordEncoder.matches(requestCurrentPassword, currentPassword)) {
            throw new InvalidAttributeException("Invalid current password");
        }

        externalUserRepository.updatePasswordByUsername(username, passwordEncoder.encode(requestNewPassword));
    }

    public void changePasswordAsAdmin(String username, String requestNewPassword) {
        if (!externalUserRepository.existsByUsername(username)) {
            throw new EntityNotFoundException("External user not found");
        }

        externalUserRepository.updatePasswordByUsername(username, passwordEncoder.encode(requestNewPassword));
    }
}
