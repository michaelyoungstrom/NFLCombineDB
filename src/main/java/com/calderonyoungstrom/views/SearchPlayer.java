package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.*;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created on 12/4/16.
 */
public class SearchPlayer extends JFrame {
    private JPanel rootPanel;
    private JPanel searchFieldsPanel;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JButton btnSearch;
    private JPanel panelResultList;
    private JList<Player> listResult;
    private JPanel searchPanel;
    private JPanel playerDetailsPanel;
    private JPanel namePanel;
    private JPanel buttonsPanel;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnStats;
    private JTextField txtDetailsFirstName;
    private JTextField txtDetailsLastName;
    private JTextField txtDetailsTeam;
    private JButton btnAdd;
    private boolean loading = false;
    private ArrayList<Player> playersList;
    private Player selectedPlayer;
    private String currentSearchTermFirstName = null;
    private String currentSearchTermLastName = null;
    private int selectedIndex = -1;
    private DefaultListModel<Player> listModel;


    public SearchPlayer() {
        super("Search Player");
        initialize();

    }

    private void initialize() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("User searched");
                System.out.println("First name: " + txtFirstName.getText());
                System.out.println("Last name: " + txtLastName.getText());

                currentSearchTermFirstName = txtFirstName.getName();
                currentSearchTermLastName = txtLastName.getName();

