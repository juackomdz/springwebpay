package cl.spring.webpay.springwebpay.model;

public class CrearTokenModel {

    private String token;
    private String url;
    
    public CrearTokenModel() {
    }

    public CrearTokenModel(String token, String url) {
        this.token = token;
        this.url = url;
    }


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
}
