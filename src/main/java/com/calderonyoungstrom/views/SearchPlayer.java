package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

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
    private boolean loading = false;
    private ArrayList<Player> playersList;


    public SearchPlayer() {
        super("Search Player");
        initialize();
    }

    void initialize() {
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
           listModel.addElement(player.getFname() + " " + player.getLname());
        }

        listResult.setModel(listModel);

    }

    private void emptyList() {

    }
}
