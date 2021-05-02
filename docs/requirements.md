Requirements
============
Must Have
---------
- A ***server*** which keeps track of all system data.
  - Data is stored in a database (MariaDB). Data includes: 
    - User information (username, password, account type, organisational unit).
    - Organisational unit information (organisational unit name, credits, assets, and the quantity of each asset).
    - Asset types (asset names).
    - Current trades (BUY/SELL, organisational unit, asset name, quantity, price, date).
    - Trade history (same as above).
  - Outstanding trades are periodically checked and reconciled if possible.
    - E.g., there is a BUY offer for 100 CPU hours at 10 credits each and a SELL offer for 500 CPU hours at 10 credits each. Once reconciled, the BUY offer will be fulfilled, and the sell offer will be updated to 400 CPU hours at 10 credits each. Reconciling will only proceed if the selling price is less than or equal to the buying price (buyer pays the lower price).
  - Offers that would put an organisational unit into debt are prevented from being listed.
    - I.e., BUY offers or SELL offers that exceed the organisational unit’s number of credits or number of assets, respectively.
  - Support for an artificially limitless number of commodities, users and trades.
  - Support for zero or many concurrent clients using the platform and making trades.
- A ***client*** for users to interface with the server through.
  - User-friendly GUI.
  - A login screen that authenticates a user's credentials before allowing them in the system in the form of a username + password combination.
  - Users can:
    - View currently listed BUY and SELL offers.
    - List offers using the credit balance and assets of their organisational unit.
    - When listing an offer, be able to view all available assets.
    - Remove currently listed offers from their organisational unit.
  - Administrators can:
    - Add new organisational units, asset types and users (including administrators). 
    - Modify an organisational unit’s number of credits and assets.

Should Have
-----------
- Server
    - Port number is retrieved from a configuration file.
- Client
  - The server’s IP address and port number are retrieved from a configuration file.
  - Passwords are hashed before being sent to the server.
  - Users can change their own password.
  - Users are presented with helpful and informative error messages when the system is unable to perform their desired action.

Nice to Have
------------
- Client
  - Users:
    - Can see the graphical price history (date on the X axis and price on the Y axis) of an asset when they go to list a BUY or SELL offer.
    - Are immediately notified when a trade involving their organisational unit is fulfilled. 
    - Ability to filter and sort currently listed trades.

