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

public class CourierCreationTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getCourier();
    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }

    @Test
    @Description("Успешный запрос создания курьера")
    @Step
    public void courierCreationSuccess() {

        ValidatableResponse createResponse = courierClient.create(courier);
        int createStatusCode = createResponse.extract().statusCode();

        assertEquals(HttpStatus.SC_CREATED, createStatusCode); //201

        boolean created = createResponse.extract().path("ok");
        assertTrue(created);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(loginStatusCode, HttpStatus.SC_OK); //200
        courierId = loginResponse.extract().path("id");

        assertNotEquals(courierId, 0);
    }

    @Test
    @Description("Успешный запрос создания курьера: только обязательные поля (firstName = null)")
    @Step
    public void courierCreationWithNullFirstNameSuccess() {

        courier.setFirstName(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        int createStatusCode = createResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_CREATED, createStatusCode); //201

        boolean created = createResponse.extract().path("ok");
        assertTrue(created);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(loginStatusCode, HttpStatus.SC_OK); //200
        courierId = loginResponse.extract().path("id");

        assertNotEquals(courierId, 0);
    }

    @Test
    @Description("Проверка невозможности создания существующего курьера")
    @Step
    public void existingCourierCreationFails() {

        courierClient.create(courier);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");

        ValidatableResponse secondCreateResponse = courierClient.create(courier);
        int creationErrorStatusCode = secondCreateResponse.extract().statusCode();
        assertEquals(creationErrorStatusCode, HttpStatus.SC_CONFLICT); //409

        String existingCourierCreationError = secondCreateResponse.extract().path("message");
        assertEquals("Этот логин уже используется", existingCourierCreationError);
    }
}
