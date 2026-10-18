package org.pages;

import org.basePage.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

public class LoginPage extends BasePage {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator logoutButton;

    public LoginPage(Page page) {
        super(page);

        this.usernameInput = page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Username"));
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Password"));
        this.loginButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login"));
        this.logoutButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Logout"));
    }

    public void navigateTo(String url) {
        page.navigate(url, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                .setTimeout(60_000));
    }

    public AdminPage login(String user, String pass) {
        usernameInput.fill(user);
        passwordInput.fill(pass);
        loginButton.click();

        return new AdminPage(page);
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public void clickLogoutAndWait() {
        logoutButton.click();
        page.waitForURL("**/", new Page.WaitForURLOptions().setTimeout(20_000));
        waitForPageToLoad(LoadState.DOMCONTENTLOADED, 10_000);
    }

    public boolean isLogoutButtonVisible() {
        return logoutButton.isVisible();
    }
}