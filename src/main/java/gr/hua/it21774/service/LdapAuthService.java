package gr.hua.it21774.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import gr.hua.it21774.dto.EnabledUserDTO;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.respository.RoleRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.userdetails.AppUserDetails;

@Service
public class LdapAuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public LdapAuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public EnabledUserDTO loadEnabledUser(String username) {
        EnabledUserDTO user = userRepository
                .findIdAndIsEnabledByUsername(username)
                .orElse(new EnabledUserDTO(0L, false));

        return user;
    }

    public Long ldapUserToLocal(AppUserDetails userDetails) {
        Long roleId = roleRepository.findIdByRole(userDetails.getRole()).get();

        Instant now = Instant.now();
        User newUser = new User(userDetails,
                now, true, roleId);
        userRepository.save(newUser);

        return newUser.getId();
    }

}
