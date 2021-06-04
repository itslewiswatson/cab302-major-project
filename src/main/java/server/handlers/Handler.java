package server.handlers;

import common.domain.Asset;
import common.domain.Unit;
import common.domain.UnitAsset;
import common.domain.User;
import common.dto.DTO;
import common.exceptions.NullResultException;
import org.jetbrains.annotations.Nullable;
import server.db.DBStrategy;

abstract public class Handler<T, U extends DTO> {
    protected final DBStrategy dbStatements;

    public Handler(DBStrategy dbStatements) {
        this.dbStatements = dbStatements;
    }

    abstract public T handle(U dto);

    protected Unit resolveUnit(String unitId) throws NullResultException {
        @Nullable Unit unit = dbStatements.findUnitById(unitId);
        if (unit == null) throw new NullResultException();
        return unit;
    }

    protected Asset resolveAsset(String assetId) throws NullResultException {
        @Nullable Asset asset = dbStatements.findAssetById(assetId);
        if (asset == null) throw new NullResultException();
        return asset;
    }

    protected User resolveUser(String userId) throws NullResultException {
        @Nullable User user = dbStatements.findUserById(userId);
        if (user == null) throw new NullResultException();
        return user;
    }

    protected UnitAsset resolveUnitAsset(String unitId, String assetId) throws NullResultException {
        @Nullable UnitAsset unitAsset = dbStatements.findUnitAsset(unitId, assetId);
        if (unitAsset == null) throw new NullResultException();
        return unitAsset;
    }
}
