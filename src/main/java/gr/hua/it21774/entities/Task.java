package gr.hua.it21774.entities;

import java.time.Instant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 256, message = "Title cannot be longer than 256 characters.")
    private String title;

    @NotBlank
    private String description;

    @Column(name = "thesis_id")
    private Long thesisId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "priotiry_id")
    private Long priorityId;

    @Column(name = "status_id")
    private Long statusId;

    public Task() {
    }

    public Task(String title, String description, Long thesisId, Instant createdAt, Long priorityId, Long statusId) {
        this.title = title;
        this.description = description;
        this.thesisId = thesisId;
        this.createdAt = createdAt;
        this.priorityId = priorityId;
        this.statusId = statusId;
    }

    public Task(Long id, String title, String description, Long thesisId, Instant createdAt, Long priorityId,
            Long statusId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thesisId = thesisId;
        this.createdAt = createdAt;
        this.priorityId = priorityId;
        this.statusId = statusId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getThesisId() {
        return thesisId;
    }

    public void setThesisId(Long thesisId) {
        this.thesisId = thesisId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}