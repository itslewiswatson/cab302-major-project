package client;

import java.util.Timer;
import java.util.TimerTask;

public class AllTradesRefreshScheduler extends Thread {

    private AllTradesController allTradesController;

    public AllTradesRefreshScheduler(AllTradesController allTradesController) {
        this.allTradesController = allTradesController;
    }

    @Override
    public void run() {
        int periodSec = 5;

        Timer timer = new Timer();
        TimerTask reconcile = new AllTradesRefresher(allTradesController);

        timer.schedule(reconcile, periodSec*1000, periodSec*1000);
    }
}
