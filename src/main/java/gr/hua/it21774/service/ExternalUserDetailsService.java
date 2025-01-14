package gr.hua.it21774.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gr.hua.it21774.dto.ExternalUserDTO;
import gr.hua.it21774.respository.ExternalUserRepository;
import gr.hua.it21774.userdetails.AppUserDetails;

@Service
public class ExternalUserDetailsService implements UserDetailsService {

    private final ExternalUserRepository externalUserRepository;

    public ExternalUserDetailsService(ExternalUserRepository externalUserRepository) {
        this.externalUserRepository = externalUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ExternalUserDTO externalUser = externalUserRepository.findByUsername(username).get();

        AppUserDetails userDetails = new AppUserDetails(externalUser.getId(), externalUser.getUsername(),
                externalUser.getPassword(),
                externalUser.getEmail(),
                externalUser.getFirstName(),
                externalUser.getLastName(),
                externalUser.getRole(),
                externalUser.getIsEnabled());

        return userDetails;
    }
}
