package server;

import common.dto.*;
import server.handlers.*;

import java.util.HashMap;

public class RoutesMap implements RoutesMapStrategy {
    private final HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> routesMap = new HashMap<>();

    public RoutesMap() {
        routesMap.put(AddAssetDTO.class, AddAssetHandler.class);
        routesMap.put(CreateOrUpdateUnitAssetDTO.class, CreateOrUpdateUnitAssetHandler.class);
        routesMap.put(GetAssetsDTO.class, GetAssetsHandler.class);
        routesMap.put(GetHistoricTradesDTO.class, GetHistoricTradesHandler.class);
        routesMap.put(GetTradesDTO.class, GetTradesHandler.class);
        routesMap.put(GetUnitAssetsDTO.class, GetUnitAssetsHandler.class);
        routesMap.put(GetUnitsDTO.class, GetUnitsHandler.class);
        routesMap.put(GetUnitTradesDTO.class, GetUnitTradesHandler.class);
        routesMap.put(GetUnitUsersDTO.class, GetUnitUsersHandler.class);
        routesMap.put(LoginDTO.class, LoginHandler.class);
        routesMap.put(NewTradeDTO.class, NewTradeHandler.class);
        routesMap.put(RemoveTradeDTO.class, RemoveTradeHandler.class);
        routesMap.put(RemoveUnitAssetDTO.class, RemoveUnitAssetHandler.class);
        routesMap.put(UpdateCreditsDTO.class, UpdateCreditsHandler.class);
        routesMap.put(UpdatePasswordDTO.class, UpdatePasswordHandler.class);
        routesMap.put(GetUsersDTO.class, GetUsersHandler.class);
        routesMap.put(UpdateUserPermissionsDTO.class, UpdateUserPermissionsHandler.class);
        routesMap.put(DeleteAssetDTO.class, DeleteAssetHandler.class);
        routesMap.put(DeleteUserDTO.class, DeleteUserHandler.class);
        routesMap.put(AddUserToUnitDTO.class, AddUserToUnitHandler.class);
        routesMap.put(RemoveUserFromUnitDTO.class, RemoveUserFromUnitHandler.class);
        routesMap.put(NewUserDTO.class, NewUserHandler.class);
        routesMap.put(NewUnitDTO.class, NewUnitHandler.class);
    }

    /**
     * Public accessor for the internally-stored routes hash map
     *
     * @return The class-stored routes hash map
     */
    @Override
    public HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> getMap() {
        return routesMap;
    }
}
