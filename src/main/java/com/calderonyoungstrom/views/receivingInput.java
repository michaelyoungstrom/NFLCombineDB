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
public class receivingInput extends JFrame{
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

    public receivingInput(Player player) {
        super("Receiving Input");
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

            int receptions = Integer.parseInt(txtReceptions.getText());
            float catchPerc = Float.parseFloat(txtCatchPerc.getText());
            int yards = Integer.parseInt(txtYards.getText());
            float yardsPerRec = Float.parseFloat(txtYardsPerRec.getText());
            int touchdowns = Integer.parseInt(txtTouchdowns.getText());
            float yardsPerGame = Float.parseFloat(txtYardsPerGame.getText());

            PlayersHelper.insertNewReceiving
                    (DatabaseHelper.loginToDB(), currentPlayer, receptions, catchPerc, yards, yardsPerRec,
                            touchdowns, yardsPerGame);

            JOptionPane.showMessageDialog(this, "Receiving Data Successfully added!");
            Panel1.setVisible(false);
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }
}
