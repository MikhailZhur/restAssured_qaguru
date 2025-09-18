import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    @Test
    void listUsersCheckPage() {
        get("https://reqres.in/api/users?page=2")
                .then()
                .body("page", is(2));
    }

    @Test
    void listUsersCheckPageWithLogResponse() {
        get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

    @Test
    void listUsersCheckPageWithLogRequest() {
        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

    @Test
//    Делаем пост запрос /api/login с телом {
//    "email": "eve.holt@reqres.in",
//    "password": "pistol"
//}
//    проверяем что пришел токен

    void successfulLoginTest() {
        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }

}
