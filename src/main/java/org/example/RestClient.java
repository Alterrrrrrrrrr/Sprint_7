package org.example;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.http.ContentType.JSON;

public class RestClient {

    protected static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    @Step
    protected static RequestSpecification getBaseSpec() {

        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URI)
                .build();
    }
}
