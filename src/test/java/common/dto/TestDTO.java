package common.dto;

import org.jetbrains.annotations.Nullable;

public class TestDTO extends DTO {
    private final @Nullable String testId;

    public TestDTO(@Nullable String testId) {
        this.testId = testId;
    }

    public @Nullable String getTestId() {
        return testId;
    }
}
