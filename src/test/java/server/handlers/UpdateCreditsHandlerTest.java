package server.handlers;

import common.domain.Unit;
import common.dto.UpdateCreditsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class UpdateCreditsHandlerTest {
    private UpdateCreditsHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new UpdateCreditsHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        Unit unit = handler.handle(new UpdateCreditsDTO("ID11", 100));

        Assert.assertNotNull(unit);
        Assert.assertEquals(100, unit.getCredits());
    }

    @Test
    public void testBadUnit() {
        Unit unit = handler.handle(new UpdateCreditsDTO("INVALID", 100));
        Assert.assertNull(unit);
    }

    @Test
    public void testNullUnit() {
        Unit unit = handler.handle(new UpdateCreditsDTO(null, 100));
        Assert.assertNull(unit);
    }
}
