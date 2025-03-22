package gr.hua.it21774.entities;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name = "assignment_dates")
public class AssignmentDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private Instant from;

    @Column(name = "to_date")
    private Instant to;

    public AssignmentDates() {
    }

    public AssignmentDates(Long id, Instant from, Instant to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public AssignmentDates(Instant from, Instant to) {
        this.from = from;
        this.to = to;
    }

    public Long getId() {
        return id;
    }

    public Instant getFrom() {
        return from;
    }

    public Instant getTo() {
        return to;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public void setTo(Instant to) {
        this.to = to;
    }
}
