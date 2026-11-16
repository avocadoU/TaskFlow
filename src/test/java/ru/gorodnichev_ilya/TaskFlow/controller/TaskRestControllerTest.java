package ru.gorodnichev_ilya.TaskFlow.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class TaskRestControllerTest {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        RestAssured.authentication = RestAssured.basic("admin", "admin123");
    }

    @Test
    void testSearchTasks_Success() {
        given()
                .queryParam("status", "TODO")
                .queryParam("priority", 1)
                .when()
                .get("/api/tasks/search")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    void testSearchTasks_MissingParam() {
        given()
                .queryParam("status", "TODO")
                .when()
                .get("/api/tasks/search")
                .then()
                .statusCode(400);
    }

    @Test
    void testGetTasksByAssignee() {
        given()
                .queryParam("fullName", "admin")
                .when()
                .get("/api/tasks/by-assignee")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}