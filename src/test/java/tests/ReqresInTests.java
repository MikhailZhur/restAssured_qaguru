package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.LoginBodyLombokModels;
import models.lombok.LoginResponseLombokModels;
import models.pojo.LoginBodyPojoModels;
import models.pojo.LoginResponsePojoModels;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class ReqresInTests {

    public static String BASEURL = "https://reqres.in";

    @Disabled
    @Test
    void listUsersCheckPage() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .get(BASEURL + "/api/users?page=2")
                .then()
                .body("page", is(3));
    }

    @Disabled
    @Test
    void listUsersCheckPageWithLogResponse() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .get(BASEURL + "/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

    @Disabled
    @Test
    void listUsersCheckPageWithLogRequest() {
        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .get(BASEURL + "/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

    @Disabled
    @Test
    void successfulLoginTest() {
        LoginBodyPojoModels authData = new LoginBodyPojoModels("eve.holt@reqres.in", "cityslicka");
        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }
    @Disabled
    @Test
    void successfullRegTest() {
        LoginBodyPojoModels authData = new LoginBodyPojoModels("eve.holt@reqres.in", "cityslicka");
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/register")
                .then()
                .log().all()
                .body("id", is(4), "token", is("QpwL5tke4Pnpja7X4"));

    }

    @Disabled
    @Test
    void successfulLoginBestPracticeTest() {

        LoginBodyPojoModels authData = new LoginBodyPojoModels("eve.holt@reqres.in", "cityslicka");

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }

    @Disabled
    @Tag("Negative")
    @Test
    void loginTestWithOutPassword() {

        LoginBodyPojoModels authData = new LoginBodyPojoModels("eve.holt@reqres.in", null);

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));

    }

    @Disabled
    @Tag("Negative")
    @Test
    void loginTestWithOutPasswordAndMail() {

        LoginBodyPojoModels authData = new LoginBodyPojoModels();

        LoginResponsePojoModels response = given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(LoginResponsePojoModels.class);

        assertEquals("Missing email or username", response.getError());

    }

    @Disabled
    @Tag("Negative")
    @Test
    void loginTestWithOutEmail() {

        LoginBodyPojoModels authData = new LoginBodyPojoModels(null, "cityslicka");

        LoginResponsePojoModels response = given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(LoginResponsePojoModels.class);

        assertEquals("Missing email or username", response.getError());

    }

    @Disabled
    @Test
    void successfulLoginTestWithResponseModel() {

        LoginBodyPojoModels authData = new LoginBodyPojoModels();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponsePojoModels response = given()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(LoginResponsePojoModels.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }

    @Disabled
    @Test
    void successfulLoginLombokTest() {

        LoginBodyLombokModels authData = new LoginBodyLombokModels();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslick");

        LoginResponseLombokModels response =
                given()
                        .filter(new AllureRestAssured())
                        .header("x-api-key", "reqres-free-v1")
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post(BASEURL + "/api/login")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(LoginResponseLombokModels.class);
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Disabled
    @Test
    void successfulLoginWithStepsTest() {

        LoginBodyLombokModels authData = new LoginBodyLombokModels();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslick");

        LoginResponseLombokModels response = step("Make request", () ->

                given()
                        .filter(new AllureRestAssured())
                        .header("x-api-key", "reqres-free-v1")
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post(BASEURL + "/api/login")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(LoginResponseLombokModels.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successfulLoginWithSpecTest() {

        LoginBodyLombokModels authData = new LoginBodyLombokModels();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslick");

        LoginResponseLombokModels response =
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(loginResponseSpecStatusCode200)
                        .extract().as(LoginResponseLombokModels.class);
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Tag("Negative")
    @Test
    void loginTestWithSpecWithOutEmail() {

        LoginBodyPojoModels authData = new LoginBodyPojoModels(null, "cityslicka");

        LoginResponsePojoModels response =
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(loginResponseSpecStatusCode400)
                        .extract().as(LoginResponsePojoModels.class);

        assertEquals("Missing email or username", response.getError());

    }


}