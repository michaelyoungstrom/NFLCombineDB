package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created on 12/6/16.
 */
public class PlayerForm extends JPanel {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtTeam;
    private JButton saveButton;
    private JPanel rootPanel;
    private OnButtonClickedListener onButtonClickedListener;
    private boolean buttonVisible = true;
    private String buttonText;

    private Player player;

    public PlayerForm(){
        super();
        initialize();
    }

    private void initialize() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onButtonClickedListener != null) {
                    onButtonClickedListener.onButtonClickedListener(getPlayer(), getRootPanel());
                }
            }
        });

        saveButton.setVisible(this.buttonVisible);
        if (buttonText != null) {
            saveButton.setText(buttonText);
        }
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        if (saveButton != null) {
            saveButton.setText(buttonText);
        }
    }

    public void setButtonVisible(boolean isVisible) {
        buttonVisible = isVisible;
        if (saveButton != null) {
            saveButton.setVisible(isVisible);
        }
    }

    public void setPlayer(Player player){
        txtFirstName.setText(player.getFirstName());
        txtFirstName.repaint();
        txtLastName.setText(player.getLastName());
        txtTeam.setText(player.getTeam());
    }

    public void setOnButtonClickedListener(OnButtonClickedListener clickedListener) {
        this.onButtonClickedListener = clickedListener;
    }

    public void removePlayer(){

    }

    public Player getPlayer(){
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String team = txtTeam.getText();
        String id = null;
        if (this.player != null && this.player.getPlayerId() != null) {
            id = player.getPlayerId();
        }

        return new Player(id, firstName, lastName, team);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public interface OnButtonClickedListener{
        void onButtonClickedListener(Player player, JPanel parent);
    }
}
