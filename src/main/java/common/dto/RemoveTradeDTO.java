package common.dto;

public class RemoveTradeDTO extends DTO {
    private final String tradeId;

    public RemoveTradeDTO(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeId() {
        return tradeId;
    }
}
