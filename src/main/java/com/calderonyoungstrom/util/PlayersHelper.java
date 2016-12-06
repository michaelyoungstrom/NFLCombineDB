package com.calderonyoungstrom.util;

import com.calderonyoungstrom.model.CombineData;
import com.calderonyoungstrom.model.PassingData;
import com.calderonyoungstrom.model.Player;
import com.calderonyoungstrom.model.ReceivingData;
import com.calderonyoungstrom.model.RushingData;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created on 12/4/16.
 */
public class PlayersHelper {
    /**
     * Returns a list of players associated with the given first name and/or last name
     * @param connection the connection
     * @param fname the first name
     * @param lname the last name
     * @return a list of results
     * @throws IOException
     * @throws SQLException
     */
    public static ArrayList<Player> findPlayersByName(Connection connection, String fname, String lname) throws
            IOException, SQLException {
        //Find all Players that match the desired player fname and lname
        String query = "SELECT * FROM players";
        String fNameValue = "";
        String lNameValue = "";

        if (fname != null && fname.length() > 0) {
            query += " WHERE fname LIKE ?";
            fNameValue = "%" + fname + "%";
        }

        if (lname != null && lname.length() > 0) {
            lNameValue = "%" + lname + "%";

            if (!fNameValue.isEmpty()) {
                query += " AND lname LIKE ?";
            } else {
                query += " WHERE lname LIKE ?";
            }
        }

        PreparedStatement st = connection.prepareStatement(query);

        if (!fNameValue.isEmpty() && !lNameValue.isEmpty()) {
            st.setString(1, fNameValue);
            st.setString(2, lNameValue);
        } else if (!fNameValue.isEmpty()) {
            st.setString(1, fNameValue);
        } else {
            st.setString(1, lNameValue);
        }


        System.out.println(st);

        ResultSet rs = st.executeQuery();

        String playerId; String firstName; String lastName; String team;

        ArrayList<Player> players = new ArrayList<Player>();

        while (rs.next()) {
            //get each column
            playerId = rs.getString("playerId");
            firstName = rs.getString("fname");
            lastName = rs.getString("lname");
            team = rs.getString("team");
            //if desired player, save data
            Player newPlayer = new Player(playerId, firstName, lastName, team);
            players.add(newPlayer);
        }
        st.close();

        return players;
    }

    public static String generatePlayerId(Connection connection, String fname, String lname) throws SQLException{

        createProcedureFindPlayerById(connection);

        int lnameLength = lname.length();
        int fnameLength = fname.length();
        String playerId = "";

        if (lnameLength >= 4){
            playerId = playerId + lname.substring(0,4);
        } else {
            playerId = playerId + lname;
        }

        if (fnameLength >= 2){
            playerId = playerId + fname.substring(0,2);
        } else {
            playerId = playerId + fname;
        }

        int num = 0;
        boolean generated = false;

        while (!generated){

            CallableStatement cs = connection.prepareCall("{call FINDPLAYERBYID(?)}");
            cs.setString(1, playerId + Integer.toString(num));
            ResultSet rs = cs.executeQuery();

            ArrayList<String> players = new ArrayList<String>();
            while (rs.next()) {
                String character = rs.getString("playerId");
                players.add(character);
            }

            if (players.size() == 0){
                generated = true;
                playerId = playerId + Integer.toString(num);
            } else {
                num++;
            }
        }

        return playerId;

    }

    public static void insertNewPlayer(Connection connection, String fname, String lname, String team)
            throws SQLException {
        createProcedureInsertNewPlayer(connection);
        String playerId = generatePlayerId(connection, fname, lname);

        CallableStatement cs = connection.prepareCall("{call INSERTNEWPLAYER(?,?,?,?)}");
        cs.setString(1, playerId);
        cs.setString(2, fname);
        cs.setString(3, lname);
        cs.setString(4, team);
        cs.executeQuery();
    }

    public static void deletePlayer(Connection connection, String playerId)
            throws SQLException, IOException {

        createProcedureDeletePlayer(connection);
        CallableStatement cs = connection.prepareCall("{call DELETEPLAYER(?)}");
        cs.setString(1, playerId);
        ResultSet rs2 = cs.executeQuery();
    }