                searchPlayers();

            }
        });

        listResult.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getFirstIndex() > -1) {
                    selectedPlayer = playersList.get(e.getFirstIndex());

                    txtDetailsFirstName.setText(selectedPlayer.getFirstName());
                    txtDetailsLastName.setText(selectedPlayer.getLastName());
                    txtDetailsTeam.setText(selectedPlayer.getTeam());
                    setPlayerDetailsButtonsEnabled(true);
                    selectedIndex = e.getFirstIndex();
                } else {
                    setPlayerDetailsButtonsEnabled(false);
                }
            }
        });

        btnStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayer == null) {
                    return;
                }
                try {
                    PlayersHelper.getCombineData(DatabaseHelper.loginToDB(), selectedPlayer);
                    PlayersHelper.getPassingData(DatabaseHelper.loginToDB(), selectedPlayer);
                    PlayersHelper.getReceivingData(DatabaseHelper.loginToDB(), selectedPlayer);
                    PlayersHelper.getRushingData(DatabaseHelper.loginToDB(), selectedPlayer);

                    PlayerStats playerStats = new PlayerStats(selectedPlayer);
                    playerStats.setVisible(true);

                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }

            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateClicked();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClicked();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addClicked();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setPlayerDetailsButtonsEnabled(false);

    }

    private void searchPlayers() {
        loading = true;
        try {
            playersList = PlayersHelper.findPlayersByName(DatabaseHelper.loginToDB(), txtFirstName.getText(), txtLastName.getText());

            if (playersList != null) {
                System.out.println("Retrieved players. Got:" + playersList.size());
            }

            updateList(playersList);
        } catch (IOException | ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        loading = false;
    }

    private void updateList(ArrayList<Player> players) {
        if (players == null) {
            emptyList();
            return;
        }

        listModel = new DefaultListModel<>();

        for (Player player : players) {
           listModel.addElement(player);
        }

        listResult.setModel(listModel);
        listResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void emptyList() {

    }

    private void setPlayerDetailsButtonsEnabled(boolean enabled) {
        btnStats.setEnabled(enabled);
        btnDelete.setEnabled(enabled);
        btnUpdate.setEnabled(enabled);
    }

    private void deleteClicked(){
        if (selectedPlayer == null) {
            return;
        }

        try {
            int response = JOptionPane.showConfirmDialog(this, String.format(Locale.getDefault(),
                    "Are you sure you want to delete %s %s?", selectedPlayer.getFirstName(),
                    selectedPlayer.getLastName()), "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                PlayersHelper.deletePlayer(DatabaseHelper.loginToDB(), selectedPlayer.getPlayerId());
                if (listModel != null && selectedIndex >= 0) {
                    listModel.remove(selectedIndex);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void addClicked() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (selectedPlayer == null) {

            String newFirstName = txtDetailsFirstName.getText();
            String newLastName = txtDetailsLastName.getText();
            String newTeam = txtDetailsTeam.getText();

            if (newFirstName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name can't be empty");
                return;
            } else if (newLastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Last name can't be empty");
                return;
            } else if (newTeam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Team name can't be empty");
                return;
            }

            try {
                PlayersHelper.insertNewPlayer(DatabaseHelper.loginToDB(), newFirstName, newLastName, newTeam);
                JOptionPane.showMessageDialog(this, "Player: " + newFirstName + " " + newLastName + " " + newTeam + " Added successfully!");
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {

            Vector<String> buttonVec = new Vector<String>();

            PlayersHelper.getCombineData(DatabaseHelper.loginToDB(), selectedPlayer);
            PlayersHelper.getPassingData(DatabaseHelper.loginToDB(), selectedPlayer);
            PlayersHelper.getReceivingData(DatabaseHelper.loginToDB(), selectedPlayer);
            PlayersHelper.getRushingData(DatabaseHelper.loginToDB(), selectedPlayer);

            if (selectedPlayer.getCombineData() == null){
                buttonVec.add("Combine");
            }

            if (selectedPlayer.getPassingData() == null){
                buttonVec.add("Passing");
            }

            if (selectedPlayer.getRushingData() == null){
                buttonVec.add("Rushing");
            }

            if (selectedPlayer.getReceivingData() == null){
                buttonVec.add("Receiving");
            }

            if (buttonVec.size() == 0){
                JOptionPane.showMessageDialog(this, "There are no empty stat categories to add");
            } else {

                String[] buttons = buttonVec.toArray(new String[buttonVec.size()]);

                int rc = JOptionPane.showOptionDialog(this, "Which stat would you like to add?", "Select What to Add",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, null);

                String choice = buttonVec.get(rc);

                switch(choice){
                    case "Combine":
                        //combine code
                        combineInput combine = new combineInput(selectedPlayer);
                        combine.setVisible(true);
                        break;
                    case "Passing":
                        //passing code
                        passingInput passing = new passingInput(selectedPlayer);
                        passing.setVisible(true);
                        break;
                    case "Rushing":
                        //rushing code
                        rushingInput rushing = new rushingInput(selectedPlayer);
                        rushing.setVisible(true);
                        break;
                    case "Receiving":
                        //receiving code
                        receivingInput receiving = new receivingInput(selectedPlayer);
                        receiving.setVisible(true);
                        break;
                }

            }

        }
    }

    private void updateClicked() throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        if (selectedIndex < 0) {
            return;
        }

        Player playerToUpdate = listModel.getElementAt(selectedIndex);

        Vector<String> buttonVec = new Vector<String>();

        PlayersHelper.getCombineData(DatabaseHelper.loginToDB(), selectedPlayer);
        PlayersHelper.getPassingData(DatabaseHelper.loginToDB(), selectedPlayer);
        PlayersHelper.getReceivingData(DatabaseHelper.loginToDB(), selectedPlayer);
        PlayersHelper.getRushingData(DatabaseHelper.loginToDB(), selectedPlayer);

        buttonVec.add("Player");

        if (selectedPlayer.getCombineData() != null){
            buttonVec.add("Combine");
        }

        if (selectedPlayer.getPassingData() != null){
            buttonVec.add("Passing");
        }

        if (selectedPlayer.getRushingData() != null){
            buttonVec.add("Rushing");
        }

        if (selectedPlayer.getReceivingData() != null){
            buttonVec.add("Receiving");
        }

        String[] buttons = buttonVec.toArray(new String[buttonVec.size()]);

        int rc = JOptionPane.showOptionDialog(this, "Which stat would you like to update?", "Select What to Update",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, null);

        String choice = buttonVec.get(rc);

        switch(choice){
            case "Player":
                String newFirstName = txtDetailsFirstName.getText();
                String newLastName = txtDetailsLastName.getText();
                String newTeam = txtDetailsTeam.getText();

                if (newFirstName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "First name can't be empty");
                    return;
                } else if (newLastName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Last name can't be empty");
                    return;
                } else if (newTeam.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Team name can't be empty");
                    return;
                }

                playerToUpdate.setFirstName(newFirstName);
                playerToUpdate.setLastName(newLastName);
                playerToUpdate.setTeam(newTeam);

                try {
                    PlayersHelper.updatePlayer(DatabaseHelper.loginToDB(), playerToUpdate, newFirstName, newLastName, newTeam);
                } catch (SQLException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case "Combine":
                //combine code
                break;
            case "Passing":
                //passing code
                break;
            case "Rushing":
                //rushing code
                break;
            case "Receiving":
                //receiving code
                break;
        }
    }
}
