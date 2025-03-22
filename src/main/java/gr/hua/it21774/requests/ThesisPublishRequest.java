package gr.hua.it21774.requests;

import org.springframework.web.multipart.MultipartFile;

public class ThesisPublishRequest {
    private MultipartFile pdf;

    public ThesisPublishRequest() {
    }

    public ThesisPublishRequest(MultipartFile pdf) {
        this.pdf = pdf;
    }

    public MultipartFile getPdf() {
        return pdf;
    }

    public void setPdf(MultipartFile pdf) {
        this.pdf = pdf;
    }
}
