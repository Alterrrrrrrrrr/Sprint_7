package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String CREATE_ORDER_PATH = "/api/v1/orders/";
    private static final String CHECK_ORDER_PATH = "/api/v1/orders/track?t=";
    private static final String ALL_ORDERS_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse getOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then();
//                .log().all();
    }

    @Step
    public ValidatableResponse checkOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(CHECK_ORDER_PATH + track)
                .then();
//                .log().all();
    }

    @Step
    public ValidatableResponse getAllOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ALL_ORDERS_PATH)
                .then();
//                .log().all();
    }
}