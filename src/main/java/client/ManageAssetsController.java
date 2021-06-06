package client;

import client.alert.AlertDialog;
import client.config.Page;
import client.strategy.ClientController;
import client.strategy.Controller;
import common.domain.FullAsset;
import common.dto.AddAssetDTO;
import common.dto.DeleteAssetDTO;
import common.dto.GetAssetsDTO;
import common.exceptions.NullResultException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageAssetsController extends Controller implements Initializable {

    Timeline refresher;

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

    public ManageAssetsController(ClientController clientController) {
        super(clientController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int refreshPeriod = 5;

        setupColumns();
        populateTable();

        refresher = new Timeline(
                new KeyFrame(
                        Duration.seconds(refreshPeriod),
                        event -> populateTable()
                )
        );

        refresher.setCycleCount(Timeline.INDEFINITE);
        refresher.play();
    }

    public void populateTable() {
        int previouslySelectedAsset = tableView.getSelectionModel().getSelectedIndex();

        ObservableList<FullAsset> assets = FXCollections.observableArrayList();

        SortedList<FullAsset> sortedAssets = new SortedList<>(assets);

        sortedAssets.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedAssets);

        assets.addAll(fetchAssets());

        tableView.getFocusModel().focus(previouslySelectedAsset);
        tableView.getSelectionModel().select(previouslySelectedAsset);
    }

    public void addAsset() {
        if (newAssetName.getLength() == 0) {
            AlertDialog.warning("Empty asset name field.", "Please enter an asset name and try again.");
            return;
        }

        AddAssetDTO addAssetDTO = new AddAssetDTO(newAssetName.getText());
        sendObject(addAssetDTO);

        try {
            readObject();
            populateTable();
        } catch (NullResultException e) {
            AlertDialog.error("Could not add asset.", "Ensure an asset with the same name doesn't " +
                    "already exist and try again.");
        }
    }

    public void removeAsset() {
        @Nullable FullAsset asset = tableView.getSelectionModel().getSelectedItem();
        if (asset == null) {
            AlertDialog.warning("No asset selected.", "Please select an asset and try again.");
            return;
        }
        if (asset.getAmount() != 0) {
            AlertDialog.warning("Unable to remove asset.", "You can only remove assets that aren't in " +
                    "circulation.");
            return;
        }

        sendObject(new DeleteAssetDTO(asset.getAssetId()));
        try {
            readObject();
        } catch (NullResultException e) {
            AlertDialog.error("Could not remove asset.", "Please try again.");
        }
        populateTable();
    }

    public void goBack() {
        refresher.stop();
        switchToPage(Page.myAccount);
    }

    private ArrayList<FullAsset> fetchAssets() {
        sendObject(new GetAssetsDTO());
        try {
            return readObject();
        } catch (NullResultException e) {
            return new ArrayList<>();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setupColumns() {
        name.setCellValueFactory(c -> new ReadOnlyObjectWrapper(c.getValue().getAssetName()));
        dateAdded.setCellValueFactory(c -> new ReadOnlyObjectWrapper(c.getValue().getDateAdded().toString()));
        quantity.setCellValueFactory(c -> new ReadOnlyObjectWrapper(c.getValue().getAmount()));
    }
}
