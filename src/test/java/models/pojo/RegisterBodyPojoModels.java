package models.pojo;

import lombok.Setter;

@Setter
public class RegisterBodyPojoModels {

    String email;
    String password;

    public RegisterBodyPojoModels(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterBodyPojoModels() {
    }
}
