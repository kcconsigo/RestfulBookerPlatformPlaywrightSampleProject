package org.pages;

import org.basePage.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AdminPage extends BasePage {

    private final Locator adminMenu;

    public AdminPage(Page page) {
        super(page);
        this.adminMenu = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Admin").setExact(true));
    }

    public void adminMenuNavigation() {
        adminMenu.click();
    }
}
