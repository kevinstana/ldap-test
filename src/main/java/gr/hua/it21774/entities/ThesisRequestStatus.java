package gr.hua.it21774.entities;

import org.hibernate.annotations.ColumnTransformer;

import gr.hua.it21774.enums.EThesisRequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "thesis_request_status")
public class ThesisRequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(read = "status::text", write = "?::thesis_request_status_type")
    @Column(nullable = false, unique = true)
    private EThesisRequestStatus status;

    public ThesisRequestStatus() {
    }

    public ThesisRequestStatus(EThesisRequestStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EThesisRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EThesisRequestStatus status) {
        this.status = status;
    }
}
