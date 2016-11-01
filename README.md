#Tempic Readme

## MYSQL Connection
In order to connect to the Google Cloud SQL Server when running the app locally you need to be in the UZH IP Adress range.
We restricted the access to the server to prevent other users from querying our database.
So any IP in the range between **89.206.0.0** and **89.206.255.255** is able to query the database (given the correct credentials were used.)

## Database Structure
The database currently only has one table with the following columns:
![alt text](https://raw.githubusercontent.com/mnbucher/Tempic/master/docs/database_structure.png "Tempic Database Structure")