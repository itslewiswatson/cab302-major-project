package server.handlers;

import common.domain.FullAsset;
import common.dto.GetAssetsDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

/**
 * This class represents the get assets logic
 */
public class GetAssetsHandler extends Handler<ArrayList<FullAsset>, GetAssetsDTO> {
    public GetAssetsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * @param dto Information from client request
     * @return List of all assets
     */
    @Override
    public ArrayList<FullAsset> handle(GetAssetsDTO dto) {
        return dbStatements.findAssets();
    }
}
