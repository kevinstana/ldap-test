package gr.hua.it21774.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.ExternalUserDTO;
import gr.hua.it21774.entities.ExternalUser;
import jakarta.transaction.Transactional;

@Repository
public interface ExternalUserRepository extends JpaRepository<ExternalUser, Long> {

    Boolean existsByUsername(String username);

    @Query("SELECT new gr.hua.it21774.dto.ExternalUserDTO(u.id, e.username, e.password, u.email, r.role, u.isEnabled) "
            +
            "FROM ExternalUser e " +
            "JOIN User u ON e.username = u.username " +
            "JOIN Role r ON u.roleId = r.id " +
            "WHERE e.username = :username")
    Optional<ExternalUserDTO> findByUsername(String username);

    @Query("SELECT e.password " +
            "FROM ExternalUser e WHERE e.username = :username ")
    Optional<String> findPasswordByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE ExternalUser e SET e.password = :password WHERE e.username = :username")
    int updatePasswordByUsername(String username, String password);
}