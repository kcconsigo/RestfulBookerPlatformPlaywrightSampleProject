package org.data;

public enum RoomType {
    SINGLE("Single", 100),
    DOUBLE("Double", 150),
    SUITE("Suite", 225);

    private final String typeName;
    private final int pricePerNight;

    RoomType(String typeName, int pricePerNight) {
        this.typeName = typeName;
        this.pricePerNight = pricePerNight;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public String getPriceString() {
        return "£" + pricePerNight;
    }
}
