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
public class rushingInput extends JFrame{
    private JPanel Panel1;
    private JButton btnBack;
    private JTextField txtYards;
    private JTextField txtTouchdowns;
    private JTextField txtLongest;
    private JTextField txtYardsPerAtt;
    private JTextField txtYardsPerGame;
    private JButton btnOk;

    private Player currentPlayer;

    public rushingInput(Player player) {
        super("Rushing Input");
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

            int yards = Integer.parseInt(txtYards.getText());
            int touchdowns = Integer.parseInt(txtTouchdowns.getText());
            int longest = Integer.parseInt(txtLongest.getText());
            float yardsPerAttempt = Float.parseFloat(txtYardsPerAtt.getText());
            float yardsPerGame = Float.parseFloat(txtYardsPerGame.getText());

            PlayersHelper.insertNewRushing
                    (DatabaseHelper.loginToDB(), currentPlayer, yards, touchdowns, longest, yardsPerAttempt,
                            yardsPerGame);

            JOptionPane.showMessageDialog(this, "Rushing Data Successfully added!");
            Panel1.setVisible(false);
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }
}
