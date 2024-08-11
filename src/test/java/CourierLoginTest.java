import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getCourier();
        courierClient.create(courier);
    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }

    @Test
    @Description("Успешный логин курьера")
    @Step
    public void courierLoginSuccess() {

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(loginStatusCode, HttpStatus.SC_OK); //200

        courierId = loginResponse.extract().path("id");
        assertNotEquals(courierId, 0);
    }

    @Test
    @Description("Безуспешный логин с некорректными данными")
    @Step
    public void courierLoginFail() {

        courier.setLogin("Unexistable");
        courier.setPassword("Unbelievable");

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(loginStatusCode, HttpStatus.SC_NOT_FOUND); //404

        String loginMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", loginMessage);
    }

}
