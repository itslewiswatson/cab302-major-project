package common.dto;

import common.dataTypes.TradeType;

/**
 * This class represents the new trade DTO
 */
public class NewTradeDTO extends DTO {
    private final String assetId;
    private final String unitId;
    private final String userId;
    private final TradeType type;
    private final int quantity;
    private final int price;

    /**
     * Create new trade DTO
     *
     * @param assetId String of asset ID
     * @param unitId String of unit ID
     * @param userId String of user ID
     * @param type Buy or sell type
     * @param quantity Integer of quantity
     * @param price Integer of price
     */
    public NewTradeDTO(String assetId, String unitId, String userId, TradeType type, int quantity, int price) {
        this.assetId = assetId;
        this.unitId = unitId;
        this.userId = userId;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Get asset ID
     *
     * @return String of asset ID
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * Get unit ID
     *
     * @return String of unit ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * Get user ID
     *
     * @return String of user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get trade type
     *
     * @return Trade type enum
     */
    public TradeType getType() {
        return type;
    }

    /**
     * Get quantity
     *
     * @return Integer of quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get price
     *
     * @return Integer of price
     */
    public int getPrice() {
        return price;
    }
}
