package org.pages;

import org.basePage.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage {

    private final Locator welcomeHeading;

    public DashboardPage(Page page) {
        super(page);
        this.welcomeHeading = page.locator("h1.welcome-message");
    }

    public boolean isHeadingVisible() {
        return welcomeHeading.isVisible();
    }
}
