package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the server application.
 */
class Server {

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
                RoutesMap routesMap = new RoutesMap();

                Thread thread = new RequestHandler(clientSocket, inputStream, outputStream, dbStatements, routesMap);

                System.out.println("\tConnection sent to thread.");
                thread.start();
            }
        } catch (BindException exception) {
            System.err.println("Port number " + portNumber + " is already in use. Try closing the process using it or change the port number, and restart the server.");
        }
        catch (IOException exception) {
            System.err.println("An error occurred while starting or during execution of the server. Try restarting the server or rebuilding the program.");
        }
    }
}