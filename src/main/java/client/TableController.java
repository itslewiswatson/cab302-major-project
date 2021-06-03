package client;

public abstract class TableController extends Controller {
    public TableController(ClientController clientController) {
        super(clientController);
    }

    protected abstract void populateTable();
}
