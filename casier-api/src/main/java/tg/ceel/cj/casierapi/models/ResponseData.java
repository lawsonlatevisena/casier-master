package tg.ceel.cj.casierapi.models;

public class ResponseData {
    private String token;
    private Long expiry;

    public ResponseData() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "token='" + token + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}
