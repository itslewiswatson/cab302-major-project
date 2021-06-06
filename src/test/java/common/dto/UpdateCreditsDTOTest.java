package common.dto;

import org.junit.Assert;
import org.junit.Test;

public class UpdateCreditsDTOTest {
    @Test
    public void testGetUnit() {
        UpdateCreditsDTO dto = new UpdateCreditsDTO("UNIT", 10);
        Assert.assertEquals("UNIT", dto.getUnitId());
    }

    @Test
    public void testGetCredits() {
        UpdateCreditsDTO dto = new UpdateCreditsDTO("UNIT", 10);
        Assert.assertEquals(10, dto.getNewCredits());
    }

    @Test
    public void testBadCredits() {
        Assert.assertThrows(
                NumberFormatException.class,
                () -> new UpdateCreditsDTO("UNIT", Integer.parseInt("S"))
        );
    }
}
