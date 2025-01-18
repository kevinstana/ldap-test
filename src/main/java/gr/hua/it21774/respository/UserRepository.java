package gr.hua.it21774.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.EnabledUserDTO;
import gr.hua.it21774.dto.UserListDTO;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.enums.ERole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        @Query("SELECT new gr.hua.it21774.dto.UserListDTO(u.id, u.username, u.email, u.firstName, u.lastName, u.createdAt, u.isEnabled, r.role) "
                        +
                        "FROM User u JOIN Role r ON u.roleId = r.id ")
        List<UserListDTO> customFindAll();

        @Query("SELECT new gr.hua.it21774.dto.UserListDTO(u.id, u.username, u.email, u.firstName, u.lastName, u.createdAt, u.isEnabled, r.role) "
                        +
                        "FROM User u JOIN Role r ON u.roleId = r.id "
                        + "WHERE (:roles IS NULL OR r.role IN :roles) "
                        + "ORDER BY u.id ASC")
        Page<UserListDTO> customFindAll(Pageable pageable, List<ERole> roles);

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
}