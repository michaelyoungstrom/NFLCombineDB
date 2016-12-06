package com.calderonyoungstrom.views;

import com.calderonyoungstrom.model.CombineData;
import com.calderonyoungstrom.model.PassingData;
import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.ReceivingData;
import com.calderonyoungstrom.model.RushingData;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mayoungstrom on 12/6/16.
 */
public class combineInput extends JFrame{
    private JButton btnBack;
    private JTextField txtHeight;
    private JTextField txtWeight;
    private JTextField txtForty;
    private JTextField txtTwenty;
    private JTextField txtThreecone;
    private JTextField txtVertical;
    private JTextField txtBroad;
    private JTextField txtBench;
    private JTextField txtCollege;
    private JButton btnOk;
    private JTextField txtYear;
    private JPanel Panel1;

    private Player currentPlayer;
    private boolean isUpdate;

    public combineInput(Player player, boolean isUpdate) {
        super("Combine Input");
        this.currentPlayer = player;
        this.isUpdate = isUpdate;
        initialize();
    }

    /**
     * Initializes the LoginForm
     */
    private void initialize(){
        setContentPane(Panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (isUpdate){
            setValues();
        }

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked");
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

            float height = Float.parseFloat(txtHeight.getText());
            int weight = Integer.parseInt(txtWeight.getText());

            float forty;
            if (!txtForty.getText().equals("")) {
                forty = Float.parseFloat(txtForty.getText());
            } else {
                forty = 0;
            }

            float twenty;
            if (!txtTwenty.getText().equals("")){
                twenty = Float.parseFloat(txtTwenty.getText());
            } else {
                twenty = 0;
            }

            float threecone;
            if (!txtThreecone.getText().equals("")){
                threecone = Float.parseFloat(txtThreecone.getText());
            } else {
                threecone = 0;
            }

            float vertical;
            if (!txtVertical.getText().equals("")){
                vertical = Float.parseFloat(txtVertical.getText());
            } else {
                vertical = 0;
            }

            int broad;
            if (!txtBroad.getText().equals("")){
                broad = Integer.parseInt(txtBroad.getText());
            } else {
                broad = 0;
            }

            int bench;
            if (!txtBench.getText().equals("")){
                bench = Integer.parseInt(txtBench.getText());
            } else {
                bench = 0;
            }

            String college = txtCollege.getText();
            int combineYear = Integer.parseInt(txtYear.getText());

            if (isUpdate) {
                PlayersHelper.updateCombine(DatabaseHelper.loginToDB(), currentPlayer, height, weight,
                        forty, twenty, threecone, vertical, broad, bench, college, combineYear);
            } else {
                PlayersHelper.insertNewCombine
                        (DatabaseHelper.loginToDB(), currentPlayer, height, weight,
                                forty, twenty, threecone, vertical, broad, bench, college, combineYear);
            }

            if (isUpdate){
                JOptionPane.showMessageDialog(this, "Combine Data successfully updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Combine Data successfully added!");
            }

            Panel1.setVisible(false);
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    public void setValues(){
        CombineData combineData = currentPlayer.getCombineData();
        txtHeight.setText(Float.toString(combineData.getHeight()));
        txtWeight.setText(Integer.toString(combineData.getWeight()));
        txtForty.setText(Float.toString(combineData.getForty()));
        txtTwenty.setText(Float.toString(combineData.getTwenty()));
        txtThreecone.setText(Float.toString(combineData.getThreecone()));
        txtVertical.setText(Float.toString(combineData.getVertical()));
        txtBroad.setText(Integer.toString(combineData.getBroad()));
        txtBench.setText(Integer.toString(combineData.getBench()));
        txtCollege.setText(combineData.getCollege());
        txtYear.setText(Integer.toString(combineData.getCombineYear()));
    }
}
