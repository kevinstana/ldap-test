package gr.hua.it21774.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.entities.Thesis;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Long> {

}
