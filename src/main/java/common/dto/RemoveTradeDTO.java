package common.dto;

/**
 * This class represents the remove trade DTO
 */
public class RemoveTradeDTO extends DTO {
    private final String tradeId;

    /**
     * Create remove trade DTO
     *
     * @param tradeId String of trade ID
     */
    public RemoveTradeDTO(String tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * Get trade ID
     *
     * @return String of trade ID
     */
    public String getTradeId() {
        return tradeId;
    }
}
