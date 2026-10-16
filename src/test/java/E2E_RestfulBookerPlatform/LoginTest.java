package E2E_RestfulBookerPlatform;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.pages.AdminPage;
import org.pages.DashboardPage;
import org.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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

        // Log out
        loginPage.clickLogout();

        // Debug the actual post-logout page
        System.out.println("Current URL: " + page.url());
        System.out.println("Page title: " + page.title());
    }
}