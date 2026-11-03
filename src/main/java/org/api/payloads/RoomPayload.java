package org.api.payloads;

import java.util.List;

public class RoomPayload {
    private String roomName;
    private String type;
    private boolean accessible;
    private String description;
    private String image;
    private String roomPrice;
    private List<String> features;

    // Constructor to easily create a room body in your tests
    public RoomPayload(String roomName, String type, boolean accessible, String description, String image, String roomPrice, List<String> features) {
        this.roomName = roomName;
        this.type = type;
        this.accessible = accessible;
        this.description = description;
        this.image = image;
        this.roomPrice = roomPrice;
        this.features = features;
    }

    // Getters and Setters
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isAccessible() { return accessible; }
    public void setAccessible(boolean accessible) { this.accessible = accessible; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getRoomPrice() { return roomPrice; }
    public void setRoomPrice(String roomPrice) { this.roomPrice = roomPrice; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }
}
