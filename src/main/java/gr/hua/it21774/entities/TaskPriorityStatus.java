package gr.hua.it21774.entities;

import org.hibernate.annotations.ColumnTransformer;

import gr.hua.it21774.enums.ETaskPriorityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_priority")
public class TaskPriorityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(read = "priority::text", write = "?::task_priority_type")
    @Column(nullable = false, unique = true)
    private ETaskPriorityStatus priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ETaskPriorityStatus getStatus() {
        return priority;
    }

    public void setStatus(ETaskPriorityStatus priority) {
        this.priority = priority;
    }
}
