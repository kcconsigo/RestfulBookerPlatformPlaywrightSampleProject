package E2E_RestfulBookerPlatform;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.pages.LoginPage;
import org.pages.AdminPage;
import org.pages.DashboardPage;
import org.pages.RoomPage;
import org.data.RoomType;

public class LoginTest extends BaseTest {

    @Test
    public void loginAndVerifyRoomTypeTest() {
        LoginPage loginPage = new LoginPage(page);
        AdminPage adminPage = new AdminPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);
        RoomPage roomPage = new RoomPage(page);

        String appUrl = dotenv.get("APP_URL");
        String user = dotenv.get("ADMIN_USERNAME");
        String pass = dotenv.get("ADMIN_PASSWORD");

        // Navigate to app
        loginPage.navigateTo(appUrl);
        adminPage.adminMenuNavigation();
        loginPage.login(user, pass);

        Assert.assertTrue(loginPage.isLogoutButtonVisible(),
                "Logout button should be visible after successful login");

        // Logout, then wait for redirect to finish
        loginPage.clickLogoutAndWait();

        // Only navigate again if still not on the expected app URL
        if (!page.url().startsWith(appUrl)) {
            loginPage.safeNavigate(appUrl);
        }

        loginPage.waitForPageToLoad();

        RoomType testRoom = RoomType.DOUBLE;
        boolean isPriceCorrect = roomPage.verifyRoomPrice(testRoom);
        Assert.assertTrue(isPriceCorrect,
                "The pricing layout failed validation for: " + testRoom.getTypeName());

        roomPage.clickBookNow(testRoom);
    }
}