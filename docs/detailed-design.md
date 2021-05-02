Detailed Design
===============

Java Classes
------------
[JavaDoc](https://mitchelljqegan.github.io/cab302-major-project-javadoc/)

Class Diagram
-------------

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
  - ID – {PK} INT
  - unitName – {FK} CHAR(255)
  - assetName – {FK} CHAR(255)
  - date – DATE 
  - type – CHAR(4)
  - quantity – INT 
  - price – INT 
  - completed – BOOL

Network Protocol
----------------
- All communication facilitated by sockets and serialisation.
- Client &rarr; Server
  - Credentials class (login)
  - Empty trade class (request trades)
  - Trade class (new trade)
  - Existing user class (change password)
  - Unit class (new unit)
- Server &rarr; Client
