package server;

import common.dto.DTO;
import common.exceptions.RouteNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents a request handler.
 */
public class RequestHandler extends Thread {

    /**
     * The client socket.
     */
    private final Socket clientSocket;

    /**
     * The server socket input stream.
     */
    private final ObjectInputStream inputStream;

    /**
     * The server socket output stream.
     */
    private final ObjectOutputStream outputStream;

    /**
     * The request interpreter.
     */
    private final RequestInterpreter requestInterpreter;

    /**
     * Creates a RequestHandler object when a new thread is created.
     *
     * @param clientSocket       The client socket.
     * @param inputStream        The server socket input stream.
     * @param outputStream       The server socket output stream.
     * @param requestInterpreter The request interpreter.
     */
    public RequestHandler(Socket clientSocket, ObjectInputStream inputStream, ObjectOutputStream outputStream, RequestInterpreter requestInterpreter) {
        this.clientSocket = clientSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.requestInterpreter = requestInterpreter;
    }

    /**
     * The request handler's execution method.
     */
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                Object object = inputStream.readObject();

                System.out.println("\t\tRequest received.");
                handleRequest(object);
                System.out.println("\t\tResponse sent.");
            }
        } catch (ClassNotFoundException exception) {
            System.err.println("The expected Class file is missing. Rebuild the program.");
        } catch (IOException exception) {
            try {
                outputStream.close();
                inputStream.close();
                clientSocket.close();

                System.out.println("Connection closed.\n");
            } catch (IOException ignored) {
            }
        } catch (RouteNotFoundException e) {
            System.out.println("Route not found.\n");
        }
    }

    /**
     * Handles the request according to the object provided.
     *
     * @param object The object received by the server.
     * @throws IOException An error occurred when writing the object to the stream.
     */
    private void handleRequest(Object object) throws IOException, RouteNotFoundException {
        // Only accept DTO objects
        if (!(object instanceof DTO)) {
            throw new IOException();
        }

        // Interpret incoming request and write result to stream
        try {
            Object result = requestInterpreter.interpret((DTO) object);
            sendOutput(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RouteNotFoundException();
        }
    }

    private void sendOutput(Object object) throws IOException {
        outputStream.writeObject(object);
        outputStream.flush();
    }
}
