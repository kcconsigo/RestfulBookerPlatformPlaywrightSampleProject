package org.api.clients;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.api.payloads.RoomPayload;

public class RoomClient {
    private final APIRequestContext request;
    // FIXED: Added /api to the base URL
    private final String baseUrl = "https://automationintesting.online/api";

    public RoomClient(APIRequestContext request) {
        this.request = request;
    }

    public APIResponse createRoom(String token, RoomPayload payload) {
        return request.post(baseUrl + "/room",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("cookie", "token=" + token)
                        .setData(payload)
        );
    }
}