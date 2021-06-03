package common.dto;

public class TestDTO extends DTO {
    private final String testId;

    public TestDTO(String testId) {
        this.testId = testId;
    }

    public String getTestId() {
        return testId;
    }
}
