package models;

public class LoginBodyModels {

    String email;
    String password;

    public LoginBodyModels(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginBodyModels() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

