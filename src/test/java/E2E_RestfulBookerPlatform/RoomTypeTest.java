package E2E_RestfulBookerPlatform;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.pages.RoomPage;
import org.data.RoomType;

public class RoomTypeTest extends BaseTest {

    @DataProvider(name = "roomTypesProvider")
    public Object[][] getRoomTypes() {
        return new Object[][] {
                { RoomType.SINGLE },
                { RoomType.DOUBLE },
                { RoomType.SUITE }
        };
    }

    @Test(dataProvider = "roomTypesProvider")
    public void testSelectAndVerifyAllRoomTypes(RoomType testRoom) {
        // Pulls URL dynamically from your .env file
        String appUrl = dotenv.get("APP_URL");
        page.navigate(appUrl);

        RoomPage roomPage = new RoomPage(page);

        boolean isPriceCorrect = roomPage.verifyRoomPrice(testRoom);
        Assert.assertTrue(isPriceCorrect, "The pricing layout on the UI failed validation for: " + testRoom.getTypeName());

        roomPage.clickBookNow(testRoom);
    }
}