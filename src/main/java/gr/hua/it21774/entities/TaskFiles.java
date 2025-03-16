package gr.hua.it21774.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "task_files")
public class TaskFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "filename", nullable = false)
    private String fileName;

    @Column(name = "filesize", nullable = false)
    private Long fileSize;

    public TaskFiles() {
    }

    public TaskFiles(Long taskId, String fileName, Long fileSize) {
        this.taskId = taskId;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
