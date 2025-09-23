package models.pojo;

public class LoginBodyPojoModels {

    String email;
    String password;

    public LoginBodyPojoModels(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginBodyPojoModels() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

