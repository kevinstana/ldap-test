package gr.hua.it21774.requests;

import org.springframework.web.multipart.MultipartFile;

public class ThesisRequestReq {
    private MultipartFile pdf;
    private String description;

    public ThesisRequestReq() {
    }

    public ThesisRequestReq(MultipartFile pdf, String description) {
        this.pdf = pdf;
        this.description = description;
    }

    public MultipartFile getPdf() {
        return pdf;
    }

    public void setPdf(MultipartFile pdf) {
        this.pdf = pdf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
