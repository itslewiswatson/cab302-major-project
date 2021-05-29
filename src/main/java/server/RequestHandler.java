package server;

import common.domain.FullAsset;
import common.domain.Trade;
import common.domain.Unit;
import common.domain.User;
import common.dto.*;
import server.handlers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
     * Creates a RequestHandler object when a new thread is created.
     *
     * @param clientSocket The client socket.
     * @param inputStream  The server socket input stream.
     * @param outputStream The server socket output stream.
     * @param dbStatements The database controller.
     */
    public RequestHandler(Socket clientSocket, ObjectInputStream inputStream, ObjectOutputStream outputStream, DBStatements dbStatements) {
        this.clientSocket = clientSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.dbStatements = dbStatements;
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
        }
    }

    /**
     * Handles the request according to the object provided.
     *
     * @param object The object received by the server.
     * @throws IOException An error occurred when writing the object to the stream.
     */
    private void handleRequest(Object object) throws IOException {
        if (object instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) object;
            LoginHandler loginHandler = new LoginHandler(dbStatements);
            User user = loginHandler.handle(loginDTO);
            outputStream.writeObject(user);
            outputStream.flush();
            return;
        }

        if (object instanceof CreateAccountDTO) {
            CreateAccountDTO createAccountDTO = (CreateAccountDTO) object;
            CreateAccountHandler createAccountHandler = new CreateAccountHandler(dbStatements);
            User user = createAccountHandler.handle(createAccountDTO);
            outputStream.writeObject(user);
            outputStream.flush();
            return;
        }

        if (object instanceof NewTradeDTO) {
            NewTradeDTO newTradeDTO = (NewTradeDTO) object;
            NewTradeHandler newTradeHandler = new NewTradeHandler(dbStatements);
            Trade newTrade = newTradeHandler.handle(newTradeDTO);
            outputStream.writeObject(newTrade);
            outputStream.flush();
            return;
        }

        if (object instanceof UpdatePasswordDTO) {
            UpdatePasswordDTO updatePasswordDTO = (UpdatePasswordDTO) object;
            UpdatePasswordHandler updatePasswordHandler = new UpdatePasswordHandler(dbStatements);
            User user = updatePasswordHandler.handle(updatePasswordDTO);
            outputStream.writeObject(user);
            outputStream.flush();
            return;
        }

        if (object instanceof GetTradesDTO) {
            GetTradesDTO getTradesDTO = (GetTradesDTO) object;
            GetTradesHandler getTradesHandler = new GetTradesHandler(dbStatements);
            ArrayList<Trade> trades = getTradesHandler.handle(getTradesDTO);
            outputStream.writeObject(trades);
            outputStream.flush();
            return;
        }

        if (object instanceof GetUnitsDTO) {
            GetUnitsDTO getUnitsDTO = (GetUnitsDTO) object;
            GetUnitsHandler getUnitsHandler = new GetUnitsHandler(dbStatements);
            ArrayList<Unit> units = getUnitsHandler.handle(getUnitsDTO);
            outputStream.writeObject(units);
            outputStream.flush();
            return;
        }

        if (object instanceof GetAssetsDTO) {
            GetAssetsDTO getAssetsDTO = (GetAssetsDTO) object;
            GetAssetsHandler getAssetsHandler = new GetAssetsHandler(dbStatements);
            ArrayList<FullAsset> assets = getAssetsHandler.handle(getAssetsDTO);
            outputStream.writeObject(assets);
            outputStream.flush();
            return;
        }

        if (object instanceof AddAssetDTO) {
            AddAssetDTO addAssetDTO = (AddAssetDTO) object;
            AddAssetHandler addAssetHandler = new AddAssetHandler(dbStatements);
            ArrayList<FullAsset> assets = addAssetHandler.handle(addAssetDTO);
            outputStream.writeObject(assets);
            outputStream.flush();
            return;
        }

        if (object instanceof GetUnitTradesDTO) {
            GetUnitTradesDTO getUnitTradesDTO = (GetUnitTradesDTO) object;
            GetUnitTradesHandler getUnitTradesHandler = new GetUnitTradesHandler(dbStatements);
            ArrayList<Trade> trades = getUnitTradesHandler.handle(getUnitTradesDTO);
            outputStream.writeObject(trades);
            outputStream.flush();
            return;
        }

        if (object instanceof RemoveTradeDTO) {
            RemoveTradeDTO removeTradeDTO = (RemoveTradeDTO) object;
            RemoveTradeHandler removeTradeHandler = new RemoveTradeHandler(dbStatements);
            Boolean tradeRemoved = removeTradeHandler.handle(removeTradeDTO);
            outputStream.writeObject(tradeRemoved);
            outputStream.flush();
            return;
        }

        throw new IOException();
    }
}
