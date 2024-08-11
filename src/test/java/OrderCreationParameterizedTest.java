import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Order;
import org.example.OrderClient;
import org.example.OrderGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)

public class OrderCreationParameterizedTest {
    private static String firstName;
    private static String lastName;
    private static String address;
    private static String metroStation;
    private static String phone;
    private static int rentTime;
    private static String deliveryDate;
    private static String comment;
    private static List <String> color;

    public OrderCreationParameterizedTest(
            String firstName,
            String lastName,
            String address,
            String metroStation,
            String phone,
            int rentTime,
            String deliveryDate,
            String comment,
            List <String> color
    )
    {
       this.firstName = firstName;
       this.lastName = lastName;
       this.address = address;
       this.metroStation = metroStation;
       this.phone = phone;
       this.rentTime = rentTime;
       this.deliveryDate = deliveryDate;
       this.comment = comment;
       this.color = color;
    }



    @Parameterized.Parameters
    public static Object[][] getOrderCreationTestData() {
        return new Object[][]{

                { "Иоганн", "Гете", "Страсбургский университет", "7", "+49118584311", 2, "1772-05-28", "Sturm und Drang", List.of("BLACK") },
                { "Иоганн", "Бах", "Веймар, в 5 минутах ходьбы от дворца герцога", "4", "+49325082551", 1, "1711-07-12", "Der Spielmann", List.of("GREY") },
                { "Иоганн", "Штраус", "Санкт-Петербург, Павловский вокзал, оркестровая яма", "12", "+79210258722", 4, "1856-07-02", "Братья талантливее меня, просто я популярнее", List.of("BLACK", "GREY") },
                { "Иоганн", "Баптист Йозеф Фабиан Себастьян", "Тироль, ищи ветра в поле", "4", "+4906924881", 1, "1815-09-25", "Анне Плохль стало плохль, надо срочно ехать!", List.of("") }
                //{ "Иоганн", "Эрнст Саксен-Кобург-Заальфельдский", "Зальфельд, не найти даже с навигатором", 5, "+4977205752", "2", "1683-08-19", "А вы думаете нам, царям, легко?", List.of(null) }
        };
    }


    @Test
    @Description("Параметризованные тесты на создание заказа с разными цветами самоката")
    public void checkResultOrderCreation() {
        OrderClient orderClient = new OrderClient();
        Order order = OrderGenerator.getOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        ValidatableResponse createOrderResponse = orderClient.getOrder(order);

        int createOrderCodeStatus = createOrderResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_CREATED, createOrderCodeStatus); //201

        createOrderResponse.assertThat().body("track", notNullValue());
        int track = createOrderResponse.extract().path("track");

        ValidatableResponse checkOrderResponse = orderClient.checkOrder(track);
        checkOrderResponse.assertThat().body("order.color", equalTo(color));

    }
}
