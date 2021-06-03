package client;

import client.strategy.ClientController;
import client.strategy.Controller;
import javafx.fxml.Initializable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("all")
public class TestControllerTest {
    private TestController testController;
    private ClientController clientController;

    @Before
    public void setUp() {
        testController = new TestController(clientController);
    }

    @Test
    public void testClass() {
        Assert.assertSame(testController.getClass().getSuperclass(), Controller.class);
    }

    @Test
    public void testInitializeClass() {
        Assert.assertTrue(testController instanceof Initializable);
    }

    @Test
    public void testInitializeData() throws MalformedURLException {
        URL url = new URL("http://null.oracle.com/");
        testController.initialize(url, null);

        Assert.assertSame(url, testController.getUrl());
    }
}
