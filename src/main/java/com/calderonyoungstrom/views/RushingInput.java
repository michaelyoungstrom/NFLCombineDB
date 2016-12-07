package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.RushingData;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created by mayoungstrom on 12/6/16.
 */
public class RushingInput extends JFrame {
    private JPanel Panel1;
    private JButton btnBack;
    private JTextField txtYards;
    private JTextField txtTouchdowns;
    private JTextField txtLongest;
    private JTextField txtYardsPerAtt;
    private JTextField txtYardsPerGame;
    private JButton btnOk;

    private Player currentPlayer;


    public RushingInput(Player player) {
        super("Rushing Input");
        this.currentPlayer = player;

        if (isUpdate()) {
            setValues();
        }

        initialize();
    }

    /**
     * Initializes the LoginForm
     */
    private void initialize() {
        setContentPane(Panel1);
        pack();

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClicked();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel1.setVisible(false);
                dispose();
            }
        });
    }

    public void okClicked() {
        try {

            int yards = Integer.parseInt(txtYards.getText());
            int touchdowns = Integer.parseInt(txtTouchdowns.getText());
            int longest = Integer.parseInt(txtLongest.getText());
            float yardsPerAttempt = Float.parseFloat(txtYardsPerAtt.getText());
            float yardsPerGame = Float.parseFloat(txtYardsPerGame.getText());

            if (isUpdate()) {
                PlayersHelper.updateRushing(DatabaseHelper.loginToDB(), currentPlayer,
                        yards, touchdowns, longest, yardsPerAttempt, yardsPerGame);
            } else {
                PlayersHelper.insertNewRushing
                        (DatabaseHelper.loginToDB(), currentPlayer, yards, touchdowns, longest, yardsPerAttempt,
                                yardsPerGame);
            }

            if (isUpdate()) {
                JOptionPane.showMessageDialog(this, "Rushing Data successfully updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Rushing Data successfully added!");
            }
            Panel1.setVisible(false);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private boolean isUpdate() {
        return currentPlayer.getRushingData() != null && currentPlayer.getRushingData().getRushingId() != null;
    }


    public void setValues() {
        RushingData rushingData = currentPlayer.getRushingData();

        txtYards.setText(Integer.toString(rushingData.getYards()));
        txtTouchdowns.setText(Integer.toString(rushingData.getTouchdowns()));
        txtLongest.setText(Integer.toString(rushingData.getLongest()));
        txtYardsPerAtt.setText(Float.toString(rushingData.getYardsPerAttempt()));
        txtYardsPerGame.setText(Float.toString(rushingData.getYardsPerGame()));
    }

}
