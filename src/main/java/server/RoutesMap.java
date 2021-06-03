package server;

import common.dto.*;
import server.handlers.*;

import java.util.HashMap;

public class RoutesMap {
    private final HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> routesMap = new HashMap<>();

    public RoutesMap() {
        routesMap.put(AddAssetDTO.class, AddAssetHandler.class);
        routesMap.put(CreateAccountDTO.class, CreateAccountHandler.class);
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
    }

    public HashMap<Class<? extends DTO>, Class<? extends Handler<?, ?>>> getMap() {
        return routesMap;
    }
}
