package E2E_RestfulBookerPlatform;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.options.AriaRole; // Added for the button click
import org.pages.LoginPage;
import org.pages.AdminPage;
import org.pages.DashboardPage;
import org.pages.RoomPage;
import org.data.RoomType;

public class LoginTest extends BaseTest {

    @Test
    public void loginAndVerifyRoomTypeTest() {
        // Initialize your Page Objects
        LoginPage loginPage = new LoginPage(page);
        AdminPage adminPage = new AdminPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);
        RoomPage roomPage = new RoomPage(page);

        // Grab environment configurations
        String appUrl = dotenv.get("APP_URL");
        String user = dotenv.get("ADMIN_USERNAME");
        String pass = dotenv.get("ADMIN_PASSWORD");

        // Execute login workflow
        loginPage.navigateTo(appUrl);
        adminPage.adminMenuNavigation();
        loginPage.login(user, pass);

        // Verify successful login state
        Assert.assertTrue(loginPage.isLogoutButtonVisible(), "Logout button should be visible after successful login");

        // 1. Click logout cleanly (this naturally kicks off the redirect)
        loginPage.clickLogout();

        // 2. 🚀 Instead of page.navigate(), just wait for the network requests to finish settling down
        page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);

        // If the logout function doesn't automatically take you to the root home page,
        // you can cleanly go back to the base URL here without getting aborted:
        if (!page.url().equals(appUrl + "/")) {
            page.navigate(appUrl);
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
        }

        // 3. Run your public room validations (now using div.hotel-room)
        RoomType testRoom = RoomType.DOUBLE;
        boolean isPriceCorrect = roomPage.verifyRoomPrice(testRoom);
        Assert.assertTrue(isPriceCorrect, "The pricing layout failed validation for: " + testRoom.getTypeName());

        roomPage.clickBookNow(testRoom);
    }
}