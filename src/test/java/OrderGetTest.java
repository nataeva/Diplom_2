import io.restassured.response.Response;
import model.AuthResponseModel;
import model.UserRequestModel;
import org.junit.Test;
import util.BaseTest;

import static org.junit.Assert.assertEquals;

public class OrderGetTest extends BaseTest {

    @Test
    public void getOrdersForAuthorizedUserTest() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        postNewUser(elena);
        AuthResponseModel authResponseModel = authorizeUser(elena).as(AuthResponseModel.class);

        Response orders = getOrders(authResponseModel.getAccessToken());

        assertEquals("Request should be successful!", 200, orders.statusCode());
    }

    @Test
    public void getOrdersForUnauthorizedUserTest() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        postNewUser(elena);

        Response orders = getOrders("");

        assertEquals("Request should have failed!", 401, orders.statusCode());
    }
}
