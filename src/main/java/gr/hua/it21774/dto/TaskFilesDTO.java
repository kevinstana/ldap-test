package gr.hua.it21774.dto;

public class TaskFilesDTO {
    private String fileName;
    private Long fileSize;

    public TaskFilesDTO() {
    }

    public TaskFilesDTO(String fileName, Long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
