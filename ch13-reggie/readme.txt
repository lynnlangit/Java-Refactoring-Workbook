You'll need to install mysql before running this program. 
(On the Mac, I've found MAMP to be a helpful all-in-one package.)
The original version used ODBC on Windows but it's being de-supported. 

You can use "reggie.sql" to set up the database and tables; you can run it via the mysql command line tool.

Your application will need a suitable driver; I used the Connector/J driver from dev.mysql.com; 
this required adding mysql-connector-java-x.y.z-bin.jar to my build path.

There are places in several files that you will need to change to load the right driver,
and to set up the right connection string, username, and password (which you set for your database).
