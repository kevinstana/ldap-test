package gr.hua.it21774.respository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.entities.AssignmentDates;
import jakarta.transaction.Transactional;

@Repository
public interface AssignmentDatesRepository extends JpaRepository<AssignmentDates, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE AssignmentDates ad SET ad.from = :from, ad.to = :to WHERE ad.id = :id")
    void updateDate(Long id, Instant from, Instant to);
}
