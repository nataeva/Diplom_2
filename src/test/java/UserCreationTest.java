import io.restassured.response.Response;
import model.UserRequestModel;
import org.junit.Test;
import util.BaseTest;

import static org.junit.Assert.assertEquals;

public class UserCreationTest extends BaseTest {

    @Test
    public void createUniqueUserTest() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");

        postNewUser(elena);
    }

    @Test
    public void createAlreadyExistingUserTest() {
        String uniqueId = getUniqueId();
        UserRequestModel elena = new UserRequestModel("enataeva" + uniqueId + "@mail.ru", "1234", "Elena");
        usersToDelete.add(elena);

        Response response = createUser(elena);

        assertEquals("User should have been created!",
                200,
                response.getStatusCode());

        Response failedResponse = createUser(elena);

        assertEquals("Should have received status code 403 because user already exists!",
                403,
                failedResponse.getStatusCode());
    }

    @Test
    public void createUniqueUserWithoutRequiredFieldTest() {
        UserRequestModel user1 = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", null);
        UserRequestModel user2 = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", null, "Elena");
        UserRequestModel user3 = new UserRequestModel(null, "1234", "Elena");

        Response failedResponse2 = createUser(user1);
        assertEquals("Should have received status code 403 because there are missing required fields!",
                403,
                failedResponse2.getStatusCode());

        Response failedResponse1 = createUser(user2);
        assertEquals("Should have received status code 403 because there are missing required fields!",
                403,
                failedResponse1.getStatusCode());

        Response failedResponse = createUser(user3);
        assertEquals("Should have received status code 403 because there are missing required fields!",
                403,
                failedResponse.getStatusCode());
    }
}
