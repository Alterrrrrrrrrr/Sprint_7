import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class CourierLoginFailsParametrizedTest {

    private static String login;
    private static String password;

    public CourierLoginFailsParametrizedTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getLoginFailsTestData() {
        return new Object[][]{

                { "", "123456" },
                { "wott", "" },
                { null, "123456", },
                { "wott", null }
        };
    }

    @Test
    @Description("Параметризованные тесты на логин без нужных данных")
    @Step
    public void checkResultOnFailedCourierLogin() {

        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getCourier(login, password);

        ValidatableResponse loginFailedResponse = courierClient.login(CourierCredentials.from(courier));

        int loginFailedStatusCode = loginFailedResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_BAD_REQUEST, loginFailedStatusCode); //400

        String existingCourierCreationError = loginFailedResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", existingCourierCreationError);
    }
}
