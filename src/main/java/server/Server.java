package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the server application.
 */
public class Server  {

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        int portNumber = 1234;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            DBStatements dbStatements = new DBStatements();

            System.out.println("Server started.\n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection received.");

                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                Thread thread = new RequestHandler(clientSocket, inputStream, outputStream, dbStatements);

                System.out.println("\tConnection sent to thread.");
                thread.start();
            }
        } catch (IOException exception) {
            System.err.println("An error occurred while starting or during execution of the server. Try restarting the server or rebuilding the program.");
        }
    }
}