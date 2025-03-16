package gr.hua.it21774.responses;

public class SignedUrlResponse {

    private String url;

    public SignedUrlResponse() {
    }

    public SignedUrlResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
