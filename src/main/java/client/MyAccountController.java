package client;

import javafx.event.ActionEvent;

/**
 * This class is the My Account GUI controller.
 */
public class MyAccountController extends Controller {

    /**
     * Processes a user's logout button click
     *
     * @param event Event action.
     */
    public void logout(ActionEvent event) {
        switchToPage(event, Page.login);
    }
}
