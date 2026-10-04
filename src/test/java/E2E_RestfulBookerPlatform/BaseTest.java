package E2E_RestfulBookerPlatform;

import com.microsoft.playwright.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected Dotenv dotenv;

    @BeforeClass
    public void launchBrowser() {

        dotenv = Dotenv.load();
        // Start the single underlying driver server instance
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void setUp() {
        // Create an isolated context (like an incognito session) for each test case
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        if (context != null) context.close();
    }

    @org.testng.annotations.AfterClass
    public void closeBrowser() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
