// MADE BY: Jacob Hanson-Regalado

package trackit.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class User implements Model {
    /**
     * Class for creating users & querying user table.
     */

    String username;
    String password;
    Calendar date;

    private static PreparedStatement newUserStatement;
    private static PreparedStatement getUserByIdStatement;
    private static PreparedStatement updateUserStatement;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date = Calendar.getInstance();
    }

    private User(String username, String password, Calendar date) {
        this.username = username;
        this.password = password;
        this.date = date;
    }

    /**
     * Prepare all statements for user table.
     */
    static public void prepareStatements() {
        try {
            newUserStatement = connection.prepareStatement("insert into user values (?, ?, ?);");
            getUserByIdStatement = connection.prepareStatement("select * from user where username = ?;");
            updateUserStatement = connection.prepareStatement("update user set username = ?, password = ? where username = ?");
        } catch (SQLException e) {
            System.out.println("Something went wrong preparing user statements.");
        }
    }

    /**
     * Utility method for querying Entry table for entries pertaining to this user between 2 dates.
     * @param start first valid date
     * @param end last valid date
     * @return list of all Entries between dates that are owned by this user.
     */
    public List<Entry> getEntriesInRange(Calendar start, Calendar end) {
        return Entry.getByUserInDateRange(this, start, end);
    }

    /**
     * Utility method for getting entries after certain date owned by this user.
     * @param sinceDate first valid date
     * @return list of all entries owned by this user occurring after specified date.
     */
    public List<Entry> getEntriesAfterDate(Calendar sinceDate) {
        return Entry.getByUserAfterDate(this, sinceDate);
    }

    // Database interactions

    /**
     * Save user into table.
     */
    @Override
    public void save() {
        try {
            newUserStatement.setString(1, username);
            newUserStatement.setString(2, password);
            newUserStatement.setDate(3, new Date(date.getTimeInMillis()));
            newUserStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong saving user with name: " + username);
            e.printStackTrace();
        }
    }

    /**
     * get user by username.
     * @param username username to look for
     * @return User from username
     */
    public static User getById(String username) {
        try {
            getUserByIdStatement.setString(1, username);
            ResultSet resultSet = getUserByIdStatement.executeQuery();
            if (resultSet.next()) {
                Calendar date = Calendar.getInstance();
                date.setTime(resultSet.getDate("date"));
                return new User(resultSet.getString("username"), resultSet.getString("password"), date);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong getting user by id with username: " + username);
        }

        return null;
    }

    /**
     * update current user's username.
     * @param username new username.
     * @return 1 for success -1 for failure.
     */
    public int updateUsername(String username) {
        try {
            updateUserStatement.setString(1, username);
            updateUserStatement.setString(2, password);
            updateUserStatement.setString(3, this.username);
            updateUserStatement.executeUpdate();
        }
        catch (SQLException e) {
            return -1;
        }
        this.username = username;
        return 1;
    }

    /**
     * Update user's password.
     * @param password new password
     * @return 1 for sucess -1 for failure
     */
    public int updatePassword(String password) {
        try {
            updateUserStatement.setString(1, username);
            updateUserStatement.setString(2, password);
            updateUserStatement.setString(3, username);
            updateUserStatement.executeUpdate();
        }
        catch (SQLException e) {
            return -1;
        }
        this.password = password;
        return -1;
    }

    // Getters/Setters

    public String getUsername() {
        return username;
    }

    public Calendar getDate() {
        return (Calendar) date.clone();
    }

    public List<Item> getItems() {
        return Item.getByUser(this);
    }

    public boolean equals(Object o) {
        if (!(o instanceof User))
            return false;
        return ((User) o).getUsername().equals(getUsername());
    }
}
