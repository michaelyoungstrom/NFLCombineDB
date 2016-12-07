package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.ReceivingData;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created by mayoungstrom on 12/6/16.
 */
public class receivingInput extends JFrame {
    private JPanel Panel1;
    private JButton btnBack;
    private JButton btnOk;
    private JTextField txtReceptions;
    private JTextField txtCatchPerc;
    private JTextField txtYards;
    private JTextField txtYardsPerRec;
    private JTextField txtTouchdowns;
    private JTextField txtYardsPerGame;

    private Player currentPlayer;
    private boolean isUpdate;

    public receivingInput(Player player, boolean isUpdate) {
        super("Receiving Input");
        this.currentPlayer = player;
        this.isUpdate = isUpdate;

        if (isUpdate) {
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

            int receptions = Integer.parseInt(txtReceptions.getText());
            float catchPerc = Float.parseFloat(txtCatchPerc.getText());
            int yards = Integer.parseInt(txtYards.getText());
            float yardsPerRec = Float.parseFloat(txtYardsPerRec.getText());
            int touchdowns = Integer.parseInt(txtTouchdowns.getText());
            float yardsPerGame = Float.parseFloat(txtYardsPerGame.getText());

            if (isUpdate) {
                PlayersHelper.updateReceiving(DatabaseHelper.loginToDB(), currentPlayer, receptions,
                        catchPerc, yards, yardsPerRec, touchdowns, yardsPerGame);
            } else {
                PlayersHelper.insertNewReceiving
                        (DatabaseHelper.loginToDB(), currentPlayer, receptions, catchPerc, yards, yardsPerRec,
                                touchdowns, yardsPerGame);
            }

            if (isUpdate) {
                JOptionPane.showMessageDialog(this, "Receiving Data successfully updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Receiving Data successfully added!");
            }
            Panel1.setVisible(false);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    public void setValues() {
        ReceivingData receivingData = currentPlayer.getReceivingData();

        txtReceptions.setText(Integer.toString(receivingData.getReceptions()));
        txtCatchPerc.setText(Float.toString(receivingData.getCatchPerc()));
        txtYards.setText(Integer.toString(receivingData.getYards()));
        txtYardsPerRec.setText(Float.toString(receivingData.getYardsPerRec()));
        txtTouchdowns.setText(Integer.toString(receivingData.getTouchdowns()));
        txtYardsPerGame.setText(Float.toString(receivingData.getYardsPerGame()));
    }

}
