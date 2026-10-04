package org.pages;

import org.basePage.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    // Define Locators using Playwright's lazy strategies
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator logoutButton;

    public LoginPage(Page page) {
        super(page); // Pass page context to BasePage

        // Initialize locators safely; they won't query the DOM until acted upon
        this.usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username"));
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.logoutButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logout"));
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public AdminPage login(String user, String pass) {
        usernameInput.fill(user);
        passwordInput.fill(pass);
        loginButton.click();

        // Return an instance of AdminPage instead
        return new AdminPage(page);
    }
    public void clickLogout(){
        logoutButton.click();
    }

    public boolean isLogoutButtonVisible() {
        return logoutButton.isVisible();
    }
}
