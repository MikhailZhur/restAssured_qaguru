package models.pojo;

import lombok.Setter;

@Setter
public class LoginBodyPojoModels {

    String email;
    String password;

    public LoginBodyPojoModels(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginBodyPojoModels() {
    }

}

