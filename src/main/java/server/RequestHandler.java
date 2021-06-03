package server;

import common.dto.DTO;
import common.exceptions.RouteNotFoundException;
import server.db.DBStatements;
import server.handlers.Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
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
     * The database controller.
     */
    private final DBStatements dbStatements;

    /**
     * The routes map.
     */
    private final RoutesMap routesMap;

    /**
     * Creates a RequestHandler object when a new thread is created.
     *
     * @param clientSocket The client socket.
     * @param inputStream  The server socket input stream.
     * @param outputStream The server socket output stream.
     * @param dbStatements The database controller.
     * @param routesMap    The routes map.
     */
    public RequestHandler(Socket clientSocket, ObjectInputStream inputStream, ObjectOutputStream outputStream, DBStatements dbStatements, RoutesMap routesMap) {
        this.clientSocket = clientSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.dbStatements = dbStatements;
        this.routesMap = routesMap;
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
            } catch (IOException ignored) {}
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
        if (!(object instanceof DTO)) {
            throw new IOException();
        }

        try {
            Class<Handler<Object, DTO>> handlerClass = getHandlerClass(object);
            Handler<Object, DTO> handler = createHandlerFromClass(handlerClass);
            Object result = handler.handle((DTO) object);
            sendOutput(result);
        } catch (Exception e) {
            throw new RouteNotFoundException();
        }
    }

    private Handler<Object, DTO> createHandlerFromClass(Class<Handler<Object, DTO>> handlerClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return handlerClass.getDeclaredConstructor(DBStatements.class).newInstance(dbStatements);
    }

    @SuppressWarnings("unchecked")
    private Class<Handler<Object, DTO>> getHandlerClass(Object object) {
        return (Class<Handler<Object, DTO>>) routesMap.getMap().get(object.getClass());
    }

    private void sendOutput(Object object) throws IOException {
        outputStream.writeObject(object);
        outputStream.flush();
    }
}
