import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    @Test
    void listUsersCheckPage(){
        get("https://reqres.in/api/users?page=2")
                .then()
                .body("page", is(2));
    }

    @Test
    void listUsersCheckPageWithLogResponse(){
        get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

    @Test
    void listUsersCheckPageWithLogRequest(){
        given()
                .log().all()
                .header("x-api-key","reqres-free-v1")
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", is(2));
    }

}
