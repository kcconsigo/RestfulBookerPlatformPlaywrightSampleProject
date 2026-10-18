package org.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.data.RoomType;

import java.util.regex.Pattern;

public class RoomPage {
    private final Page page;

    public RoomPage(Page page) {
        this.page = page;
    }

    public void clickBookNow(RoomType type) {
        Locator roomCard = page.locator("article, .room, .card, div")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile(type.getTypeName(), Pattern.CASE_INSENSITIVE)))
                .filter(new Locator.FilterOptions().setHasText("Book now"))
                .first();

        roomCard.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30_000));

        Locator bookNowButton = roomCard.getByRole(com.microsoft.playwright.options.AriaRole.LINK,
                        new Locator.GetByRoleOptions().setName("Book now"))
                .or(roomCard.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("Book now")))
                .first();

        bookNowButton.click();
    }

    public boolean verifyRoomPrice(RoomType type) {
        String typeName = type.getTypeName();
        String expectedPrice = type.getPriceString(); // e.g. "£150"

        Locator roomCard = page.locator("article, .room, .card, div")
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile(typeName, Pattern.CASE_INSENSITIVE)))
                .filter(new Locator.FilterOptions().setHasText("Book now"))
                .first();

        try {
            roomCard.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30_000));
        } catch (PlaywrightException e) {
            System.out.println("Room card not found for: " + typeName + " on URL: " + page.url());
            return false;
        }

        String actualText = roomCard.innerText();
        System.out.println("Expected price: " + expectedPrice + " | Actual text: " + actualText);

        return actualText.contains(expectedPrice);
    }
}