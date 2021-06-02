package server;

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
        Timer timer = new Timer();

        TimerTask task = new Reconciler(dbStatements);

        timer.schedule(task, 10000, 10000);
    }
}
