package server.handlers;

import common.domain.FullAsset;
import common.dto.GetAssetsDTO;
import server.db.DBStrategy;

import java.util.ArrayList;

public class GetAssetsHandler extends Handler<ArrayList<FullAsset>, GetAssetsDTO> {
    public GetAssetsHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<FullAsset> handle(GetAssetsDTO dto) {
        return dbStatements.findAssets();
    }
}
