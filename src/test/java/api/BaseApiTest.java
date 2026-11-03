package api;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {
    protected Playwright playwright;
    protected APIRequestContext request;

    @BeforeClass
    public void setupAPIClient() {
        playwright = Playwright.create();

        // Configures a shared network session context for all API tests
        request = playwright.request().newContext();
    }

    @AfterClass
    public void tearDownAPIClient() {
        // Disposes the network context and terminates Playwright background processes
        if (request != null) {
            request.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}