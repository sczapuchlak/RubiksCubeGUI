package com.Stephanie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class RubiksDB {



        private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
        private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/rubiks";     //Connection string – where's the database?
        private static final String USER = "sczapuchlak";   //
        private static final String PASSWORD = "stardust123";
        private static final String TABLE_NAME = "rubiks";
        private static final String THING_COL = "Thing_Or_Person";
        private static final String TIME_COl = "Time_it_Took";
          Scanner newScanner = new Scanner(System.in);

        RubiksDB() {
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Can't instantiate driver class; check you have drivers and classpath configured correctly?");
                cnfe.printStackTrace();
                System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
            }
        }

            void createTable(){

            try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
                 Statement statement = conn.createStatement()) {
                //You should have already created a database via terminal/command prompt OR MySQL Workbench

                //Create a table in the database, if it does not exist already
                String createTableSQLTemplate= "CREATE TABLE IF NOT EXISTS rubiks_cube (Things_that_can_solve_cube varchar(100), Time_taken_in_seconds double)";
                String createTableSQL = String.format(createTableSQLTemplate,TABLE_NAME,THING_COL,TIME_COl);

                statement.executeUpdate(createTableSQL);
                System.out.println("Created Rubiks Cube table");

                statement.close();
                conn.close();


            } catch (SQLException se) {
                se.printStackTrace();
            }
            }
            void addRecord(RubiksMain rubiksMain) {
                //Add some data
                try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

                    String addRubiksSQL = "INSERT INTO" + TABLE_NAME + "VALUES (?, ?)";
                    PreparedStatement addRubiksPS = conn.prepareStatement(addRubiksSQL);
                    // set the string  and double up
                    addRubiksPS.setString(1, rubiksMain.thingOrPerson);
                    addRubiksPS.setDouble(2, rubiksMain.timeItTook);

                    //execute the prepared statement
                    addRubiksPS.execute();

                    //ask user for record they would like to add
                    System.out.println("Added record for " + rubiksMain);
                    //close the connection
                    addRubiksPS.close();
                    conn.close();

                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            ArrayList<RubiksMain>fetchAllRecords() {
                ArrayList<RubiksMain> allRecords = new ArrayList();

                try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
                     Statement statement = conn.createStatement()) {

                    String selectAllSQL = "SELECT * FROM" + TABLE_NAME;
                    ResultSet rsAll = statement.executeQuery(selectAllSQL);

                    while (rsAll.next()) {
                        String person = rsAll.getString(THING_COL);
                        double time = rsAll.getDouble(TIME_COl);
                        RubiksMain rubiksRecord = new RubiksMain(person, time);
                        allRecords.add(rubiksRecord);
                    }
                    //close the result set, statement , and connection
                    rsAll.close();
                    statement.close();
                    conn.close();
                    return allRecords;

                }catch(SQLException se){
                    se.printStackTrace();
                    return null; //we have to return something
                }
            }
                    void delete(RubiksMain rubiksMain) {
                        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {
                            String deleteSQLTemplate = "Delete from %s WHERE %s = ? AND %s= ? ";
                            String deleteSQL = String.format(deleteSQLTemplate, TABLE_NAME, THING_COL, TIME_COl);
                            System.out.println("The SQL for the prepared statement is " + deleteSQL);
                            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
                            deletePreparedStatement.setString(1, rubiksMain.thingOrPerson);
                            deletePreparedStatement.setDouble(2, rubiksMain.timeItTook);
                            //for debugging - displays the actual SQL created in the Prepared Statement after the data has been set
                            System.out.println(deletePreparedStatement.toString());
                            //delete
                            deletePreparedStatement.execute();
                            //close everything
                            deletePreparedStatement.close();
                            conn.close();

                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }}


            /*  void modifyRecord(RubiksMain rubiksMain){
                try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {
                    String modifySQLTemplate = "Modify from %s WHERE %s = ? AND %s= ? ";
                    String modifySQL = String.format(modifySQLTemplate, TABLE_NAME, THING_COL, TIME_COl);
                    Statement statement = conn.createStatement()) {

                Scanner scan = new Scanner(System.in);

                while (true) {
                    System.out.println("Enter thing/name to find in database, or enter to continue program");
                    String thing_or_name = scan.nextLine();
                    if (thing_or_name.equals("")) {
                        break;
                    }

                    // Have to convert all data to uppercase and compare (or convert to lower and compare)
                    PreparedStatement findThing = conn.prepareStatement("SELECT * FROM rubiks_cube where UPPER(Things_that_can_solve_cube) = UPPER(?)");

                    findThing.setString(1, thing_or_name);

                    ResultSet rs = findThing.executeQuery();

                    boolean Thingfound = false;
                    while (rs.next()) {
                        Thingfound = true;
                        String nameOrThing = rs.getString(thing_or_name);
                        Double timeFound = rs.getDouble(t);
                        System.out.println("Thing or Name That Completed the Rubix Cube: " + nameOrThing +"In this amount of time:"+timeFound);
                        System.out.println("Update the time for this record? Press Enter to continue or enter the time");
                        Double newTime = newScanner.nextDouble();
                        System.out.println("Updating database. Hold on one second!");
                        String updateRecord = "UPDATE rubix_cube SET Time_taken_in_seconds= newTime WHERE name = nameOrThing";
                        System.out.println(nameOrThing+ "Updated!");
                    }
                    if (!Thingfound) {
                        System.out.println("Sorry, that person or thing was not found in the system!");


                    rs.close();


                conn.close();

                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
                System.exit(-1);
            }

        }
              }}

/*psInsert.setString(1, "PoppCubestormer II robot");
                    psInsert.setDouble(2, 5.270);
                    psInsert.executeUpdate();
                //next insert
                    psInsert.setString(1, "Fakhri Raihaan (using his feet)");
                    psInsert.setDouble(2, 27.93);
                    psInsert.executeUpdate();
                //next insert
                    psInsert.setString(1, "Ruxin Liu (age 3)");
                    psInsert.setDouble(2, 99.33);
                    psInsert.executeUpdate();
                //next insert
                    psInsert.setString(1, "Mats Valk (human record holder)");
                    psInsert.setDouble(2, 6.27);
                    psInsert.executeUpdate();*/