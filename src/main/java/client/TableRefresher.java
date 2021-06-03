package client;

import java.util.TimerTask;

public class TableRefresher<T extends TableController> extends TimerTask {

    private final T tradesController;

    public TableRefresher(T tradesController) {
        this.tradesController = tradesController;
    }

    @Override
    public void run() {
        tradesController.populateTable();
    }
}
