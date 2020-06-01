// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Entry implements Model {
    /**
     * Class for creating Entries/ querying entry table
     */

    UUID id;
    UUID itemId;
    String userId;
    double amt;
    int units;
    Calendar date;

    private static PreparedStatement createNewEntryStatement;
    private static PreparedStatement getByItemAfterDateStatement;
    private static PreparedStatement getByUserInDateRangeStatement;
    private static PreparedStatement getByUserAfterDateStatement;

    public Entry(Item item, User user, double amt, int units) {
        this.id = UUID.randomUUID();
        this.itemId = item.id;
        this.userId = user.username;
        this.amt = amt;
        this.units = units;
        this.date = Calendar.getInstance();
    }

    private Entry(UUID id, UUID itemId, String userId, double amt, int units, Calendar date) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.amt = amt;
        this.units = units;
        this.date = date;
    }

    /**
     * prepare all entry table statements.
     */
    static public void prepareStatements() {
        try {
            createNewEntryStatement = connection.prepareStatement("insert into entry values (?, ?, ?, ?, ?, ?);");
            getByItemAfterDateStatement = connection.prepareStatement("select * from entry where itemId = ? and date >= ?;");
            getByUserAfterDateStatement = connection.prepareStatement("select * from entry where userId = ? and date >= ? order by date desc;");
            getByUserInDateRangeStatement = connection.prepareStatement("select * from entry where userId = ? and date between ? and ?;");
        } catch (SQLException e) {
            System.out.println("Something went wrong preparing statements for Entry table.");
        }
    }

    // Database interactions

    @Override
    public void save() {
        try {
            createNewEntryStatement.setString(1, id.toString());
            createNewEntryStatement.setString(2, itemId.toString());
            createNewEntryStatement.setString(3, userId);
            createNewEntryStatement.setDouble(4, amt);
            createNewEntryStatement.setInt(5, units);
            createNewEntryStatement.setDate(6, new java.sql.Date(date.getTimeInMillis()));
            createNewEntryStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong saving entry with amount: " + amt + " and units: " + units);
        }
    }

    /**
     * get all entries from db owned by given item occurring after given date
     */
    protected static List<Entry> getByItemAfterDate(Item item, Calendar date) {
        try {
            getByItemAfterDateStatement.setString(1, item.getId().toString());
            getByItemAfterDateStatement.setDate(2, new java.sql.Date(date.getTimeInMillis()));
            ResultSet result = getByItemAfterDateStatement.executeQuery();
            return resultSetToList(result);
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get Entries by item");
        }

        return null;
    }

    /**
     * get all entries from db owned by given user occurring after given date.
     */
    protected static List<Entry> getByUserAfterDate(User user, Calendar date) {
        try {
            getByUserAfterDateStatement.setString(1, user.getUsername());
            getByUserAfterDateStatement.setDate(2, new java.sql.Date(date.getTimeInMillis()));
            ResultSet result = getByUserAfterDateStatement.executeQuery();
            return resultSetToList(result);
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get Entries by User after date");
        }

        return null;
    }

    /**
     * get all entries from db owned by given user occurring between given dates.
     */
    protected static List<Entry> getByUserInDateRange(User user, Calendar date1, Calendar date2) {
        try {
            getByUserInDateRangeStatement.setString(1, user.getUsername());
            getByUserInDateRangeStatement.setDate(2, new java.sql.Date(date1.getTimeInMillis()));
            getByUserInDateRangeStatement.setDate(3, new java.sql.Date(date2.getTimeInMillis()));
            ResultSet result = getByUserInDateRangeStatement.executeQuery();
            return resultSetToList(result);
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get Entries by User in between dates");
        }

        return null;
    }

    // Getters/Setters

    public UUID getId() {
        return id;
    }

    public Item getItem() {
        return Item.getById(itemId);
    }

    public double getAmt() {
        return amt;
    }

    public int getUnits() {
        return units;
    }

    public Calendar getDate() {
        return (Calendar) date.clone();
    }

    // Only for demo purposes
    public void setDate(Calendar date) {
        this.date = date;
    }

    // Utility methods

    private static List<Entry> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Entry> list = new ArrayList<>();

        while (resultSet.next()) {
            UUID id = UUID.fromString(resultSet.getString("id"));
            UUID itemId = UUID.fromString(resultSet.getString("itemId"));
            String username = resultSet.getString("userId");
            double amt = resultSet.getDouble("amount");
            int units = resultSet.getInt("units");
            Calendar date = Calendar.getInstance();
            date.setTime(resultSet.getDate("date"));

            list.add(new Entry(id, itemId, username, amt, units, date));
        }

        return list;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Entry))
            return false;
        return ((Entry) o).getId().equals(getId());
    }

    @Override
    public String toString() {
        return String.format("<Entry amount: %f units: %d>", amt, units);
    }
}
