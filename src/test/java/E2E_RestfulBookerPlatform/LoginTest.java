package E2E_RestfulBookerPlatform;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.options.AriaRole; // Added for the button click
import org.pages.LoginPage;
import org.pages.AdminPage;
import org.pages.DashboardPage;
import org.pages.LoginPage;
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

        loginPage.clickLogout();

        page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);

        if (!page.url().equals(appUrl + "/")) {
            page.navigate(appUrl);
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
        }

        RoomType testRoom = RoomType.DOUBLE;
        boolean isPriceCorrect = roomPage.verifyRoomPrice(testRoom);
        Assert.assertTrue(isPriceCorrect, "The pricing layout failed validation for: " + testRoom.getTypeName());

        roomPage.clickBookNow(testRoom);
    }
}