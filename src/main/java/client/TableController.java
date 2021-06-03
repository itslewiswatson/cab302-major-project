package client;

import client.strategy.ClientController;
import client.strategy.Controller;

public abstract class TableController extends Controller {
    public TableController(ClientController clientController) {
        super(clientController);
    }

    protected abstract void populateTable();
}
