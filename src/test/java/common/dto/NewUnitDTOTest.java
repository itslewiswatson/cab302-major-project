package common.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewUnitDTOTest {
    private NewUnitDTO dto;

    @Before
    public void setUp() throws Exception {
        dto = new NewUnitDTO("NAME", 100);
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("NAME", dto.getUnitName());
    }

    @Test
    public void testGetCredits() {
        Assert.assertSame(100, dto.getCredits());
    }
}
