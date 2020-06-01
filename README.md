## Trackit (Java)
This was originally created as my final project for COMSC-256, Advanced Programming in Java.
This was inspired by my old project, TrackIt, which you can view [here](https://github.com/jhanreg11/TrackIt).

In this project, I utilize JavaFX and MySQL to create a fully functional small-business accounting application. 

Some of the features include:
* A user login system.
* Ability to create business items, sales, and purchases for a user.
* Ability to view past transactions for a user's business.
* Ability to view a summary of a user's sales/purchases/profits over a selected time period.
* Ability to view a ranking of all a user's business items by sales, purchases, and profits over a selected time period.
* Ability to view a graph of sales, purchases, and profits over a selected time period.
* Ability to edit user and business item information.

## Running The Project
1. Clone this repository into your machine
2. Download MySQL (if you don't already have it) and start the server. I used version 8.0.15, but most others should work as well.
    * You can download it from [here](https://dev.mysql.com/downloads/mysql/).
3. Run the SQL script named [create_db.sql](https://github.com/jhanreg11/TrackItJava/create_db.sql) to initialize the 
database.
    * You can see a tutorial on how to run a SQL script [here](https://dev.mysql.com/doc/refman/8.0/en/mysql-batch-commands.html).
4. Run the project using the following command in the Terminal/Command Line from the root directory of the project:
    ```shell script
    ./gradlew run
    ```