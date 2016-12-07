package com.calderonyoungstrom;

import com.calderonyoungstrom.views.SearchPlayer;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

public class footballDb {

    public static void main(String[] args) throws
            ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException {
        try {
            UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SearchPlayer searchPlayer = new SearchPlayer();
        searchPlayer.setLocationRelativeTo(null);
        searchPlayer.setVisible(true);
    }
}
