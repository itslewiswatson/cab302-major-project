package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class TestDTOTest {
    private final TestDTO dto;

    public TestDTOTest() {
        dto = new TestDTO("STRING");
    }

    @Test
    public void testNotNull() {
        Assert.assertNotNull(dto.getTestId());
    }

    @Test
    public void testGetter() {
        Assert.assertEquals("STRING", dto.getTestId());
    }
}
