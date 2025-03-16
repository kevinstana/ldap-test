package gr.hua.it21774.dto;

import java.time.Instant;
import java.util.List;

import gr.hua.it21774.enums.ETaskPriorityStatus;
import gr.hua.it21774.enums.ETaskStatus;

public class TasksDTO {

    private Long id;
    private String title;
    private String description;
    private ETaskPriorityStatus priority;
    private ETaskStatus status;
    private Instant createdAt;
    private List<TaskFilesDTO> files;

    public TasksDTO() {
    }

    public TasksDTO(Long id, String title, String description, ETaskPriorityStatus priority,
            ETaskStatus status, Instant createdAt, List<TaskFilesDTO> files) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.files = files;
    }

    public TasksDTO(Long id, String title, String description, ETaskPriorityStatus priority,
            ETaskStatus status, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
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

    public ETaskPriorityStatus getPriority() {
        return priority;
    }

    public void setPriority(ETaskPriorityStatus priority) {
        this.priority = priority;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public void setStatus(ETaskStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<TaskFilesDTO> getFiles() {
        return files;
    }

    public void setFiles(List<TaskFilesDTO> files) {
        this.files = files;
    }
}
