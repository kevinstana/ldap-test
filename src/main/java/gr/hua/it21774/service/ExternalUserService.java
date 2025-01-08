package gr.hua.it21774.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.hua.it21774.entities.ExternalUser;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.requests.CreateExternalUserRequest;
import gr.hua.it21774.respository.ExternalUserRepository;
import gr.hua.it21774.respository.UserRepository;

@Service
public class ExternalUserService {

    ExternalUserRepository externalUserRepository;
    UserRepository userRepository;

    public ExternalUserService(UserRepository userRepository, ExternalUserRepository externalUserRepository) {
        this.userRepository = userRepository;
        this.externalUserRepository = externalUserRepository;
    }

    @Transactional
    public void createExternalUser(CreateExternalUserRequest request, String encodedPassword, Long roleId) {
        externalUserRepository
                .save(new ExternalUser(0L, request.getUsername(), encodedPassword));

        userRepository.save(new User(0L, request.getUsername(), request.getEmail(), request.getFirstnName(),
                request.getLastName(), Instant.now(), null, null, true, roleId));
    }

}
