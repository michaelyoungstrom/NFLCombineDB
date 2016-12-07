package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.CombineData;
import com.calderonyoungstrom.model.PassingData;
import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.ReceivingData;
import com.calderonyoungstrom.model.RushingData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created on 12/4/16.
 */
public class PlayerStats extends JFrame {
    private JPanel rootPanel;
    private JPanel panelCombinedData;
    private JPanel panelReceivingInfo;
    private JPanel panelPassingInfo;
    private JPanel panelRushingInfo;
    private JLabel txtCombinedPlayerHeight;
    private JLabel txtCombinedPlayerWeight;
    private JLabel txtCombinedPlayerForty;
    private JLabel txtCombinedPlayerTwenty;
    private JLabel txtCombinedPlayerVertical;
    private JLabel txtCombinedPlayerBroad;
    private JLabel txtCombinedPlayerBench;
    private JLabel txtCombinedPlayerCollege;
    private JLabel txtCombinedPlayerYear;
    private JLabel txtPassingCompletionPercentage;
    private JLabel lblYards;
    private JLabel txtReceivingReception;
    private JLabel txtReceivingCatchPercentage;
    private JLabel txtReceivingYards;
    private JLabel txtReceivingYardsPerReception;
    private JLabel txtReceivingReceivingInfoTouchdowns;
    private JLabel txtReceivingYardsPerGame;
    private JLabel txtRushYards;
    private JLabel txtRushTouchdowns;
    private JLabel txtRushLongest;
    private JLabel txtRushYardsPerAttempt;
    private JLabel txtRushYardsPerGame;
    private JLabel txtPassingYards;
    private JLabel txtPassingTouchdown;
    private JLabel txtPassingInterceptions;
    private JLabel txtPassingRating;
    private JButton btnBack;
    private JLabel threecone;
    private JLabel txtCombinedThreecone;

    private Player currentPlayer;

    public PlayerStats(Player player) {
        super(player.getFirstName() + " " + player.getLastName() + " stats");
        if (player == null) {
            throw new IllegalArgumentException("Player can't be null");
        }

        this.currentPlayer = player;

        initialize();
    }

    private void initialize() {
        setContentPane(rootPanel);
        pack();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootPanel.setVisible(false);
                dispose();
            }
        });

        if (currentPlayer.getCombineData() == null) {
            panelCombinedData.setVisible(false);
        } else {
            setCombinedData(currentPlayer.getCombineData());
        }

        if (currentPlayer.getReceivingData() == null) {
            panelReceivingInfo.setVisible(false);
        } else {
            setReceivingData(currentPlayer.getReceivingData());
        }

        if (currentPlayer.getPassingData() == null) {
            panelPassingInfo.setVisible(false);
        } else {
            setPassingData(currentPlayer.getPassingData());
        }

        if (currentPlayer.getRushingData() == null) {
            panelRushingInfo.setVisible(false);
        } else {
            setRushingData(currentPlayer.getRushingData());
        }
    }

    private void setRushingData(RushingData rushingData) {
        txtRushLongest.setText(rushingData.getLongest() + "");
        txtRushTouchdowns.setText(rushingData.getTouchdowns() + "");
        txtRushYards.setText(rushingData.getYards() + "");
        txtRushYardsPerAttempt.setText(rushingData.getYardsPerAttempt() + "");
        txtRushYardsPerGame.setText(rushingData.getYardsPerGame() + "");
    }

    private void setCombinedData(CombineData combinedData) {
        setValue(txtCombinedPlayerWeight, combinedData.getWeight(), false);
        setValue(txtCombinedPlayerHeight, combinedData.getHeight(), false);
        setValue(txtCombinedPlayerCollege, combinedData.getCollege(), false);
        setValue(txtCombinedPlayerForty, combinedData.getForty(), true);
        setValue(txtCombinedPlayerTwenty, combinedData.getTwenty(), true);
        setValue(txtCombinedThreecone, combinedData.getThreecone(), true);
        setValue(txtCombinedPlayerVertical, combinedData.getVertical(), true);
        setValue(txtCombinedPlayerBroad, combinedData.getBroad(), true);
        setValue(txtCombinedPlayerBench, combinedData.getBench(), true);
        setValue(txtCombinedPlayerYear, combinedData.getCombineYear(), true);
    }

    private void setReceivingData(ReceivingData receivingData) {
        setValue(txtReceivingReception, receivingData.getReceptions(), false);
        setValue(txtReceivingCatchPercentage, receivingData.getCatchPerc(), false);
        setValue(txtReceivingYards, receivingData.getYards(), false);
        setValue(txtReceivingYardsPerReception, receivingData.getYardsPerRec(), false);
        setValue(txtReceivingReceivingInfoTouchdowns, receivingData.getTouchdowns(), false);
        setValue(txtReceivingYardsPerGame, receivingData.getYardsPerGame(), false);
    }

    private void setPassingData(PassingData passingData) {
        setValue(txtPassingCompletionPercentage, passingData.getCompPerc(), false);
        setValue(txtPassingYards, passingData.getYard(), false);
        setValue(txtPassingTouchdown, passingData.getTouchdowns(), false);
        setValue(txtPassingInterceptions, passingData.getInterceptions(), false);
        setValue(txtPassingRating, passingData.getRating(), false);
    }

    private void setValue(JLabel lbl, String value, boolean naIfEmpty) {
        if (value != null && !value.isEmpty()) {
            lbl.setText(value);
        } else {
            if (naIfEmpty) {
                lbl.setText("N/A");
            } else {
                lbl.setText("");
            }
        }
    }

    private void setValue(JLabel lbl, int value, boolean naIfEmpty) {
        if (naIfEmpty && value <= 0) {
            setValue(lbl, "", true);
        } else {
            setValue(lbl, value + "", naIfEmpty);
        }
    }

    private void setValue(JLabel lbl, float value, boolean naIfEmpty) {
        if (naIfEmpty && value <= 0) {
            setValue(lbl, "", true);
        } else {
            setValue(lbl, value + "", naIfEmpty);
        }
    }

}
