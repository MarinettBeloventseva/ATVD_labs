package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ServerTests {
    private static final String baseUrl = "https://718d84e4-a67f-4cb9-b376-959cb5713769.mock.pstmn.io";
    private static final String GET_USER = "/ownerName",
            POST_USER = "/createSomething",
            PUT_USER = "/updateMe",
            DELETE_USER = "/deleteWorld";

    private String param;

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyGetSuccessAction() {
        given().get(GET_USER + "/success")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void verifyGetUnsuccessAction() {
        given().get(GET_USER + "/unsuccess")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void verifyPostSuccessAction() {
        param = "?permission=yes";
        given().post(POST_USER + param)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void verifyPostUnsuccessAction() {
        given().post(POST_USER)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void verifyPutAction() {
        Map<String, ?> body = Map.of(
                "name", "",
                "surname", "");
        given().body(body).put(PUT_USER)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void verifyDeleteAction() {
        given().delete(DELETE_USER)
                .then()
                .statusCode(HttpStatus.SC_GONE);
    }
}