    public static void updatePlayer(Connection connection, Player player, String newFname,
                                    String newLname, String newTeamName) throws SQLException, IOException {
        createProcedureUpdatePlayer(connection);

        updatePassing(connection, player, (float) 64.4, 4770, 36, 7, (float) 102.2);

        CallableStatement cs = connection.prepareCall("{call UPDATEPLAYER (?,?,?,?)}");
        cs.setString(1, player.getPlayerId());
        cs.setString(2, newFname);
        cs.setString(3, newLname);
        cs.setString(4, newTeamName);
        //ResultSet rs2 = cs.executeQuery();
    }

    public static void updateCombine(Connection connection, Player player, float height, int weight, float forty,
                                     float twenty, float threecone, float vertical, int broad, int bench,
                                     String college, int combineYear) throws SQLException{
        createProcedureUpdateCombine(connection);

        CallableStatement cs = connection.prepareCall("{call UPDATECOMBINE (?,?,?,?,?,?,?,?,?,?,?)}");
        cs.setString(1, player.getPlayerId());
        cs.setFloat(2, height);
        cs.setInt(3, weight);
        cs.setFloat(4, forty);
        cs.setFloat(5, twenty);
        cs.setFloat(6, threecone);
        cs.setFloat(7, vertical);
        cs.setInt(8, broad);
        cs.setInt(9, bench);
        cs.setString(10, college);
        cs.setInt(11, combineYear);
        ResultSet rs2 = cs.executeQuery();
    }

    public static void updatePassing(Connection connection, Player player, float compPerc, int yards,
                                     int touchdowns, int interceptions, float rating) throws SQLException {
        createProcedureUpdatePassing(connection);

        CallableStatement cs = connection.prepareCall("{call UPDATEPASSING (?,?,?,?,?,?)}");
        cs.setString(1, player.getPlayerId());
        cs.setFloat(2, compPerc);
        cs.setInt(3, yards);
        cs.setInt(4, touchdowns);
        cs.setInt(5, interceptions);
        cs.setFloat(6, rating);
        ResultSet rs2 = cs.executeQuery();
    }

    public static void getCombineData(Connection connection, Player player) throws SQLException {

        //Create Procedure
        createProcedureGetCombineData(connection);

        String playerId = player.getPlayerId();

        CallableStatement cs = connection.prepareCall("{call GETCOMBINEDATA(?)}");
        cs.setString(1, playerId);
        ResultSet rs2 = cs.executeQuery();

        float height; int weight; float forty; float twenty; float threecone; float vertical;
        int broad; int bench; String college; int year;

        while (rs2.next()) {
            year = rs2.getInt("combineYear");
            height = rs2.getFloat("height");
            weight = rs2.getInt("weight");
            forty = rs2.getFloat("forty");
            twenty = rs2.getFloat("twenty");
            threecone = rs2.getFloat("threecone");
            vertical = rs2.getFloat("vertical");
            broad = rs2.getInt("broad");
            bench = rs2.getInt("bench");
            college = rs2.getString("college");

            String combineId = playerId + "Combine";

            CombineData combine = new CombineData(combineId, playerId, height, weight, forty, twenty, threecone,
                    vertical, broad, bench, college, year);
            player.setCombineData(combine);
        }
    }

    public static void getPassingData(Connection connection, Player player) throws SQLException {

        createProcedureGetPassingData(connection);

        String playerId = player.getPlayerId();

        CallableStatement cs = connection.prepareCall("{call GETPASSINGDATA(?)}");
        cs.setString(1, playerId);
        ResultSet rs2 = cs.executeQuery();

        float compPerc; int yards; int touchdowns;
        int interceptions; float rating;
        while (rs2.next()) {
            compPerc = rs2.getFloat("compPerc");
            yards = rs2.getInt("yards");
            touchdowns = rs2.getInt("touchdowns");
            interceptions =  rs2.getInt("interceptions");
            rating = rs2.getFloat("rating");

            String passingId = playerId + "Passing";

            PassingData passingData = new PassingData(passingId, playerId, compPerc, yards, touchdowns, interceptions,
                    rating);
            player.setPassingData(passingData);
        }
    }

