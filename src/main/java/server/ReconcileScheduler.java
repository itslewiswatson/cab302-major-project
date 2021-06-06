package server;

import server.db.DBStatements;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the scheduler of the reconciler
 */
public class ReconcileScheduler extends Thread {

    /**
     * The database controller.
     */
    private final DBStatements dbStatements;

    /**
     * Creates an instance of the reconcile scheduler
     *
     * @param dbStatements DB strategy to use
     */
    public ReconcileScheduler(DBStatements dbStatements) {
        this.dbStatements = dbStatements;
    }

    /**
     * Runs the reconciler on a timer
     */
    @Override
    public void run() {
        int periodSec = 5;

        Timer timer = new Timer();
        TimerTask reconcile = new Reconciler(dbStatements);

        timer.schedule(reconcile, periodSec*1000, periodSec*1000);
    }
}
