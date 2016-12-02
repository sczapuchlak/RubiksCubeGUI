package com.Stephanie;


import java.util.ArrayList;

public class Controller {

    static RubiksGUI gui;
    static RubiksDB db;

    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.startApp();

    }

    private void startApp() {

        db = new RubiksDB();
        db.createTable();
        ArrayList<RubiksMain> allData = db.fetchAllRecords();
        gui = new RubiksGUI(this);
        gui.setListData(allData);
    }
    void delete(RubiksMain rubiksMain){
        db.delete(rubiksMain);
    }

    ArrayList<RubiksMain> getAllData() {
        return db.fetchAllRecords();
    }

    void addRecordToDatabase(RubiksMain rubiksMain) {
        db.addRecord(rubiksMain);
    }}

