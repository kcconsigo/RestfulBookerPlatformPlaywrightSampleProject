package org.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;
import org.data.RoomType;
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

        targetCard.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Book now"))
                .or(targetCard.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Book now")))
                .first()
                .click();
    }

    public boolean verifyRoomPrice(RoomType type) {
        Locator roomContainer = page.locator("div")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(type.getTypeName(), Pattern.CASE_INSENSITIVE)))
                .filter(new Locator.FilterOptions().setHasText("Book now"));

        // Wait briefly for the UI elements to render on screen
        roomContainer.last().waitFor();

        // Validate that this isolated module contains your expected price currency label (e.g., "£150")
        String containerText = roomContainer.last().innerText();
        return containerText.contains(type.getPriceString());
    }
}