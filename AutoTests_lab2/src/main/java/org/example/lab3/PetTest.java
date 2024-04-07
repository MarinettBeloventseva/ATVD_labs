package org.example.lab3;

import com.github.javafaker.Faker;
import com.github.javafaker.IdNumber;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
import static java.util.Map.of;

public class PetTest {
    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final String USER = "/user",
            USER_USERNAME = USER + "/{username}",
            USER_LOGIN = USER + "/login",
            USER_LOGOUT = USER + "/logout";

    private Integer petId;
    private String petname;
    private String category;
    private String status;

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyLoginAction() {
        Map<String, ?> body = of(
                "username", "MarinaBeloventseva",
                "password", "122-20-4.1"
        );
        Response response = given().body(body).get(USER_LOGIN);
        response.then().statusCode(HttpStatus.SC_OK);
        RestAssured.requestSpecification.sessionId(response.jsonPath()
                .get("message").toString()
                .replaceAll("[^0-9]",""));
    }

    @Test(dependsOnMethods = "verifyLoginAction")
    public void verifyPostAction() {
        petId = Faker.instance().random().nextInt(5);
        petname = Faker.instance().name().username();
        category = Faker.instance().animal().name();
        status = "available";
        Map<String, ?> body = Map.of(
                "id", petId,
                "category", category,
                "name", petname,
                "photoUrls", Faker.instance().internet().url(),
                "tags", Faker.instance().animal().name(),
                "status", status);
        given().body(body)
                .post(USER)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyPostAction")
    public void verifyGetAction() {
        given().pathParam("petId", petId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("petId", equalTo(petId));
    }

}
