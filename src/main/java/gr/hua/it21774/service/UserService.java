package gr.hua.it21774.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gr.hua.it21774.dto.UserListDTO;
import gr.hua.it21774.respository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserListDTO> getAllUsers() {
        return userRepository.customFindAll();
    }
}
