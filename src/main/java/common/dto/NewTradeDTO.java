package common.dto;

import common.dataTypes.TradeType;

public class NewTradeDTO extends DTO {
    private final String assetId;
    private final String unitId;
    private final String userId;
    private final TradeType type;
    private final int quantity;
    private final int price;

    public NewTradeDTO(String assetId, String unitId, String userId, TradeType type, int quantity, int price) {
        this.assetId = assetId;
        this.unitId = unitId;
        this.userId = userId;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getUserId() {
        return userId;
    }

    public TradeType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
