CAB302 Major Project
====================
Instructions
------------
### Database
Firstly, install [MariaDB Server](https://mariadb.org/download/). Once installed, open the `MySQL Client` and press `Enter` when prompted for the password. Then enter the following: 
    
    CREATE DATABASE db;

Next, open `cmd` and navigate to the installation's `bin` folder, by default:

    cd C:\Program Files\MariaDB 10.5\bin
    
Then enter the following (where `%sql_file%` is the absolute file path to the `db.sql` file in the project's root directory):

    mysql -u root -p db < %sql_file%
    
Press `Enter` when prompted for the password.
    
### Server (IDE)
To run the server, open the project in IntelliJ and go to `Run` &rarr; `Run 'Server'`.

### Client (IDE)
To run the client, open the project in IntelliJ and go to the `Maven Tool Window` on the right-hand side. If it's not open, go to `View` &rarr; `Tool Windows` &rarr; `Maven`. Within the `Maven Tool Window` open the `cab302-major-project` dropdown, go to `Lifecycle` &rarr; `compile`. Once compilation is complete, click on `Reload All Maven Projects` at the top left of the window. Then go to `Plugins` &rarr; `javafx` &rarr; `javafx:run`.

### Server & Client (Deployment)
Within the `Maven Tool Window` open the `cab302-major-project` dropdown, go to `Lifecycle` &rarr; `package`. Once the packaging process is complete, the executable jars and their properties files can be found in the target directory in the project root.

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

### Milestone 2
- [Requirements](/docs/milestone-two/requirements.md)
- [Detailed Design](/docs/milestone-two/detailed-design.md)
- [Sprint Planning](/docs/milestone-two/sprint-planning.md)
