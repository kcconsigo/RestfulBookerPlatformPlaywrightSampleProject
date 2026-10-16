package org.pages;

import com.microsoft.playwright.options.AriaRole;
import org.basePage.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage {

    private final Locator welcomeHeading;

    public DashboardPage(Page page) {
        super(page);
        this.welcomeHeading = page.locator("h1.display-4");
    }

    public boolean isHeadingVisible() {
        return page.getByRole(
                AriaRole.HEADING,
                new Page.GetByRoleOptions()
                        .setName("Welcome to Shady Meadows B&B")
        ).isVisible();
    }
}
