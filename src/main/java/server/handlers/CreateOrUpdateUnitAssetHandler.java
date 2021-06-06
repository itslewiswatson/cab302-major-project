package server.handlers;

import common.domain.FullAsset;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.dto.CreateOrUpdateUnitAssetDTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

import java.util.ArrayList;

public class CreateOrUpdateUnitAssetHandler extends Handler<ArrayList<UnitAsset>, CreateOrUpdateUnitAssetDTO> {
    public CreateOrUpdateUnitAssetHandler(DBStrategy dbStatements) {
        super(dbStatements);
    }

    /**
     * Update or create a unit asset
     *
     * @param dto Information from client request
     * @return List of all unit assets
     */
    @Override
    public ArrayList<UnitAsset> handle(CreateOrUpdateUnitAssetDTO dto) {

        try {
            createOrUpdateUnitAsset(dto);
        } catch (NullResultException e) {
            return null;
        }

        return dbStatements.findUnitAssetsByUnit(dto.getUnitId());
    }

    private void createOrUpdateUnitAsset(CreateOrUpdateUnitAssetDTO dto) throws NullResultException {
        Unit unit = resolveUnit(dto.getUnitId());
        FullAsset asset = resolveAsset(dto.getAssetId());
        UnitAsset existingUnitAsset = findUnitAsset(unit.getUnitId(), asset.getAssetId());

        if (existingUnitAsset != null) {
            updateUnitAsset(existingUnitAsset, dto);
            return;
        }

        createNewUnitAsset(dto);
    }

    private @Nullable UnitAsset findUnitAsset(String unitId, String assetId) {
        ArrayList<UnitAsset> unitAssets = dbStatements.findUnitAssetsByUnit(unitId);
        for (UnitAsset unitAsset : unitAssets) {
            if (unitAsset.getAsset().getAssetId().equals(assetId) && unitAsset.getUnitId().equals(unitId)) {
                return unitAsset;
            }
        }
        return null;
    }

    private void updateUnitAsset(UnitAsset existingUnitAsset, CreateOrUpdateUnitAssetDTO dto) {
        existingUnitAsset.setQuantity(dto.getQuantity());
        dbStatements.updateUnitAsset(existingUnitAsset);
    }

    private void createNewUnitAsset(CreateOrUpdateUnitAssetDTO dto) throws NullResultException {
        FullAsset asset = resolveAsset(dto.getAssetId());

        UnitAsset unitAsset = new UnitAsset(
                dto.getUnitId(),
                asset,
                dto.getQuantity()
        );

        dbStatements.addUnitAsset(unitAsset);
    }
}
