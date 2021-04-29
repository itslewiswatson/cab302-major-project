Detailed Design
===============

Java Classes
------------
[JavaDoc](/docs/JavaDoc/index.html)

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
  - username – {PK} {FK} CHAR(255) 
  - unitName – {PK} {FK} CHAR(255)
- Asset
  - name – {PK} CHAR(255)
- UnitAssets
  - unitName – {PK} {FK} CHAR(255)
  - assetName – {PK} {FK} CHAR(255)
  - quantity – BIGINT 
- Trade
  - ID – {PK} BIGINT
  - unitName – {FK} CHAR(255)
  - assetName – {FK} CHAR(255)
  - date – DATE 
  - type – CHAR(4)
  - quantity – BIGINT 
  - price – BIGINT 
  - completed – BOOL

Network Protocol
----------------
