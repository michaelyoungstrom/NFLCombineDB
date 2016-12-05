package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private JList<String> listResult;
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
    private boolean loading = false;
    private ArrayList<Player> playersList;
    private Player selectedPlayer;


    public SearchPlayer() {
        super("Search Player");
        initialize();
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
                } else {
                    setPlayerDetailsButtonsEnabled(false);
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

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Player player : players) {
           listModel.addElement(player.getFirstName() + " " + player.getLastName() + " - " + player.getTeam());
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
}
