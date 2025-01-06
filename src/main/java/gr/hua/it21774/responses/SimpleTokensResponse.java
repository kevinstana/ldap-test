package gr.hua.it21774.responses;

public class SimpleTokensResponse {
    private String accessToken;
    private String refreshToken;

    public SimpleTokensResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

}