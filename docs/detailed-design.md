Detailed Design
===============

Java Classes
------------
[JavaDoc](https://mitchelljqegan.github.io/javadoc/)

Class Diagram
-------------
![UML Class Diagram](/docs/images/uml-class-diagram.png)

GUI Design
----------
[Fluid UI](https://www.fluidui.com/editor/live/preview/cF9KRlduQ2xMSEoxREpxdHZUNm9wV3N5S3ZwT2MydFJHNQ==)

Database Schema
---------------
### Entity Relationship Diagram
![Entity Relationship Diagram](/docs/images/erd.png)
### Tables & Columns
- User 
  - username – {PK} CHAR(255)
  - password – CHAR(255)
  - admin – BOOL 
- Unit
  - name – {PK} CHAR(255)
  - credits – INT
- UnitUsers
  - username – {PK} {FK} CHAR(255) 
  - unitName – {PK} {FK} CHAR(255)
- Asset
  - name – {PK} CHAR(255)
- UnitAssets
  - unitName – {PK} {FK} CHAR(255)
  - assetName – {PK} {FK} CHAR(255)
  - quantity – INT 
- Trade
  - id – {PK} INT
  - unitName – {FK} CHAR(255)
  - assetName – {FK} CHAR(255)
  - date – DATE 
  - type – CHAR(4)
  - quantity – INT 
  - price – INT 
  - completed – BOOL

Network Protocol
----------------

### For any request, the following occurs:

1. Client create a serializable data transfer object (DTO) with any information required to be relayed
2. The client sends this DTO to the server over a socket connection
3. The server intercepts this request
4. An appropriate handler route is found for the request
5. The request is handled and the result sent back over the socket
6. Client receives this serializable result

### Types

- Server
  - Create socket at startup.
  - Listen and accept incoming connections.
- Client
  - Create socket at startup.
- Login
  - Client sends `LoginDTO` with `string username`
  - Server returns `User | null`
- Add asset
  - Client sends `AddAssetDTO` with `string assetName`
  - Server returns an array of `FullAsset`
- Add user to unit
  - Client sends `AddUserToUnitDTO` with `string userId` and `string unitId`
  - Server returns success `boolean`
- Create or update unit asset
  - Client sends `CreateOrUpdateUnitAssetDTO` with `string unitId`, `string assetId` and `int quantity`
  - Server returns an array of `UnitAsset`
- Delete asset
  - Client sends `DeleteAssetDTO` with `string assetId`
  - Server returns a success `boolean`
- Delete user
  - Client sends a `DeleteUserDTO` with `string userId`
  - Server returns a success `boolean`
- Get assets
  - Client sends an empty `GetAssetsDTO`
  - Server returns an array of `FullAsset`
- Get historic trades
  - Client sends an empty `GetHistoricTradesDTO`
  - Server returns an array of `Trade` or `null` if there are no historic trades
- Get trades
  - Client sends an empty `GetTradesDTO`
  - Server returns an array of `Trade` or `null` if there are no active trades
- Get unit assets
  - Client sends a `GetUnitAssetsDTO` with a `string unitId`
  - Server returns an array of `UnitAsset` belonging to the specified `unitId`
- Get units
  - Client sends a `GetUnitsDTO` with an optional `string userId`
  - Server returns an array of all `Unit` in the database, or all the `Unit` the specified `userId` is part of
- Get unit trades
  - Client sends a `GetUnitTradesDTO` with a `string unitId`
  - Server returns an array of `Trade`
- Get unit users
  - Client sends a `GetUnitUsers` with a `string unitId`
  - Server returns an array of `User`
- Get users
  - Client sends an empty `GetUsersDTO`
  - Server returns an array of all `User`
- New trade
  - Client sends a `NewTradeDTO` with:
    - `string assetId` (for what asset)
    - `string unitId` (from what unit trade is coming from)
    - `string userId` (who is making the trade)
    - `TradeType type` (BUY or SELL)
    - `int quantity`
    - `int price`
  - Server returns a `Trade` representing the newly constructed trade offer
- New unit
  - Client sends a `NewUnitDTO` with `string unitName` and `int credits`
  - Server returns `Unit | null` representing the new unit or an error in creating it
- New user
  - Client sends a `NewUserDTO` with `string username`, `string password` and `boolean isAdmin`
  - Server returns `User | null` representing the new user or an error in creating it
- Remove trade
  - Client sends a `RemoveTradeDTO` with `string tradeId`
  - Server returns a success `boolean`
- Remove unit asset
  - Client sends a `RemoveUnitAssetDTO` with `string unitId` and `string assetId`
  - Server returns a success `boolean`
- Remove user from unit
  - Client sends a `RemoveUserFromUnitDTO` with `string userId` and `string unitId`
  - Server returns a success `boolean`
- Update credits
  - Client sends a `UpdateCreditsDTO` with `string unitId` and `int newCredits`
  - Server returns a `Unit | null` depending on success of request
- Update password
  - Client sends a `UpdatePasswordDTO` with `string userId` and `string newPassword`
  - Server returns `User | null` depending on success of request
- Update user permission
  - Client sends a `UpdateUserPermissionsDTO` with `string userId` and `boolean isAdmin`
  - Server returns `User | null` depending on success of request