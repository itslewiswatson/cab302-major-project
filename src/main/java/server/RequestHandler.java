package server;

import common.domain.*;
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
        if (!(object instanceof DTO)) {
            throw new IOException();
        }

        if (object instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) object;
            LoginHandler loginHandler = new LoginHandler(dbStatements);
            User user = loginHandler.handle(loginDTO);
            sendOutput(user);
            return;
        }

        if (object instanceof CreateAccountDTO) {
            CreateAccountDTO createAccountDTO = (CreateAccountDTO) object;
            CreateAccountHandler createAccountHandler = new CreateAccountHandler(dbStatements);
            User user = createAccountHandler.handle(createAccountDTO);
            sendOutput(user);
            return;
        }

        if (object instanceof NewTradeDTO) {
            NewTradeDTO newTradeDTO = (NewTradeDTO) object;
            NewTradeHandler newTradeHandler = new NewTradeHandler(dbStatements);
            Trade newTrade = newTradeHandler.handle(newTradeDTO);
            sendOutput(newTrade);
            return;
        }

        if (object instanceof UpdatePasswordDTO) {
            UpdatePasswordDTO updatePasswordDTO = (UpdatePasswordDTO) object;
            UpdatePasswordHandler updatePasswordHandler = new UpdatePasswordHandler(dbStatements);
            User user = updatePasswordHandler.handle(updatePasswordDTO);
            sendOutput(user);
            return;
        }

        if (object instanceof GetTradesDTO) {
            GetTradesDTO getTradesDTO = (GetTradesDTO) object;
            GetTradesHandler getTradesHandler = new GetTradesHandler(dbStatements);
            ArrayList<Trade> trades = getTradesHandler.handle(getTradesDTO);
            sendOutput(trades);
            return;
        }

        if (object instanceof GetUnitsDTO) {
            GetUnitsDTO getUnitsDTO = (GetUnitsDTO) object;
            GetUnitsHandler getUnitsHandler = new GetUnitsHandler(dbStatements);
            ArrayList<Unit> units = getUnitsHandler.handle(getUnitsDTO);
            sendOutput(units);
            return;
        }

        if (object instanceof GetAssetsDTO) {
            GetAssetsDTO getAssetsDTO = (GetAssetsDTO) object;
            GetAssetsHandler getAssetsHandler = new GetAssetsHandler(dbStatements);
            ArrayList<FullAsset> assets = getAssetsHandler.handle(getAssetsDTO);
            sendOutput(assets);
            return;
        }

        if (object instanceof AddAssetDTO) {
            AddAssetDTO addAssetDTO = (AddAssetDTO) object;
            AddAssetHandler addAssetHandler = new AddAssetHandler(dbStatements);
            ArrayList<FullAsset> assets = addAssetHandler.handle(addAssetDTO);
            sendOutput(assets);
            return;
        }

        if (object instanceof GetUnitTradesDTO) {
            GetUnitTradesDTO getUnitTradesDTO = (GetUnitTradesDTO) object;
            GetUnitTradesHandler getUnitTradesHandler = new GetUnitTradesHandler(dbStatements);
            ArrayList<Trade> trades = getUnitTradesHandler.handle(getUnitTradesDTO);
            sendOutput(trades);
            return;
        }

        if (object instanceof RemoveTradeDTO) {
            RemoveTradeDTO removeTradeDTO = (RemoveTradeDTO) object;
            RemoveTradeHandler removeTradeHandler = new RemoveTradeHandler(dbStatements);
            Boolean tradeRemoved = removeTradeHandler.handle(removeTradeDTO);
            sendOutput(tradeRemoved);
            return;
        }

        if (object instanceof UpdateCreditsDTO) {
            UpdateCreditsDTO updateCreditsDTO = (UpdateCreditsDTO) object;
            UpdateCreditsHandler updateCreditsHandler = new UpdateCreditsHandler(dbStatements);
            Unit unit = updateCreditsHandler.handle(updateCreditsDTO);
            sendOutput(unit);
            return;
        }

        if (object instanceof GetUnitAssetsDTO) {
            GetUnitAssetsDTO getUnitAssetsDTO = (GetUnitAssetsDTO) object;
            GetUnitAssetsHandler getUnitAssetsHandler = new GetUnitAssetsHandler(dbStatements);
            ArrayList<UnitAsset> unitAssets = getUnitAssetsHandler.handle(getUnitAssetsDTO);
            sendOutput(unitAssets);
            return;
        }

        if (object instanceof RemoveUnitAssetDTO) {
            RemoveUnitAssetDTO removeUnitAssetDTO = (RemoveUnitAssetDTO) object;
            RemoveUnitAssetHandler removeUnitAssetHandler = new RemoveUnitAssetHandler(dbStatements);
            Boolean unitAssetRemoved = removeUnitAssetHandler.handle(removeUnitAssetDTO);
            sendOutput(unitAssetRemoved);
            return;
        }

        if (object instanceof CreateOrUpdateUnitAssetDTO) {
            CreateOrUpdateUnitAssetDTO createOrUpdateUnitAssetDTO = (CreateOrUpdateUnitAssetDTO) object;
            CreateOrUpdateUnitAssetHandler createOrUpdateUnitAssetHandler = new CreateOrUpdateUnitAssetHandler(dbStatements);
            ArrayList<UnitAsset> unitAssets = createOrUpdateUnitAssetHandler.handle(createOrUpdateUnitAssetDTO);
            sendOutput(unitAssets);
            return;
        }

        if (object instanceof GetHistoricTradesDTO) {
            GetHistoricTradesDTO getHistoricTradesDTO = (GetHistoricTradesDTO) object;
            GetHistoricTradesHandler getHistoricTradesHandler = new GetHistoricTradesHandler(dbStatements);
            ArrayList<Trade> historicTrades = getHistoricTradesHandler.handle(getHistoricTradesDTO);
            sendOutput(historicTrades);
            return;
        }

        if (object instanceof GetUnitUsersDTO) {
            GetUnitUsersDTO getUnitUsersDTO = (GetUnitUsersDTO) object;
            GetUnitUsersHandler getUnitUsersHandler = new GetUnitUsersHandler(dbStatements);
            ArrayList<User> users = getUnitUsersHandler.handle(getUnitUsersDTO);
            sendOutput(users);
            return;
        }

        throw new IOException();
    }

    private void sendOutput(Object object) throws IOException {
        outputStream.writeObject(object);
        outputStream.flush();
    }
}
