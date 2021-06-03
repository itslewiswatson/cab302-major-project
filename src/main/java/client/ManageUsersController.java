package client;

import client.config.Page;

public class ManageUsersController extends Controller {
    public ManageUsersController(ClientController clientController) {
        super(clientController);
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }
}
