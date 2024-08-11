import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class CourierCreationFailsParametrizedTest {

    private static String login; //
    private static String password;
    private static String firstName;

    public CourierCreationFailsParametrizedTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getCreationFailsTestData() {
        return new Object[][]{

                { "", "123456", "August" },
                { "wott", "", "August" },
                { null, "123456", "August" },
                { "wott", null, "August" },
        };
    }

@Test
@Description("Параметризованные тесты на создание курьера без необходимых данных")
@Step
    public void checkResultOnFailedCourierCreations() {

    CourierClient courierClient = new CourierClient();
    Courier courier = CourierGenerator.getCourier(login, password, firstName);

    ValidatableResponse createFailedResponse = courierClient.create(courier);

    int createFailedStatusCode = createFailedResponse.extract().statusCode();
    assertEquals(HttpStatus.SC_BAD_REQUEST, createFailedStatusCode); //400

    String existingCourierCreationError = createFailedResponse.extract().path("message");
    assertEquals("Недостаточно данных для создания учетной записи", existingCourierCreationError);
    }
}
