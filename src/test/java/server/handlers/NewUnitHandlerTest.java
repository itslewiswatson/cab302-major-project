package server.handlers;

import common.domain.Unit;
import common.dto.NewUnitDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.MockDBStatements;

public class NewUnitHandlerTest {
    private NewUnitHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new NewUnitHandler(new MockDBStatements());
    }

    @Test
    public void testHandle() {
        Unit newUnit = handler.handle(new NewUnitDTO("CAB203", 100));
        Assert.assertNotNull(newUnit);
    }

    @Test
    public void testExistingUser() {
        Unit newUnit = handler.handle(new NewUnitDTO("Engineering", 100));
        Assert.assertNull(newUnit);
    }

    @Test
    public void testInvalidNumber() {
        Unit newUnit = handler.handle(new NewUnitDTO("NEW UNIT", -100));
        Assert.assertNull(newUnit);
    }

    @Test
    public void testNullNumber() {
        Unit newUnit = handler.handle(new NewUnitDTO("NEW UNIT", null));
        Assert.assertNull(newUnit);
    }
}
