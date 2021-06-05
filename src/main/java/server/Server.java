package server;

import client.Client;
import client.Main;
import server.db.DBStatements;
import server.handlers.strategy.HandlerResolver;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * This class is the server application.
 */
public class Server {

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        int portNumber = getPortNumber();

        if (portNumber == -1) return;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            DBStatements dbStatements = new DBStatements();

            System.out.println("Server started.\n");

            Thread reconcileThread = new ReconcileScheduler(dbStatements);
            reconcileThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection received.");

                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                RoutesMap routesMap = new RoutesMap();
                HandlerResolver handlerResolver = new HandlerResolver(routesMap, dbStatements);
                RequestInterpreter requestInterpreter = new RequestInterpreter(handlerResolver);

                Thread requestThread = new RequestHandler(clientSocket, inputStream, outputStream, requestInterpreter);
                requestThread.start();
                System.out.println("\tConnection sent to thread.");
            }
        } catch (BindException exception) {
            System.err.println("Port number " + portNumber + " is already in use. Try closing the process using it or changing the port number, then restart the server.");
        }
        catch (IOException exception) {
            System.err.println("An error occurred while starting or during execution of the server. Try restarting the server or rebuilding the program.");
        }
    }

    private static int getPortNumber() {
        Properties properties = new Properties();
        FileInputStream fileInputStream;

        try {
            URI jarFileURI = Server.class.getProtectionDomain().getCodeSource().getLocation().toURI();

            File jarFile = Paths.get(jarFileURI).toFile();
            File propertiesFile = new File(jarFile.getParentFile(), "server.properties");

            fileInputStream = new FileInputStream(propertiesFile);
            properties.load(fileInputStream);
            fileInputStream.close();

            String portNumberString = properties.getProperty("port");

            int portNumber = Integer.parseInt(portNumberString);

            if (portNumber < 1024 || portNumber > 65535) throw new Exception();

            return portNumber;

        } catch (FileNotFoundException exception) {
            System.err.println("server.properties is not where it is expected to be. Ensure it is in the same directory as server.jar.");
        } catch (IOException exception) {
            System.err.println("server.properties has become corrupted. Rebuild the program.");
        } catch (Exception exception) {
            System.err.println("Invalid port number. Please change the port property in server.properties to be a number between 1024 and 65535.");
        }

        return -1;
    }
}