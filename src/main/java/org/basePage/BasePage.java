package org.basePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class BasePage {
    protected Page page;
    private final Locator pageHeading;

    // The constructor accepts the Page object from Playwright
    public BasePage(Page page) {
        this.page = page;
        this.pageHeading = page.locator("h1");
    }

    // Global utility: Get page title
    public String getPageTitle() {
        return page.title();
    }

    public String getPageUrl() {
        return page.url();
    }

    public String getHeadingText() { return this.pageHeading.textContent().trim();}
    public boolean isHeadingVisible() {
        return this.pageHeading.isVisible();
    }

    public void waitForUrl(String urlRegex) {
        page.waitForURL(urlRegex);
    }

    public void waitForPageToLoad() {
        page.waitForLoadState();
    }
    public void waitForTimeout(double milliseconds) {
        page.waitForTimeout(milliseconds);
    }
    public void waitForElementVisible(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }
    public void waitForElementHidden(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }
}
