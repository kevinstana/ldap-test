package gr.hua.it21774.respository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.entities.ReviewingDates;
import jakarta.transaction.Transactional;

@Repository
public interface ReviewingDatesRepository extends JpaRepository<ReviewingDates, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE ReviewingDates rd SET rd.from = :from, rd.to = :to WHERE rd.id = :id")
    void updateDate(Long id, Instant from, Instant to);
}
