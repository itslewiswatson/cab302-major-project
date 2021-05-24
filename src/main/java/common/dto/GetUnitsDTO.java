package common.dto;

public class GetUnitsDTO extends DTO {
    private final String userId;

    public GetUnitsDTO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
