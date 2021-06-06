package server.handlers;

import common.domain.FullAsset;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.domain.User;
import common.dto.DTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

abstract public class Handler<T, U extends DTO> {
    final DBStrategy dbStatements;

    public Handler(DBStrategy dbStatements) {
        this.dbStatements = dbStatements;
    }

    abstract public T handle(U dto);

    /**
     * Helper function to assist in DTO unit value resolution.
     *
     * @param unitId Unit ID to resolve.
     * @return Unit corresponding to the given ID.
     */
    Unit resolveUnit(String unitId) throws NullResultException {
        @Nullable Unit unit = dbStatements.findUnitById(unitId);
        if (unit == null) throw new NullResultException();
        return unit;
    }

    /**
     * Helper function to assist in DTO asset value resolution.
     *
     * @param assetId Asset ID to resolve.
     * @return FullAsset corresponding to the given ID.
     */
    FullAsset resolveAsset(String assetId) throws NullResultException {
        @Nullable FullAsset asset = dbStatements.findAssetById(assetId);
        if (asset == null) throw new NullResultException();
        return asset;
    }

    /**
     * Helper function to assist in DTO user value resolution.
     *
     * @param userId User ID to resolve.
     * @return User corresponding to the given ID.
     */
    User resolveUser(String userId) throws NullResultException {
        @Nullable User user = dbStatements.findUserById(userId);
        if (user == null) throw new NullResultException();
        return user;
    }

    /**
     * Helper function to assist in DTO unitasset value resolution.
     *
     * @param unitId  Unit ID to resolve.
     * @param assetId Asset ID to resolve.
     * @return UnitAsset corresponding to the given IDs.
     */
    UnitAsset resolveUnitAsset(String unitId, String assetId) throws NullResultException {
        @Nullable UnitAsset unitAsset = dbStatements.findUnitAsset(unitId, assetId);
        if (unitAsset == null) throw new NullResultException();
        return unitAsset;
    }
}
