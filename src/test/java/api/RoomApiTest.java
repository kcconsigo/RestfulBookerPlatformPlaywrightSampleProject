package api;

import com.microsoft.playwright.APIResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.api.clients.AuthClient;
import org.api.clients.RoomClient;
import org.api.payloads.RoomPayload;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;

public class RoomApiTest extends BaseApiTest {
    private AuthClient authClient;
    private RoomClient roomClient;
    private String authToken;

    @BeforeMethod
    public void setupTest() {
        authClient = new AuthClient(request);
        roomClient = new RoomClient(request);

        // 1. Authenticate before running room test
        APIResponse loginResponse = authClient.login("admin", "password");
        Assert.assertEquals(loginResponse.status(), 200, "Login failed!");

        // 2. Parse the JSON body directly to get the token value
        String responseBody = loginResponse.text();
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

        if (jsonResponse.has("token")) {
            authToken = jsonResponse.get("token").getAsString();
        } else {
            Assert.fail("Auth token not found inside the response body payload! Body was: " + responseBody);
        }
    }

    @Test
    public void shouldCreateNewRoomAsAdmin() {
        // 1. Prepare Room Data Payload
        RoomPayload roomData = new RoomPayload(
                "101",
                "Suite",
                true,
                "Ocean view with king size bed.",
                "https://unsplash.com",
                "250",
                Arrays.asList("WiFi", "TV", "Safe")
        );

        // 2. Create the Room using the extracted authorization token
        APIResponse roomResponse = roomClient.createRoom(authToken, roomData);
        Assert.assertEquals(roomResponse.status(), 200, "Room creation failed!");

        // 3. Verify Response Payload matches {"success":true}
        String responseBody = roomResponse.text();
        JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();

        Assert.assertTrue(responseJson.has("success"), "Response body missing 'success' key!");
        Assert.assertTrue(responseJson.get("success").getAsBoolean(), "Room creation 'success' flag was false!");
    }
}