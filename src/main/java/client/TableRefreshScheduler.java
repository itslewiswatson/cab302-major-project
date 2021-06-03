package client;

import java.util.Timer;
import java.util.TimerTask;

public class TableRefreshScheduler<T extends TableController> extends Thread {

    private final T tradesController;

    public TableRefreshScheduler(T tradesController) {
        this.tradesController = tradesController;
    }

    @Override
    public void run() {
        int periodSec = 5;

        Timer timer = new Timer();
        TimerTask reconcile = new TableRefresher<>(tradesController);

        timer.schedule(reconcile, periodSec*1000, periodSec*1000);
    }
}
