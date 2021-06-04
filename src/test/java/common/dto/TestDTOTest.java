package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class TestDTOTest {
    private final TestDTO dto;
    private final TestDTO nullableDTO;

    public TestDTOTest() {
        dto = new TestDTO("STRING");
        nullableDTO = new TestDTO(null);
    }

    @Test
    public void testNotNull() {
        Assert.assertNotNull(dto.getTestId());
    }

    @Test
    public void testGetter() {
        Assert.assertEquals("STRING", dto.getTestId());
    }

    @Test
    public void testNull() {
        Assert.assertNull(nullableDTO.getTestId());
    }
}
