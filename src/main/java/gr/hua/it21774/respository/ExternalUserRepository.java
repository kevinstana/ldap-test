package gr.hua.it21774.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.ExternalUserDTO;
import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.entities.ExternalUser;
import gr.hua.it21774.enums.ERole;
import jakarta.transaction.Transactional;

@Repository
public interface ExternalUserRepository extends JpaRepository<ExternalUser, Long> {

        Boolean existsByUsername(String username);

        @Query("SELECT new gr.hua.it21774.dto.CommonUserDTO(" +
                        "u.id, e.username, u.email, u.firstName, u.lastName, " +
                        "u.createdAt, r.role, u.isEnabled, u.lastModified, " +
                        "lm.username AS lastModifiedBy) " +
                        "FROM ExternalUser e " +
                        "JOIN User u ON e.username = u.username " +
                        "JOIN Role r ON u.roleId = r.id " +
                        "LEFT JOIN User lm ON u.lastModifiedBy = lm.id " +
                        "WHERE (:roles IS NULL OR r.role IN :roles) " +
                        "AND (:enabled IS NULL OR u.isEnabled = :enabled) " +
                        "AND (:query IS NULL OR " +
                        "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                        "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                        "ORDER BY u.id ASC")
        Page<CommonUserDTO> customFindAll(Pageable pageable, List<ERole> roles, Boolean enabled, String query);

        @Query("SELECT new gr.hua.it21774.dto.ExternalUserDTO(u.id, e.username, e.password, u.email, u.firstName, u.lastName, r.role, u.isEnabled) "
                        +
                        "FROM ExternalUser e " +
                        "JOIN User u ON e.username = u.username " +
                        "JOIN Role r ON u.roleId = r.id " +
                        "WHERE e.username = :username")
        Optional<ExternalUserDTO> findByUsername(String username);

        @Query("""
                            SELECT new gr.hua.it21774.dto.CommonUserDTO(
                                u.id,
                                e.username,
                                u.email,
                                u.firstName,
                                u.lastName,
                                u.createdAt,
                                r.role,
                                u.isEnabled,
                                u.lastModified,
                                lm.username AS lastModifiedBy
                            )
                            FROM User u
                            JOIN ExternalUser e ON u.username = e.username
                            JOIN Role r ON u.roleId = r.id
                            LEFT JOIN User lm ON u.lastModifiedBy = lm.id
                            WHERE e.username = :username
                        """)
        Optional<CommonUserDTO> getExternalUserProfile(String username);

        @Query(value = "SELECT e.username " +
                        "FROM external_users e " +
                        "WHERE e.id < :id " +
                        "ORDER BY e.id DESC " +
                        "LIMIT 1", nativeQuery = true)
        Optional<String> findPreviousUsernameById(Long id);

        @Query(value = "SELECT e.username " +
                        "FROM external_users e " +
                        "WHERE e.id > :id " +
                        "ORDER BY e.id ASC " +
                        "LIMIT 1", nativeQuery = true)
        Optional<String> findNextUsernameById(Long id);

        @Query("SELECT e.password " +
                        "FROM ExternalUser e WHERE e.username = :username ")
        Optional<String> findPasswordByUsername(String username);

        @Transactional
        @Modifying
        @Query("UPDATE ExternalUser e SET e.password = :password WHERE e.username = :username")
        int updatePasswordByUsername(String username, String password);
}