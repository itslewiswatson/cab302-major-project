package server.handlers;

import common.domain.Asset;
import common.domain.UnitAsset;
import common.dto.CreateOrUpdateUnitAssetDTO;
import org.jetbrains.annotations.Nullable;
import server.DBStatements;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateOrUpdateUnitAssetHandler extends Handler<ArrayList<UnitAsset>, CreateOrUpdateUnitAssetDTO> {
    public CreateOrUpdateUnitAssetHandler(DBStatements dbStatements) {
        super(dbStatements);
    }

    @Override
    public ArrayList<UnitAsset> handle(CreateOrUpdateUnitAssetDTO dto) {

        try {
            createOrUpdateUnitAsset(dto);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }

        return dbStatements.findUnitAssetsByUnit(dto.getUnitId());
    }

    private void createOrUpdateUnitAsset(CreateOrUpdateUnitAssetDTO dto) throws SQLException {
        String unitId = dto.getUnitId();
        UnitAsset existingUnitAsset = resolveUnitAsset(unitId, dto.getAssetId());

        if (existingUnitAsset != null) {
            System.out.println("we have existing asset");
            updateUnitAsset(existingUnitAsset, dto);
            return;
        }

        createNewUnitAsset(dto);
    }

    private @Nullable UnitAsset resolveUnitAsset(String unitId, String assetId) {
        ArrayList<UnitAsset> unitAssets = dbStatements.findUnitAssetsByUnit(unitId);
        for (UnitAsset unitAsset : unitAssets) {
            if (unitAsset.getAsset().getAssetId().equals(assetId)) {
                return unitAsset;
            }
        }
        return null;
    }

    private void updateUnitAsset(UnitAsset existingUnitAsset, CreateOrUpdateUnitAssetDTO dto) throws SQLException {
        existingUnitAsset.setQuantity(dto.getQuantity());
        dbStatements.updateUnitAsset(existingUnitAsset);
    }

    private void createNewUnitAsset(CreateOrUpdateUnitAssetDTO dto) throws SQLException {
        Asset asset = resolveAsset(dto.getAssetId());

        UnitAsset unitAsset = new UnitAsset(
                dto.getUnitId(),
                asset,
                dto.getQuantity()
        );

        dbStatements.addUnitAsset(unitAsset);
    }

    private Asset resolveAsset(String assetId) throws SQLException {
        return dbStatements.findAssetById(assetId);
    }
}
