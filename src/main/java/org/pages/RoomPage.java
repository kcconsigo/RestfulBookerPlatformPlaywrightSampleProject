package org.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.data.RoomType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class RoomPage {
    private final Page page;

    public RoomPage(Page page) {
        this.page = page;
    }

    public void clickBookNow(RoomType type) {
        Locator roomContainer = page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(type.getTypeName(), Pattern.CASE_INSENSITIVE)))
                .filter(new Locator.FilterOptions().setHasText("Book now"));

        Locator targetCard = roomContainer.last();

        targetCard.getByRole(com.microsoft.playwright.options.AriaRole.LINK, new Locator.GetByRoleOptions().setName("Book now"))
                .or(targetCard.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Book now")))
                .first()
                .click();
    }

    public boolean verifyRoomPrice(RoomType type) {
        String typeName = type.getTypeName();
        String expectedPrice = type.getPriceString();

        try {
            page.waitForLoadState(LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(15_000));
        } catch (PlaywrightException ignored) {
            // If NETWORKIDLE didn't occur quickly, continue — we'll rely on specific selectors & waits below.
        }

        Locator roomContainer = page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(typeName, Pattern.CASE_INSENSITIVE)))
                .filter(new Locator.FilterOptions().setHasText("Book now"));

        long count = safeCount(roomContainer);
        if (count == 0) {
            Locator fallback = page.locator("article, .room, .card, .listing")
                    .filter(new Locator.FilterOptions().setHasText(Pattern.compile(typeName, Pattern.CASE_INSENSITIVE)))
                    .filter(new Locator.FilterOptions().setHasText("Book now"));

            count = safeCount(fallback);
            if (count > 0) {
                roomContainer = fallback;
            }
        }

        if (count == 0) {
            Locator textMatch = page.locator("text=" + typeName);
            count = safeCount(textMatch);
            if (count > 0) {
                // try to find a nearby ancestor with "Book now"
                Locator nearby = textMatch.first().locator("xpath=ancestor::div[contains(., 'Book now')]");
                if (safeCount(nearby) > 0) roomContainer = nearby;
            }
        }

        if (safeCount(roomContainer) == 0) {
            System.out.println("verifyRoomPrice: page URL = " + page.url());

            // Print a short snippet of page content to logs (be careful with huge pages)
            try {
                String content = page.content();
                System.out.println("verifyRoomPrice: page.content() snippet (first 2000 chars):\n" +
                        (content.length() > 2000 ? content.substring(0, 2000) : content));
            } catch (PlaywrightException e) {
                System.out.println("verifyRoomPrice: failed to read page.content(): " + e.getMessage());
            }

            // Save screenshot to target/screenshots for easier inspection
            try {
                Path screenshotsDir = Paths.get("target", "screenshots");
                Files.createDirectories(screenshotsDir);
                Path screenshotPath = screenshotsDir.resolve("roompage-" + sanitizeFilename(typeName) + ".png");
                page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
                System.out.println("verifyRoomPrice: screenshot saved to: " + screenshotPath.toAbsolutePath());
            } catch (IOException | PlaywrightException e) {
                System.out.println("verifyRoomPrice: failed to save screenshot: " + e.getMessage());
            }

            return false;
        }

        // 6) We have a candidate container - wait explicitly for it to be visible (extended timeout)
        Locator target = roomContainer.last();
        try {
            target.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(60_000));
        } catch (PlaywrightException e) {
            System.out.println("verifyRoomPrice: timeout waiting for room container to be visible for type: " + typeName);
            try {
                long found = safeCount(roomContainer);
                System.out.println("verifyRoomPrice: matching container count = " + found);
                if (found > 0) {
                    try {
                        System.out.println("verifyRoomPrice: last container innerText:\n" + roomContainer.last().innerText());
                    } catch (PlaywrightException ex) {
                        System.out.println("verifyRoomPrice: could not read innerText: " + ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                System.out.println("verifyRoomPrice: diagnostic collection failed: " + ex.getMessage());
            }
            return false;
        }

        // 7) Check the price text inside the container
        try {
            String containerText = target.innerText();
            boolean contains = containerText.contains(expectedPrice);
            if (!contains) {
                System.out.println(containerText);
            }
            return contains;
        } catch (PlaywrightException e) {
            System.out.println("verifyRoomPrice: failed to read innerText: " + e.getMessage());
            return false;
        }
    }

    private long safeCount(Locator locator) {
        try {
            return locator.count();
        } catch (PlaywrightException e) {
            return 0;
        }
    }

    private String sanitizeFilename(String s) {
        return s.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}