    public static void getRushingData(Connection connection, Player player) throws SQLException {

        createProcedureGetRushingData(connection);

        String playerId = player.getPlayerId();

        CallableStatement cs = connection.prepareCall("{call GETRUSHINGDATA(?)}");
        cs.setString(1, playerId);
        ResultSet rs2 = cs.executeQuery();

        int yards; int touchdowns; int longest;
        float yardsPerAttempt; float yardsPerGame;

        while (rs2.next()) {
            yards = rs2.getInt("yards");
            touchdowns = rs2.getInt("touchdowns");
            longest = rs2.getInt("longest");
            yardsPerAttempt = rs2.getFloat("yardsPerAttempt");
            yardsPerGame = rs2.getFloat("yardsPerGame");

            String rushingId = playerId + "Rushing";

            RushingData rushingData = new RushingData(rushingId, playerId, yards, touchdowns, longest, yardsPerAttempt,
                    yardsPerGame);
            player.setRushingData(rushingData);
        }
    }

    public static void getReceivingData(Connection connection, Player player) throws SQLException {

        createProcedureGetReceivingData(connection);

        String playerId = player.getPlayerId();

        CallableStatement cs = connection.prepareCall("{call GETRECEIVINGDATA(?)}");
        cs.setString(1, playerId);
        ResultSet rs2 = cs.executeQuery();

        int receptions; float catchPerc; int yards;
        float yardsPerRec; int touchdowns; float yardsPerGame;

        while (rs2.next()) {
            receptions = rs2.getInt("receptions");
            yards = rs2.getInt("yards");
            touchdowns = rs2.getInt("touchdowns");
            catchPerc =  rs2.getFloat("catchPerc");
            yardsPerRec = rs2.getFloat("yardsPerRec");
            yardsPerGame = rs2.getFloat("yardsPerGame");

            String receivingId = playerId + "Receiving";

            ReceivingData receivingData = new ReceivingData(receivingId, playerId, receptions, catchPerc, yards,
                    yardsPerRec, touchdowns, yardsPerGame);
            player.setReceivingData(receivingData);
        }
    }

