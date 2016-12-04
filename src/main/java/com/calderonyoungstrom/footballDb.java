package com.calderonyoungstrom;

import com.calderonyoungstrom.model.CombineData;
import com.calderonyoungstrom.model.PassingData;
import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.ReceivingData;
import com.calderonyoungstrom.model.RushingData;
import com.calderonyoungstrom.util.DatabaseHelper;
import com.calderonyoungstrom.util.PlayersHelper;
import com.calderonyoungstrom.views.LoginForm;
import com.calderonyoungstrom.views.SearchPlayer;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class footballDb {

    public static void main(String[] args) throws
            ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException {

        String username = "root";
        //ENTER PASSWORD HERE!!!!!
        String password = "Pass1234";

        Connection connection = DatabaseHelper.loginToDB(username, password);

        ArrayList<Player> players = PlayersHelper.findPlayersByName(connection, "Tom", "Brady");
        Player player = players.get(0);

        player.setPassingData(PlayersHelper.getPassingData(connection, player.getPlayerId()));
        System.out.print("Touchdowns: " + player.getPassingData().getTouchdowns());
//        LoginForm form = new LoginForm();
//        form.setVisible(true);
        SearchPlayer searchPlayer = new SearchPlayer();
        searchPlayer.setVisible(true);
    }
}
