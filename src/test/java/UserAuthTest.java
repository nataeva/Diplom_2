import io.restassured.response.Response;
import model.AuthResponseModel;
import model.UserRequestModel;
import org.junit.Test;
import util.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAuthTest extends BaseTest {

    @Test
    public void authorizeExistingUserTest() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        postNewUser(elena);

        Response response = authorizeUser(elena);

        assertEquals("Code should be 200!", 200, response.getStatusCode());
        assertTrue("User auth should have succeeded!", response.as(AuthResponseModel.class).isSuccess());
    }

    @Test
    public void authorizeExistingUserWithWrongCredentialsTest() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        postNewUser(elena);

        Response response = authorizeUser(new UserRequestModel("wrongEmail@mail.ru", "wrongPassword", "Elena"));

        assertEquals("User auth should have failed because of wrong credentials!",
                401,
                response.getStatusCode());
    }
}
