package tests;

import models.LoginBodyModels;
import models.LoginResponseModels;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresInTests {

    public static String BASEURL = "https://reqres.in";
    String requestLoginBody = "{\n" +
            "    \"email\": \"eve.holt@reqres.in\",\n" +
            "    \"password\": \"cityslicka\"\n" +
            "}";

    @Test
    void listUsersCheckPage() {
        get(BASEURL + "/api/users?page=2")
                .then()
                .body("page", is(2));
    }

    @Test
    void listUsersCheckPageWithLogResponse() {
        get(BASEURL + "/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

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

    @Test
    void successfulLoginTest() {
        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(requestLoginBody)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void successfullRegTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/register")
                .then()
                .log().all()
                .body("id", is(4), "token", is("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void fieldEmailIsRequiredWhenRegistering() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .body("{\n" +
                        "    \"email\": \"\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/register")
                .then()
                .log().all()
                .body("error", is("Missing email or username"));

    }

    @Test
    void fieldPasswordIsRequiredWhenRegistering() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"\"\n" +
                        "}")
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/register")
                .then()
                .log().all()
                .body("error", is("Missing password"));

    }

    @Test
    void successfulLoginBestPracticeTest() {

        LoginBodyModels authData = new LoginBodyModels("eve.holt@reqres.in", "cityslicka");

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

    @Tag("Negative")
    @Test
    void loginTestWithOutPassword() {

        LoginBodyModels authData = new LoginBodyModels("eve.holt@reqres.in", null);

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

    @Tag("Negative")
    @Test
    void loginTestWithOutPasswordAndMail() {

        LoginBodyModels authData = new LoginBodyModels();

        LoginResponseModels response = given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(LoginResponseModels.class);

        assertEquals("Missing email or username", response.getError());

    }

    @Tag("Negative")
    @Test
    void loginTestWithOutEmail() {

        LoginBodyModels authData = new LoginBodyModels(null, "cityslicka");

        LoginResponseModels response = given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(LoginResponseModels.class);

        assertEquals("Missing email or username", response.getError());

    }

    @Test
    void successfulLoginTestWithResponseModel() {

        LoginBodyModels authData = new LoginBodyModels();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModels response = given()
                .header("x-api-key", "reqres-free-v1")
                .body(authData)
                .contentType(JSON)
                .when()
                .post(BASEURL + "/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(LoginResponseModels.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }
}