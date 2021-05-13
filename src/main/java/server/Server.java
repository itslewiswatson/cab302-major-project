package server;

import java.io.IOException;

/**
 * The system server.
 */
public class Server {
    /**
     * Main method.
     *
     * @param args Command line arguments.
     * @throws IOException Input read failed.
     */
    public static void main(String[] args) throws IOException {
        DBStatements dbStatements = new DBStatements();

        System.in.read();

        dbStatements.close();
    }
}