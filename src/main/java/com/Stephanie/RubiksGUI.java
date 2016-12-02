package com.Stephanie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RubiksGUI extends JFrame {
    private JPanel mainPanel;

    private JTextField enterThingOrPersonName;
    private JTextField enterTimeCompleted;
    private JLabel ThingOrPersonLabel;
    private JLabel EnterTimeLabel;
    private JButton submitButton;

    private JButton deleteButton;

    private JList<RubiksMain> allRubiksList;
    private JScrollPane allRubiksListScrollPane;
    private  DefaultListModel<RubiksMain> allRubiksModel;

    private Controller controller;


    RubiksGUI(Controller controller) {

        this.controller = controller;  //Store a reference to the controller object.
        // Need this to make requests to the database.

        addComponents();   //Add GUI components

        //Configure the list model

        allRubiksModel = new DefaultListModel<RubiksMain>();
        allRubiksList.setModel(allRubiksModel);

        //and listeners - only one in this program, but put in method to keep tidy.
        addListeners();

        //Regular setup stuff for the window / JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);

    }

    private void addListeners() {

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //identify what is selected
                RubiksMain rubiksMain = allRubiksList.getSelectedValue();
                if (rubiksMain == null){
                    JOptionPane.showMessageDialog(RubiksGUI.this, "Please select an time to delete");
                }else{
                    controller.delete(rubiksMain);
                    ArrayList<RubiksMain>rubiksMains = controller.getAllData();
                    setListData(rubiksMains);
                }
            }
        });




        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Read data, send message to database via controller

                String place = enterThingOrPersonName.getText();

                if (place.isEmpty()) {
                    JOptionPane.showMessageDialog(RubiksGUI.this, "Enter a Person or Thing Who Beat the Rubix Cube: ");
                    return;
                }
                double elev;

                try {
                    elev = Double.parseDouble(enterTimeCompleted.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(RubiksGUI.this, "Enter a time it was completed: ");
                    return;
                }

                RubiksMain rubiksRecord = new RubiksMain(place, elev);
                controller.addRecordToDatabase(rubiksRecord);

                //Clear input JTextFields
                enterThingOrPersonName.setText("");
                enterTimeCompleted.setText("");

                //and request all data from DB to update list
                ArrayList<RubiksMain> allData = controller.getAllData();
                setListData(allData);
            }
        });




    }

    //This does the same as the IntelliJ GUI designer.
    private void addComponents() {

        //Initialize the components
        submitButton = new JButton("Submit");
        enterThingOrPersonName = new JTextField();
        enterTimeCompleted = new JTextField();
        ThingOrPersonLabel = new JLabel("Person or Thing That Completed The Rubix Cube");
        EnterTimeLabel = new JLabel("Time it was completed in(in seconds)");

        //and the JList, add it to a JScrollPane
        allRubiksList = new JList<RubiksMain>();
        allRubiksListScrollPane = new JScrollPane(allRubiksList);

        deleteButton = new JButton("Delete");

        //Create a JPanel to hold all of the above
        mainPanel = new JPanel();

        //A LayoutManager is in charge of organizing components within a JPanel.
        //BoxLayout can display items in a vertical or horizontal list, (and also other configurations, see JavaDoc)
        BoxLayout layoutMgr = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layoutMgr);

        //And add the components to the JPanel mainPanel
        mainPanel.add(ThingOrPersonLabel);
        mainPanel.add(enterThingOrPersonName);
        mainPanel.add(EnterTimeLabel);
        mainPanel.add(enterTimeCompleted);
        mainPanel.add(submitButton);
        mainPanel.add(allRubiksList);
        mainPanel.add(deleteButton);


    }


    void setListData(ArrayList<RubiksMain> data) {

        //Display data in allDataTextArea
        allRubiksModel.clear();

        for (RubiksMain elev : data) {
            allRubiksModel.addElement(elev);
        }
    }

}

