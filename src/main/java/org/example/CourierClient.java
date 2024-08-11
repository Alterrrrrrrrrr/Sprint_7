package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "/api/v1/courier/";
    private static final String DELETE_PATH = "/api/v1/courier/";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    @Step
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
                //.log().all();
    }

    @Step
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(LOGIN_PATH)
                .then();
                //.log().all();

    }

    @Step
    public ValidatableResponse delete(int courierId) {
        Map<String, Object> courierIdMap = new HashMap<>();
        courierIdMap.put("id", courierId);
        return given()
                .spec(getBaseSpec())
                .body(courierIdMap)
                .when()
                .delete(DELETE_PATH + courierId)
                .then();
                //.log().all();
    }
}
