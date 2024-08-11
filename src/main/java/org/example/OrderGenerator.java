package org.example;

import io.qameta.allure.Step;
import java.util.List;

public class OrderGenerator {

    @Step
    public static Order getOrder() {
        return new Order();
    }

    @Step
    public static Order getOrder(
            String firstName,
            String lastName,
            String address,
            String metroStation,
            String phone,
            int rentTime,
            String deliveryDate,
            String comment,
            List<String> color
    )
    {
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhone(phone);
        order.setRentTime(rentTime);
        order.setDeliveryDate(deliveryDate);
        order.setComment(comment);
        order.setColor(color);
        return order;
    }
}
