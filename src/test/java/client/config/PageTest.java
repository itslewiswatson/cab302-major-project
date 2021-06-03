package client.config;

import client.Controller;
import org.junit.Assert;
import org.junit.Test;

public class PageTest {
    @Test
    public void testEnumNames() {
        Assert.assertEquals("myAccount", Page.myAccount.name());
        Assert.assertEquals("login", Page.login.name());
        Assert.assertEquals("allTrades", Page.allTrades.name());
        Assert.assertEquals("unitTrades", Page.unitTrades.name());
        Assert.assertEquals("assets", Page.assets.name());
        Assert.assertEquals("manageUnits", Page.manageUnits.name());
        Assert.assertEquals("manageUsers", Page.manageUsers.name());
    }

    @Test
    public void testControllerComponent() {
        for (Page p : Page.values()) {
            Assert.assertSame(Controller.class, p.namespace.getSuperclass());
        }
    }

    @Test
    public void testPathFilenames() {
        for (Page p : Page.values()) {
            Assert.assertEquals(".fxml", p.path.substring(p.path.length() - 5));
        }
    }
}
