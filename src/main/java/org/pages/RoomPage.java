package org.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.basePage.BasePage;
import org.data.RoomType;

import java.util.regex.Pattern;

public class RoomPage extends BasePage {

    public RoomPage(Page page) {
        super(page);
    }

    public void clickBookNow(RoomType roomType) {
        String dynamicCardText = "^" + roomType.getTypeName() + ".*" + roomType.getPricePerNight() + " per nightBook now$";
        page.locator("div.card")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(dynamicCardText)))
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Book now"))
                .click();
    }

    public boolean verifyRoomPrice(RoomType roomType) {
        String expectedPriceText = roomType.getPriceString() + " per night";
        return page.locator("div.card")
                .filter(new Locator.FilterOptions().setHasText(roomType.getTypeName()))
                .textContent()
                .contains(expectedPriceText);
    }
}
