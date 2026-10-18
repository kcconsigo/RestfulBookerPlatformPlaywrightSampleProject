package E2E_RestfulBookerPlatform;

import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitUntilState;
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

        loginPage.navigateTo(appUrl);
        adminPage.adminMenuNavigation();
        loginPage.login(user, pass);

        Assert.assertTrue(loginPage.isLogoutButtonVisible(),
                "Logout button should be visible after successful login");
        
        try {
            page.navigate(appUrl + "/admin/rooms",
                    new com.microsoft.playwright.Page.NavigateOptions()
                            .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                            .setTimeout(60_000));
        } catch (PlaywrightException e) {
            if (e.getMessage() != null && e.getMessage().contains("net::ERR_ABORTED")) {
                System.out.println("Navigation aborted to /admin/rooms but continuing; URL is: " + page.url());
            } else {
                throw e;
            }
        }

        // Use Playwright Java API: navigate, not goto
        page.navigate(appUrl + "/admin/rooms");
        page.waitForLoadState();

        RoomType testRoom = RoomType.DOUBLE;
        boolean isPriceCorrect = roomPage.verifyRoomPrice(testRoom);
        Assert.assertTrue(isPriceCorrect,
                "The pricing layout failed validation for: " + testRoom.getTypeName());

        roomPage.clickBookNow(testRoom);
    }
}
