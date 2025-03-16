package gr.hua.it21774.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class TaskRequest {

    private String title;
    private String description;
    private String priority;
    private List<MultipartFile> files;

    public TaskRequest() {
    }

    public TaskRequest(String title, String description, String priority, List<MultipartFile> files) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.files = files;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
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
}
