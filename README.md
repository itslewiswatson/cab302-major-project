CAB302 Major Project
====================
Instructions
------------
### Database
Firstly, install [MariaDB Server](https://mariadb.org/download/). Once installed, open the `MySQL Client` and press `Enter` when prompted for the password. Then enter the following: 
    
    CREATE DATABASE db;

Next, open `cmd` and navigate to the installation's `bin` folder, by default:

    cd C:\Program Files\MariaDB 10.5\bin
    
Then enter the following (where `sql_file` is the absolute file path to the `db.sql` file in the project's root directory):

    mysql -u root -p db < sql_file
    
And press `Enter` when prompted for a password.
    
Documentation
-------------
- [Requirements](/docs/requirements.md)  
- [Detailed Design](/docs/detailed-design.md)
- [Sprint Planning](/docs/sprint-planning.md)

Milestones
----------
### Milestone 1
- [Requirements](/docs/milestone-one/requirements.md)  
- [Detailed Design](/docs/milestone-one/detailed-design.md)
- [Sprint Planning](/docs/milestone-one/sprint-planning.md)
- [Video](https://youtu.be/3HGGa-gk6uI)
