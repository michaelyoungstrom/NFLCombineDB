package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;
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
    private JPanel savePlayerFormContainer;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnStats;
    private boolean loading = false;
    private ArrayList<Player> playersList;
    private Player selectedPlayer;
    private String currentSearchTermFirstName = null;
    private String currentSearchTermLastName = null;
    private int selectedIndex = -1;
    private DefaultListModel<Player> listModel;
    private PlayerForm playerForm;
    private JPanel playerDetailsPanel_;
    private JButton newPlayerButton;


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
                if (txtFirstName.getText().isEmpty() && txtLastName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(SearchPlayer.this, "Please enter a first name or a last name");
                    return;
                }
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
                int index = listResult.getSelectedIndex();
                if (index > -1) {
                    selectedPlayer = playersList.get(index);
                    selectedIndex = index;
                    playerForm.setPlayer(selectedPlayer);
                    setPlayerDetailsButtonsEnabled(true);
                } else {
                    selectedPlayer = null;
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

        newPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jDialog = new JDialog(SearchPlayer.this, "New player", true);

                PlayerForm dialogForm = new PlayerForm();
                dialogForm.setOnButtonClickedListener(new PlayerForm.OnButtonClickedListener() {
                    @Override
                    public void onButtonClickedListener(Player player, JPanel parent) {
                        if (player.getFirstName().isEmpty()) {
                            JOptionPane.showMessageDialog(parent, "First name can't be empty");
                            return;
                        } else if (player.getLastName().isEmpty()) {
                            JOptionPane.showMessageDialog(parent, "Last name can't be empty");
                            return;
                        } else if (player.getTeam().isEmpty()) {
                            JOptionPane.showMessageDialog(parent, "Team name can't be empty");
                            return;
                        }

                        try {
                            selectedPlayer = PlayersHelper.insertNewPlayer(DatabaseHelper.loginToDB(), player.getFirstName(), player.getLastName(), player.getTeam());
                            playerForm.setPlayer(selectedPlayer);
                            setPlayerDetailsButtonsEnabled(true);
                            JOptionPane.showMessageDialog(parent, "Player: " + player.getFirstName() + " " + player.getLastName() + " " + player.getTeam() + " Added successfully!");
                            jDialog.setVisible(false);
                        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                });

                jDialog.add(dialogForm.getRootPanel());
                jDialog.pack();
                jDialog.setLocationRelativeTo(SearchPlayer.this);
                jDialog.setVisible(true);
            }
        });

        setPlayerDetailsButtonsEnabled(false);

        playerForm.setButtonVisible(false);

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

    private void deleteClicked() {
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

    private void updateClicked() throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        if (selectedIndex < 0 && (selectedPlayer == null || selectedPlayer.getPlayerId() == null)) {
            return;
        }
        Player playerToUpdate = selectedPlayer;
        if (selectedIndex >= 0) {
            playerToUpdate = listModel.getElementAt(selectedIndex);
        }


        Vector<String> buttonVec = new Vector<String>();

        PlayersHelper.getCombineData(DatabaseHelper.loginToDB(), selectedPlayer);
        PlayersHelper.getPassingData(DatabaseHelper.loginToDB(), selectedPlayer);
        PlayersHelper.getReceivingData(DatabaseHelper.loginToDB(), selectedPlayer);
        PlayersHelper.getRushingData(DatabaseHelper.loginToDB(), selectedPlayer);

        buttonVec.add("Player");

        buttonVec.add("Combine");

        buttonVec.add("Passing");

        buttonVec.add("Rushing");

        buttonVec.add("Receiving");


        String[] buttons = buttonVec.toArray(new String[buttonVec.size()]);

        int rc = JOptionPane.showOptionDialog(this, "Which stat would you like to update?", "Select What to Update",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, null);

        if (rc < 0) {
            return;
        }

        String choice = buttonVec.get(rc);

        switch (choice) {
            case "Player":

                selectedPlayer = playerForm.getPlayer();
                String newFirstName = selectedPlayer.getFirstName();
                String newLastName = selectedPlayer.getLastName();
                String newTeam = selectedPlayer.getTeam();

                if (newFirstName.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchPlayer.this.rootPanel, "First name can't be empty");
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
                    selectedPlayer = playerToUpdate;
                } catch (SQLException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case "Combine":
                CombineInput combine = new CombineInput(selectedPlayer);
                combine.setLocationRelativeTo(SearchPlayer.this);
                combine.setVisible(true);
                break;
            case "Passing":
                PassingInput passing = new PassingInput(selectedPlayer);
                passing.setLocationRelativeTo(SearchPlayer.this);
                passing.setVisible(true);
                break;
            case "Rushing":
                RushingInput rushing = new RushingInput(selectedPlayer);
                rushing.setLocationRelativeTo(SearchPlayer.this);
                rushing.setVisible(true);
                break;
            case "Receiving":
                ReceivingInput receiving = new ReceivingInput(selectedPlayer);
                receiving.setLocationRelativeTo(SearchPlayer.this);
                receiving.setVisible(true);
                break;
        }
    }

}
