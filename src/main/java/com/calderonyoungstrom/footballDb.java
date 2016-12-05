package com.calderonyoungstrom;

import com.calderonyoungstrom.model.CombineData;
import com.calderonyoungstrom.model.PassingData;
import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.ReceivingData;
import com.calderonyoungstrom.model.RushingData;
import com.calderonyoungstrom.views.LoginForm;
import com.calderonyoungstrom.views.SearchPlayer;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class footballDb {

    public static void main(String[] args) throws
            ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException {

        SearchPlayer searchPlayer = new SearchPlayer();
        searchPlayer.setVisible(true);
    }
}
