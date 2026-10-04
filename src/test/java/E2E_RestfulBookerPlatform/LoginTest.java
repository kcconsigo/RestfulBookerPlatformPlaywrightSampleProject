package E2E_RestfulBookerPlatform;

import org.pages.AdminPage;
import org.pages.DashboardPage;
import org.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {
        LoginPage loginPage = new LoginPage(page);
        AdminPage adminPage = new AdminPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);

        String appUrl = dotenv.get("BASE_URL");
        String user = dotenv.get("ADMIN_USERNAME");
        String pass = dotenv.get("ADMIN_PASSWORD");

        loginPage.navigateTo(appUrl);
        adminPage.adminMenuNavigation();

        loginPage.login(user, pass);

        Assert.assertTrue(loginPage.isLogoutButtonVisible(),
                "Logout button should be visible after successful login");

        loginPage.clickLogout();
        Assert.assertTrue(dashboardPage.isHeadingVisible(),
                "Dashboard heading should be visible after logout");
    }
}