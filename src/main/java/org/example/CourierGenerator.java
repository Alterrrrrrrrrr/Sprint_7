package org.example;

//import org.apache.commons.lang3.RandomStringUtils;

import io.qameta.allure.Step;

public class CourierGenerator {

    @Step
    public static Courier getCourier() {
        Courier courier = new Courier();
        courier.setLogin("wott");
        courier.setPassword("123456");
        courier.setFirstName("August");
        return courier;
    }

    @Step
    public static Courier getCourier(String login, String password, String firstName) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

    @Step
    public static Courier getCourier(String login, String password) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        return courier;
    }

}
