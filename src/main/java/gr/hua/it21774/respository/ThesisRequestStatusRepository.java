package gr.hua.it21774.respository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import gr.hua.it21774.entities.ThesisRequestStatus;

@Repository
public interface ThesisRequestStatusRepository extends JpaRepository<ThesisRequestStatus, Long> {

}
