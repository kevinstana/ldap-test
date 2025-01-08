package gr.hua.it21774.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.EnabledUserDTO;
import gr.hua.it21774.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    @Query("SELECT new gr.hua.it21774.dto.EnabledUserDTO(u.id, u.isEnabled) FROM User u WHERE u.username = :username")
    Optional<EnabledUserDTO> findIdAndIsEnabledByUsername(String username);
}