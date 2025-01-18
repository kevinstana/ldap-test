package gr.hua.it21774.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gr.hua.it21774.dto.UserListDTO;
import gr.hua.it21774.enums.ERole;
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

    public Page<UserListDTO> getPagedUsers(int pageNumber, int pageSize, List<ERole> roles) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        
        return userRepository.customFindAll(pageable, roles);
    }
}