    public static void createProcedureUpdateCombine(Connection connection) throws SQLException{

        String drop =
                "DROP PROCEDURE IF EXISTS UPDATECOMBINE";

        String createProcedure =
                "CREATE PROCEDURE updateCombine(" +
                        "IN playerIdIn VARCHAR(10), " +
                        "IN heightIn DECIMAL(3,1), " +
                        "IN weightIn INT, " +
                        "IN fortyIn DECIMAL(3,2), " +
                        "IN twentyIn DECIMAL(3,2), " +
                        "IN threeconeIn DECIMAL(3,2), " +
                        "IN verticalIn DECIMAL(3,1), " +
                        "IN broadIn INT, " +
                        "IN benchIn INT, " +
                        "IN collegeIn VARCHAR(30), " +
                        "IN combineYearIn INT) " +
                        "BEGIN " +
                        "UPDATE combineData SET " +
                        "height = heightIn, " +
                        "weight = weightIn, " +
                        "forty = fortyIn, " +
                        "twenty = twentyIn, " +
                        "threecone = threeconeIn, " +
                        "vertical = verticalIn, " +
                        "broad = broadIn, " +
                        "bench = benchIn, " +
                        "college = collegeIn, " +
                        "combineYear = combineYearIn " +
                        "WHERE playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureUpdatePassing(Connection connection) throws SQLException{

        String drop =
                "DROP PROCEDURE IF EXISTS UPDATEPASSING";

        String createProcedure =
                "CREATE PROCEDURE updatePassing(" +
                        "IN playerIdIn VARCHAR(10), " +
                        "IN compPercIn FLOAT, " +
                        "IN yardsIn INT, " +
                        "IN touchdownsIn INT, " +
                        "IN interceptionsIn INT, " +
                        "IN ratingIn float) " +
                        "BEGIN " +
                        "UPDATE passingInfo SET " +
                        "compPerc = compPercIn, " +
                        "yards = yardsIn, " +
                        "touchdowns = touchdownsIn, " +
                        "interceptions = interceptionsIn, " +
                        "rating = ratingIn " +
                        "WHERE playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureUpdatePlayer(Connection connection) throws SQLException{

        String drop =
                "DROP PROCEDURE IF EXISTS UPDATEPLAYER";

        String createProcedure =
                "CREATE PROCEDURE updatePlayer(" +
                        "IN playerIdIn VARCHAR(10), " +
                        "IN fnameIn VARCHAR(50), " +
                        "IN lnameIn VARCHAR(50), " +
                        "IN teamIn VARCHAR(3)) " +
                        "BEGIN " +
                        "UPDATE players SET " +
                        "fname = fnameIn, " +
                        "lname = lnameIn, " +
                        "team = teamIn " +
                        "WHERE playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureInsertNewPlayer(Connection connection) throws SQLException{

        String drop =
                "DROP PROCEDURE IF EXISTS INSERTNEWPLAYER";

        String createProcedure =
                "CREATE PROCEDURE insertNewPlayer(" +
                        "IN playerIdIn VARCHAR(10), " +
                        "IN fnameIn VARCHAR(50), " +
                        "IN lnameIn VARCHAR(50), " +
                        "IN teamIn VARCHAR(3)) " +
                        "BEGIN " +
                        "INSERT INTO players (playerId, fname, lname, team) " +
                        "VALUES (playerIdIn, fnameIn, lnameIn, teamIn); " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureDeletePlayer(Connection connection) throws SQLException{

        String drop =
                "DROP PROCEDURE IF EXISTS DELETEPLAYER";

        String createProcedure =
                "CREATE PROCEDURE deletePlayer(" +
                        "IN playerIdIn VARCHAR(10)) " +
                        "BEGIN " +
                        "DELETE FROM players " +
                        "WHERE playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureGetCombineData(Connection connection)
            throws SQLException {

        String drop =
                "DROP PROCEDURE IF EXISTS GETCOMBINEDATA";

        String createProcedure =
                "CREATE PROCEDURE getCombineData(" +
                        "IN playerIdIn VARCHAR(10)) " +
                        "BEGIN " +
                        "SELECT * FROM players p " +
                        "JOIN combineData c ON p.playerId = c.playerId " +
                        "WHERE p.playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureGetPassingData(Connection connection)
            throws SQLException {

        String drop =
                "DROP PROCEDURE IF EXISTS GETPASSINGDATA";

        String createProcedure =
                "CREATE PROCEDURE getPassingData(" +
                        "IN playerIdIn VARCHAR(21)) " +
                        "BEGIN " +
                        "SELECT * FROM passingInfo p " +
                        "WHERE p.playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureGetRushingData(Connection connection)
            throws SQLException {

        String drop =
                "DROP PROCEDURE IF EXISTS GETRUSHINGDATA";

        String createProcedure =
                "CREATE PROCEDURE getRushingData(" +
                        "IN playerIdIn VARCHAR(21)) " +
                        "BEGIN " +
                        "SELECT * FROM rushingInfo r " +
                        "WHERE r.playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureGetReceivingData(Connection connection)
            throws SQLException {

        String drop =
                "DROP PROCEDURE IF EXISTS GETRECEIVINGDATA";

        String createProcedure =
                "CREATE PROCEDURE getReceivingData(" +
                        "IN playerIdIn VARCHAR(23)) " +
                        "BEGIN " +
                        "SELECT * FROM receivingInfo r " +
                        "WHERE r.playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }

    public static void createProcedureFindPlayerById(Connection connection) throws SQLException {

        String drop =
                "DROP PROCEDURE IF EXISTS FINDPLAYERBYID";

        String createProcedure =
                "CREATE PROCEDURE findPlayerById(" +
                        "IN playerIdIn VARCHAR(10)) " +
                        "BEGIN " +
                        "SELECT * FROM players p " +
                        "WHERE p.playerId = playerIdIn; " +
                        "END";

        Statement stmtDrop = connection.createStatement();
        stmtDrop.execute(drop);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(createProcedure);
    }


}
