Detailed Design
===============

Java Classes
------------

Class Diagram
-------------

GUI Design
----------

Database Schema
---------------
### Entity Relationship Diagram
![Entity Relationship Diagram](/docs/images/erd.png)
### Tables & Columns
- User 
  - username – {PK} CHAR(255)
  - password – TINYBLOB 
  - admin – BOOL 
- Unit
  - name – {PK} CHAR(255)
  - credits – BIGINT
- UnitUsers
  - username – {PK} CHAR(255) 
  - unitName – {PK} CHAR(255)
- Asset
  - name – {PK} CHAR(255)
- UnitAssets
  - unitName – {PK} CHAR(255)
  - assetName – {PK} CHAR(255)
  - quantity – BIGINT 
- Trade
  - ID – {PK} BIGINT
  - unitName – CHAR(255)
  - assetName – CHAR(255)
  - date – DATE 
  - type – CHAR(4)
  - quantity – BIGINT 
  - price – BIGINT 
  - completed – BOOL

Network Protocol
----------------
