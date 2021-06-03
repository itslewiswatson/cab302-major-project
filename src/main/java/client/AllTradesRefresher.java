package client;

import java.util.TimerTask;

public class AllTradesRefresher extends TimerTask {

    private final AllTradesController allTradesController;

    public AllTradesRefresher(AllTradesController allTradesController) {
        this.allTradesController = allTradesController;
    }

    @Override
    public void run() {
        allTradesController.populateTable();
    }
}
