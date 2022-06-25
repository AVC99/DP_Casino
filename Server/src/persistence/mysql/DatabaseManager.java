package persistence.mysql;

import model.users.*;
import persistence.AuthenticationDAO;
import persistence.ServerConfigurationDAO;
import persistence.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager implements AuthenticationDAO, UserDAO {
    private Connection connection;

    public DatabaseManager(ServerConfigurationDAO serverConfigurationDAO) {
        this.connection = MySQLConnector.getInstance(serverConfigurationDAO.getDatabaseURL(),
                serverConfigurationDAO.getDatabaseUsername(), serverConfigurationDAO.getDatabasePassword());
    }

    @Override
    public Boolean authenticateCredentials(UserSession userSession) {
        Boolean validCredentials = false;

        // Get user with introduced username and password
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            statement.setString(1, userSession.getUsername());
            statement.setString(2, userSession.getPassword());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                validCredentials = true;
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Cannot authenticate credentials { username: " + userSession.getUsername() +
                    ", password: " + userSession.getPassword() + " }");
        }

        return validCredentials;
    }

    public Boolean registerCredentials(UserSession userSession) {
        Boolean validRegister = true;

        try {
            // Check if exists a user with the introduced username
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            statement.setString(1, userSession.getUsername());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                validRegister = false;
            }

            // If it does not exist a user with the introduced username, register the new user
            if (validRegister) {
                statement = this.connection.prepareStatement(
                        "INSERT INTO users (username, password) VALUES (?, ?)"
                );
                statement.setString(1, userSession.getUsername());
                statement.setString(2, userSession.getPassword());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Cannot register credentials { username: " + userSession.getUsername() +
                    ", password: " + userSession.getPassword() + " }");
        }

        return validRegister;
    }


    @Override
    public User getUser(String username) {
        User user = null;

        // Get user from database
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                user = new User(result.getString("username"), result.getInt("total_balance"),
                        result.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Cannot get the user " + username + " from the database");
        }

        return user;
    }

    public ArrayList<Integer> getHistoryBalance(String username) {
        ArrayList<Integer> balanceHistory = new ArrayList<>();

        // todo group by date
        //use to group by date
        java.util.Date lastDate = new java.util.Date();
        System.out.println(lastDate.getTime());
        // Get balance history from database
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM balance_history WHERE username = ?"
            );
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                balanceHistory.add(result.getInt("user_balance"));
            }

        } catch (SQLException e) {
            System.out.println("ERROR: Cannot get the balance history from the database for the user " + username);
        }

        return balanceHistory;
    }

    public void addFunds(Guest user) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE users SET total_balance= ?  WHERE username = ?"
            );
            statement.setInt(1, user.getBalance());
            statement.setString(2, user.getUsername());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR: Cannot add funds to the database for the user " + user.getUsername());
        }
    }

    public void addFundsToBalanceHistory(Guest user) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO balance_history ( date, total_balance, username)  VALUES (?,?,?)"
            );
            java.util.Date currentDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            statement.setDate(1, sqlDate);
            statement.setInt(2, user.getBalance());
            statement.setString(3, user.getUsername());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR: Cannot add funds to the history database for the user " + user.getUsername());
        }
    }

    public void modifyUserBalance(GameResult gameResult) {
        try {
           /* PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT SUM(total_balance) FROM balance_history  WHERE username = ?"
            );
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();*/

            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE users SET total_balance=  ?  WHERE username = ?"
            );
            statement.setInt(1, gameResult.getUser().getBalance());
            System.out.println(gameResult.getUser().getBalance());
            statement.setString(2, gameResult.getUsername());
            System.out.println(gameResult.getUsername());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR: Cannot add funds to the database for the user " + gameResult.getUsername());
        }
    }

    public void addGameToMatchHistory(String gameType, Boolean isVictory, String username, int amount){
        try{
            PreparedStatement statement= this.connection.prepareStatement(
                    "INSERT INTO match_history ( game_type, is_victory, match_date, username, amount) " +
                            " VALUES (?,?,NOW(),?,?)"
            );
            statement.setString(1, gameType);
            statement.setBoolean(2, isVictory);
            statement.setString(3, username);
            statement.setInt(4, amount);
            statement.executeUpdate();

        }catch(SQLException e){
            System.out.println("ERROR: Cannot add game to the match history database for the user " + username);
        }
    }
    public void addRegisterToBalanceHistory(String username , int amount){
        try{
            PreparedStatement statement= this.connection.prepareStatement(
                    "INSERT INTO balance_history ( date, total_balance, username) " +
                            " VALUES (NOW(),?,?)"
            );
            statement.setInt(1, amount);
            statement.setString(2, username);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("ERROR: Cannot add register to the balance history database for the user " + username);
        }
    }


    public Map<String, Integer> getRankingByGameType(String gameType) {
        Map<String, Integer> ranking = new HashMap<>();

        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT DISTINCT username, (SELECT sum(amount) FROM match_history WHERE username LIKE mh.username AND is_victory = 1 AND game_type LIKE ?) -\n" +
                            "           (SELECT sum(amount) FROM match_history WHERE username LIKE mh.username AND is_victory = 0 AND game_type LIKE ?) AS total FROM match_history AS mh ORDER BY total DESC;"
            );
            statement.setString(1, gameType);
            statement.setString(2, gameType);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                ranking.put(result.getString("username"), result.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Cannot get the ranking for the game type " + gameType);
        }

        return ranking;
    }

    public void updateDatabase(GameResult gameResult){
        addRegisterToBalanceHistory(gameResult.getUsername(),gameResult.getAmount());
        addGameToMatchHistory(gameResult.getGameType(), gameResult.getIsVictory(), gameResult.getUsername(),
                gameResult.getAmount());
        modifyUserBalance(gameResult);

    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR: Database connection could not be closed");
        }
    }
}