package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mayoungstrom on 12/6/16.
 */
public class passingInput extends JFrame{
    private JButton btnBack;
    private JButton btnOk;
    private JTextField txtCompPerc;
    private JTextField txtYards;
    private JTextField txtTouchdowns;
    private JTextField txtInterceptions;
    private JTextField txtRating;
    private JPanel Panel1;

    private Player currentPlayer;

    public passingInput(Player player) {
        super("Passing Input");
        this.currentPlayer = player;
        initialize();
    }

    /**
     * Initializes the LoginForm
     */
    private void initialize(){
        setContentPane(Panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    public void okClicked(){
        try{

            float compPerc = Float.parseFloat(txtCompPerc.getText());
            int yards = Integer.parseInt(txtYards.getText());
            int touchdowns = Integer.parseInt(txtTouchdowns.getText());
            int interceptions = Integer.parseInt(txtInterceptions.getText());
            float rating = Float.parseFloat(txtRating.getText());

            PlayersHelper.insertNewPassing
                    (DatabaseHelper.loginToDB(), currentPlayer, compPerc, yards, touchdowns, interceptions, rating);

            JOptionPane.showMessageDialog(this, "Passing Data Successfully added!");
            Panel1.setVisible(false);
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

}
