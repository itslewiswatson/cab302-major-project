package client;

import client.alert.AlertDialog;
import client.config.Page;
import common.domain.FullAsset;
import common.dto.AddAssetDTO;
import common.dto.DeleteAssetDTO;
import common.dto.GetAssetsDTO;
import common.exceptions.NullResultException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AssetsController extends Controller implements Initializable {
    public AssetsController(ClientController clientController) {
        super(clientController);
    }

    @FXML
    private TableView<FullAsset> tableView;

    @FXML
    private TableColumn<FullAsset, String> name;

    @FXML
    private TableColumn<FullAsset, String> dateAdded;

    @FXML
    private TableColumn<FullAsset, String> quantity;

    @FXML
    private TextField newAssetName;

    private ArrayList<FullAsset> fetchAssets() {
        sendObject(new GetAssetsDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    public void goBack() {
        switchToPage(Page.myAccount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        populateTable();
    }

    private void populateTable() {
        tableView.setItems(FXCollections.observableList(fetchAssets()));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns() {
        name.setCellValueFactory(c -> new ReadOnlyObjectWrapper(c.getValue().getAssetName()));
        dateAdded.setCellValueFactory(c -> new ReadOnlyObjectWrapper(c.getValue().getDateAdded().toString()));
        quantity.setCellValueFactory(c -> new ReadOnlyObjectWrapper(c.getValue().getAmount()));
    }

    public void addAsset() {
        if (newAssetName.getLength() == 0) return;
        AddAssetDTO addAssetDTO = new AddAssetDTO(newAssetName.getText());
        sendObject(addAssetDTO);

        try {
            readObject();
        } catch (NullResultException e) {
            AlertDialog.warning("Could not add asset", "Please try again");
        }
        populateTable();
    }

    public void removeAsset() {
        @Nullable FullAsset asset = tableView.getSelectionModel().getSelectedItem();
        if (asset == null) return;
        if (asset.getAmount() != 0) {
            AlertDialog.info("Unable to remove asset", "You can only remove assets not in circulation");
            return;
        }

        sendObject(new DeleteAssetDTO(asset.getAssetId()));
        try {
            readObject();
        } catch (NullResultException e) {
            AlertDialog.warning("Could not remove asset", "Please try again");
        }
        populateTable();
    }
}
