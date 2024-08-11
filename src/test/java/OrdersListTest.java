import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.OrderClient;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;



public class OrdersListTest {

    @Test
    public void checkOrdersList() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse orderClientResponse = orderClient.getAllOrders();

        orderClientResponse.assertThat().statusCode(HttpStatus.SC_OK).and().body("orders", notNullValue());
    }
}
