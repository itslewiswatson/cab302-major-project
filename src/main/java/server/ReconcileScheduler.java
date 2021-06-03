package server;

import server.db.DBStatements;

import java.util.Timer;
import java.util.TimerTask;

public class ReconcileScheduler extends Thread {

    /**
     * The database controller.
     */
    private final DBStatements dbStatements;

    public ReconcileScheduler(DBStatements dbStatements) {
        this.dbStatements = dbStatements;
    }

    @Override
    public void run() {
        int periodSec = 5;

        Timer timer = new Timer();
        TimerTask reconcile = new Reconciler(dbStatements);

        timer.schedule(reconcile, periodSec*1000, periodSec*1000);
    }
}
