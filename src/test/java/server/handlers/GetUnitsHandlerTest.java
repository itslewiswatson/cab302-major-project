package server.handlers;

import common.domain.Unit;
import common.dto.GetUnitsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBStrategy;
import server.db.MockDBStatements;

import java.util.ArrayList;

public class GetUnitsHandlerTest {
    private GetUnitsHandler handler;
    private DBStrategy dbStrategy;

    @Before
    public void setUp() throws Exception {
        dbStrategy = new MockDBStatements();
        handler = new GetUnitsHandler(dbStrategy);
    }

    @Test
    public void testHandle() {
        ArrayList<Unit> units = handler.handle(new GetUnitsDTO(null));
        Assert.assertNotNull(units);
        Assert.assertEquals(units, dbStrategy.findUnits());
    }

    @Test
    public void testNullUser() {
        ArrayList<Unit> units = handler.handle(new GetUnitsDTO("INVALID"));
        Assert.assertNotNull(units);
        Assert.assertEquals(0, units.size());
    }

    @Test
    public void testGoodUser() {
        ArrayList<Unit> units = handler.handle(new GetUnitsDTO("ID1"));
        Assert.assertNotNull(units);
        Assert.assertEquals(3, units.size());
    }
}
