package E2E_RestfulBookerPlatform;

import com.microsoft.playwright.*;
import org.pages.AdminPage;
import org.testng.annotations.Test;
import org.basePage.BasePage;
import org.pages.LoginPage;
import org.pages.DashboardPage;


public class LoginTest extends BaseTest{
    @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(page);
        AdminPage adminPage = new AdminPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);

        String appUrl = dotenv.get("BASE_URL");

        loginPage.navigateTo(appUrl);
        adminPage.adminMenuNavigation();
        String user = dotenv.get("ADMIN_USERNAME");
        String pass = dotenv.get("ADMIN_PASSWORD");

        loginPage.login(user, pass);
        loginPage.getPageTitle();

        adminPage.waitForPageToLoad();
        adminPage.waitForTimeout(5000);
        loginPage.clickLogout();
        dashboardPage.isHeadingVisible();

        page.pause();
    }

}
