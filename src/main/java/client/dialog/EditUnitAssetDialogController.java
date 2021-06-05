package client.dialog;

import client.alert.AlertDialog;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.dto.CreateOrUpdateUnitAssetDTO;

public class EditUnitAssetDialogController extends Controller {
    private final CreateOrUpdateUnitAssetDTO dto;

    public EditUnitAssetDialogController(ClientController clientController, CreateOrUpdateUnitAssetDTO dto) {
        super(clientController);
        this.dto = dto;
    }

    public void clickCreate() {
        if (dto.getQuantity() == 0) {
            AlertDialog.error("Quantity cannot be 0");
        }
    }
}
