package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.PassingData;
import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created by mayoungstrom on 12/6/16.
 */
public class PassingInput extends JFrame {
    private JButton btnBack;
    private JButton btnOk;
    private JTextField txtCompPerc;
    private JTextField txtYards;
    private JTextField txtTouchdowns;
    private JTextField txtInterceptions;
    private JTextField txtRating;
    private JPanel Panel1;

    private Player currentPlayer;

    public PassingInput(Player player) {
        super("Passing Input");
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

            float compPerc = Float.parseFloat(txtCompPerc.getText());
            int yards = Integer.parseInt(txtYards.getText());
            int touchdowns = Integer.parseInt(txtTouchdowns.getText());
            int interceptions = Integer.parseInt(txtInterceptions.getText());
            float rating = Float.parseFloat(txtRating.getText());

            if (isUpdate()) {
                PlayersHelper.updatePassing(DatabaseHelper.loginToDB(), currentPlayer,
                        compPerc, yards, touchdowns, interceptions, rating);
            } else {
                PlayersHelper.insertNewPassing
                        (DatabaseHelper.loginToDB(), currentPlayer, compPerc, yards, touchdowns, interceptions, rating);
            }

            if (isUpdate()) {
                JOptionPane.showMessageDialog(this, "Passing Data successfully updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Passing Data successfully added!");
            }

            Panel1.setVisible(false);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    public void setValues() {
        PassingData passingData = currentPlayer.getPassingData();

        txtCompPerc.setText(Float.toString(passingData.getCompPerc()));
        txtYards.setText(Integer.toString(passingData.getYard()));
        txtTouchdowns.setText(Integer.toString(passingData.getTouchdowns()));
        txtInterceptions.setText(Integer.toString(passingData.getInterceptions()));
        txtRating.setText(Float.toString(passingData.getRating()));
    }

    private boolean isUpdate() {
        return currentPlayer.getPlayerId() != null && currentPlayer.getPassingData() != null && currentPlayer.getPassingData().getPassingId() != null;
    }

}
