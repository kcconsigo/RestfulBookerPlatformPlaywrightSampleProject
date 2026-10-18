package org.basePage;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

public class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(LoadState.DOMCONTENTLOADED, 20_000);
    }

    public void waitForPageToLoad(LoadState state, double timeoutMillis) {
        page.waitForLoadState(state, new Page.WaitForLoadStateOptions().setTimeout(timeoutMillis));
    }

    public void safeNavigate(String url) {
        int attempts = 3;

        for (int i = 1; i <= attempts; i++) {
            try {
                page.navigate(url, new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                        .setTimeout(60_000));
                return;
            } catch (PlaywrightException e) {
                System.out.println("safeNavigate attempt " + i + " failed: " + e.getMessage());
                if (i == attempts) throw e;
            }
        }
    }
}