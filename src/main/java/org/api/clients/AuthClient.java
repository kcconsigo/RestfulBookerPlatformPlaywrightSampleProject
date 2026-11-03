package org.api.clients;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.api.payloads.LoginPayload;

public class AuthClient {
    private final APIRequestContext request;
    // FIXED: Added /api to the base URL
    private final String baseUrl = "https://automationintesting.online/api";

    public AuthClient(APIRequestContext request) {
        this.request = request;
    }

    public APIResponse login(String username, String password) {
        LoginPayload payload = new LoginPayload(username, password);
        // Clean routing paths without duplicate slashes
        return request.post(baseUrl + "/auth/login",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(payload)
        );
    }
}