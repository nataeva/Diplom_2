import io.restassured.response.Response;
import model.AuthResponseModel;
import model.PatchResponseModel;
import model.UserRequestModel;
import org.junit.Test;
import util.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserChangingTest extends BaseTest {

    @Test
    public void changeUserWithAuth() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        postNewUser(elena);
        AuthResponseModel authResponseModel = authorizeUser(elena).as(AuthResponseModel.class);

        String newName = "Elena Nataeva";
        elena.setName(newName);

        Response response1 = updateUser(elena, authResponseModel.getAccessToken());

        assertEquals("Code should be 200!", 200, response1.getStatusCode());
        assertTrue("User update should have succeeded!", response1.as(PatchResponseModel.class).isSuccess());
        assertEquals("This field should have been updated!", newName, response1.as(PatchResponseModel.class).getUser().getName());


        String newEmail = "newemail" + getUniqueId() + "@gmail.com";
        elena.setEmail(newEmail);

        Response response2 = updateUser(elena, authResponseModel.getAccessToken());

        assertEquals("Code should be 200!", 200, response2.getStatusCode());
        assertTrue("User update should have succeeded!", response2.as(PatchResponseModel.class).isSuccess());
        assertEquals("This field should have been updated!", newEmail, response2.as(PatchResponseModel.class).getUser().getEmail());


        String newPassword = "123456";
        elena.setPassword(newPassword);

        Response response3 = updateUser(elena, authResponseModel.getAccessToken());

        assertEquals("Code should be 200!", 200, response3.getStatusCode());
        assertTrue("User update should have succeeded!", response3.as(PatchResponseModel.class).isSuccess());

        Response authWithNewPassword = authorizeUser(elena);

        assertEquals("Should have authorized with new password!",
                200,
                authWithNewPassword.getStatusCode());
    }

    @Test
    public void changeUserWithoutAuth() {
        UserRequestModel elena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        UserRequestModel updatedElena = new UserRequestModel("enataeva" + getUniqueId() + "@mail.ru", "1234", "Elena");
        postNewUser(elena);

        String newName = "Elena Nataeva";
        updatedElena.setName(newName);

        Response response1 = updateUser(updatedElena, "");

        assertEquals("Code should be 401!", 401, response1.getStatusCode());


        String newEmail = "newemail" + getUniqueId() + "@gmail.com";
        updatedElena.setEmail(newEmail);

        Response response2 = updateUser(updatedElena, "");

        assertEquals("Code should be 401!", 401, response2.getStatusCode());


        String newPassword = "123456";
        updatedElena.setPassword(newPassword);

        Response response3 = updateUser(updatedElena, "");

        assertEquals("Code should be 401!", 401, response3.getStatusCode());
    }
}
