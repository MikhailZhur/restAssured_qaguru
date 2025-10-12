package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class DeleteSpec {

    public static RequestSpecification deleteRequestSpec = with()
            .filter(new AllureRestAssured())
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api/users/2");

    public static ResponseSpecification deleteResponseSpecStatusCode204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .log(BODY)
            .build();
}
