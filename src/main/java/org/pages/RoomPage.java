package org.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.basePage.BasePage;
import org.data.RoomType;

import java.util.Objects;

public class RoomPage extends BasePage {

    public RoomPage(Page page) {
        super(page);
    }

    public void clickBookNow(RoomType roomType) {
        Locator card = page.locator("div.card")
                .filter(new Locator.FilterOptions().setHasText(roomType.getTypeName()));

        card.waitFor(new Locator.WaitForOptions().setTimeout(15000));

        card.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Book now"))
                .click(new Locator.ClickOptions().setTimeout(60000));
    }

    public boolean verifyRoomPrice(RoomType roomType) {
        String expectedPriceText = roomType.getPriceString() + " per night";
        String actualText = Objects.requireNonNull(page.locator("div.card")
                        .filter(new Locator.FilterOptions().setHasText(roomType.getTypeName()))
                        .first()
                        .textContent());

        return actualText.contains(expectedPriceText);
    }
}
