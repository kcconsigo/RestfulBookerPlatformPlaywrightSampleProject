package E2E_RestfulBookerPlatform;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.pages.LoginPage;        
import org.pages.AdminPage;
import org.pages.DashboardPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {
        // 1. Initialize your Page Objects
        LoginPage loginPage = new LoginPage(page);
        AdminPage adminPage = new AdminPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);

        // 2. Grab environment configurations from your updated .env file
        String appUrl = dotenv.get("APP_URL");
        String user = dotenv.get("ADMIN_USERNAME");
        String pass = dotenv.get("ADMIN_PASSWORD");

        // 3. Execute the login workflow steps
        loginPage.navigateTo(appUrl);
        adminPage.adminMenuNavigation();
        loginPage.login(user, pass);

        // 4. Verify successful login state
        Assert.assertTrue(loginPage.isLogoutButtonVisible(), "Logout button should be visible after successful login");

        // 5. Log out cleanly
        loginPage.clickLogout();

        // Debug output to verify page state post-logout
        System.out.println("Current URL: " + page.url());
        System.out.println("Page title: " + page.title());
    }
}