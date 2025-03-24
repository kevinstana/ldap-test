package gr.hua.it21774.respository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.dto.EnabledUserDTO;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.enums.ERole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new gr.hua.it21774.dto.CommonUserDTO(u.id, u.username, u.email, u.firstName, u.lastName, u.createdAt, r.role, u.isEnabled, u.lastModified, lm.username AS lastModifiedBy) "
            +
            "FROM User u " +
            "JOIN Role r ON u.roleId = r.id " +
            "LEFT JOIN User lm ON u.lastModifiedBy = lm.id " +
            "WHERE (:roles IS NULL OR r.role IN :roles) " +
            "AND (:enabled IS NULL OR u.isEnabled = :enabled) " +
            "AND (:query IS NULL OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "ORDER BY u.id ASC")
    Page<CommonUserDTO> customFindAll(Pageable pageable, List<ERole> roles, Boolean enabled, String query);

    @Query("SELECT new gr.hua.it21774.dto.CommonUserDTO(u.id, u.username, u.email, u.firstName, u.lastName, u.createdAt, r.role, u.isEnabled, u.lastModified, lm.username AS lastModifiedBy) "
            +
            "FROM User u " +
            "LEFT JOIN ExternalUser e ON u.username = e.username " +
            "JOIN Role r ON u.roleId = r.id " +
            "LEFT JOIN User lm ON u.lastModifiedBy = lm.id " +
            "WHERE e.username IS NULL " +
            "AND (:roles IS NULL OR r.role IN :roles) " +
            "AND (:enabled IS NULL OR u.isEnabled = :enabled) " +
            "AND (:query IS NULL OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "ORDER BY u.id ASC")
    Page<CommonUserDTO> customFindAllHua(Pageable pageable, List<ERole> roles, Boolean enabled, String query);

    Boolean existsByEmail(String email);

    @Query("SELECT " +
            "CASE " +
            "WHEN COUNT(u) > 0 " +
            "THEN true " +
            "ELSE false " +
            "END " +
            "FROM User u " +
            "JOIN Role r ON u.roleId = r.id " +
            "WHERE u.id = :id AND r.role = :role")
    Boolean hasRole(Long id, ERole role);

    @Query("SELECT " +
            "CASE " +
            "WHEN COUNT(u) = 2 " +
            "THEN true " +
            "ELSE false " +
            "END " +
            "FROM User u " +
            "JOIN Role r ON u.roleId = r.id " + "WHERE u.id IN (:id1, :id2) AND r.role = 'PROFESSOR'")
    Boolean areProfessors(Long id1, Long id2);

    @Query("SELECT new gr.hua.it21774.dto.EnabledUserDTO(u.id, u.isEnabled) FROM User u WHERE u.username = :username")
    Optional<EnabledUserDTO> findIdAndIsEnabledByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.isEnabled = :isEnabled, u.lastModified = :lastModified, u.lastModifiedBy = :lastModifiedBy WHERE u.id = :id")
    int updateUser(Long id, Boolean isEnabled, Instant lastModified, Long lastModifiedBy);

    @Query("""
                SELECT new gr.hua.it21774.dto.CommonUserDTO(
                    u.id,
                    u.username,
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
                JOIN Role r ON u.roleId = r.id
                LEFT JOIN User lm ON u.lastModifiedBy = lm.id
                WHERE u.username = :username
            """)
    Optional<CommonUserDTO> getUserProfile(String username);

    @Query("SELECT new gr.hua.it21774.dto.CommonUserDTO(u.id, u.username, u.firstName, u.lastName) " +
            "FROM User u " +
            "JOIN Role r ON u.roleId = r.id WHERE r.role = 'PROFESSOR' " +
            "AND u.id NOT IN :excludedIds " +
            "AND (" +
            "    LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            "    OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            "    OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            ") " +
            "ORDER BY u.username ASC " +
            "LIMIT 5")
    List<CommonUserDTO> assignProfessors(String query, List<Long> excludedIds);

    @Query("SELECT new gr.hua.it21774.dto.CommonUserDTO(u.id, u.username, u.firstName, u.lastName) " +
            "FROM User u " +
            "JOIN Role r ON r.id = u.roleId WHERE r.id = :roleId " +
            "AND (" +
            "    LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            "    OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            "    OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            "    OR LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) "
            +
            ") " +
            "ORDER BY u.username ASC " +
            "LIMIT 5")
    List<CommonUserDTO> searchStudents(String query, Long roleId);

}