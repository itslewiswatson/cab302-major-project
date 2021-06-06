Detailed Design
===============

Java Classes
------------
[JavaDoc](https://mitchelljqegan.github.io/javadoc/)

Class Diagrams
-------------
  - client

![client](/docs/images/client-uml-class-diagram.svg)

  - common

![common](/docs/images/common-uml-class-diagram.svg)

  - server

![server](/docs/images/common-uml-class-diagram.svg)

GUI Design
----------
Initial GUI design mockups can be viewed on [Fluid UI](https://www.fluidui.com/editor/live/preview/cF9KRlduQ2xMSEoxREpxdHZUNm9wV3N5S3ZwT2MydFJHNQ==).


The GUI was designed with the user in mind. It was important to restrict access to the platform from as early as possible, for security and for the sake of keeping accurate transactional logs. As such, the decision was made for the first screen to be a log in screen.

![Login - Standard](/docs/images/gui-login-standard.png)

Upon successful login, the user is taken to the landing page. Here they have a complete overview of everything they can access in the program. It is important for these to be quickly identifiable, so they are all buttons in a vertical line. They are split where necessary to minimize mental overhead.

![Landing - Standard](/docs/images/gui-landing-standard.png)

The all trades screen is a simple table. This provides all information needed in a clear manner. The ability to sort trades by any of the column fields means the user has all the power for which trades are more important to them. To find out more information about a trade, it is available by double-clicking the table row, which will open a dialog. A helper label is included to assist first-time users.

![All Trades](/docs/images/gui-all-trades.png)
![All Trades Trade Information](/docs/images/gui-all-trades-trade-information.png)

The trades history page follows a very similar structure for the sake of familiarity.

![Trade History](/docs/images/gui-trade-history.png)
![Trade History Trade Information](/docs/images/gui-trade-history-trade-information.png)

The my unit's trades page also includes a table view of trades. However, because the domain has support for multiple unit users (users can be in 1 or many units, and units can contain 1 or many users), we include a combo box of the user's enrolled units. This allows them to filter on a per-unit basis.

![Unit Trades](/docs/images/gui-unit-trades.png)
![Unit Trades Trade Information](/docs/images/gui-unit-trades-trade-information.png)

The new trade dialog has a simple graph which illustrates asset price history over time. This is achieved through a line graph which appears front and centre in the dialog. It populates onces a specific asset has been selected. This helps users in making trading decisions on which assets to buy or sell by allowing them to easily identify visual trends.

![New Trade](/docs/images/gui-new-trade.png)

Once administrators log in, they are taken to the same landing page but with a couple extra buttons.

![Landing - Admin](/docs/images/gui-landing-admin.png)

From the manage assets page, administrators can view a table of all listed assets. Here they can add new assets or remove those that are no longer in circulation.

![Manage Assets](/docs/images/gui-manage-assets.png)

The manage units page allows administrators to create new and update existing units. 

![Manage Units](/docs/images/gui-manage-units.png)
![New Unit](/docs/images/gui-new-unit.png)

The manage users page allows administrators to create new and update & remove existing users. 

![Manage Users](/docs/images/gui-manage-users.png)
![New User](/docs/images/gui-new-user.png)

Database Schema
---------------
### Database Choice
The project uses MariaDB for storage of all its relational data. This is because MariaDB is open-source and complies with all requirements. However, another relational database can be substituted provided the same schema is followed. See below for this schema.

### Entity Relationship Diagram
![Entity Relationship Diagram](/docs/images/erd.png)
### Tables & Columns
- users 
  - id - {PK} VARCHAR(255)
  - username – VARCHAR(255)
  - password – VARCHAR(255)
  - admin – BOOLEAN
- units
  - id - {PK} VARCHAR(255)
  - name – VARCHAR(255)
  - credits – INT
- unitusers
  - user_id – {PK} {FK} VARCHAR(255) 
  - unit_id – {PK} {FK} VARCHAR(255)
- assets
  - id - VARCHAR(255) {PK}
  - name – VARCHAR(255)
  - date_added - DATE
- unitassets
  - unit_id – {PK} {FK} VARCHAR(255)
  - asset_id – {PK} {FK} VARCHAR(255)
  - quantity – INT 
- trades
  - id – {PK} VARCHAR(255)
  - unit_id – {FK} VARCHAR(255)
  - asset_id – {FK} VARCHAR(255)
  - user_id – {FK} VARCHAR(255)
  - date_listed – DATE 
  - type – VARCHAR(4)
  - quantity – INT 
  - price – INT 
  - quantity_filled - INT
  - date_filled - DATE

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
