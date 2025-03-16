package gr.hua.it21774.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UpdateTaskRequest {

    private String title;
    private String description;
    private String priority;
    private String status;
    private List<String> files;
    private List<MultipartFile> newFiles;

    public UpdateTaskRequest() {
    }

    public UpdateTaskRequest(String title, String description, String priority, String status, List<String> files,
            List<MultipartFile> newFiles) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.files = files;
        this.newFiles = newFiles;
    }

    public List<MultipartFile> getNewFiles() {
        return newFiles;
    }

    public void setNewFiles(List<MultipartFile> newFiles) {
        this.newFiles = newFiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
