// MADE BY: Jacob Hanson-Regalado

package trackit.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Item implements Model {
    /**
     * Class for creating Items & querying item table.
     */

    UUID id;
    String userId;
    String name;
    Calendar date;
    double price;

    private static PreparedStatement newItemStatement;
    private static PreparedStatement getByUserStatement;
    private static PreparedStatement getByIdStatement;
    private static PreparedStatement updateItemStatement;

    public Item(String name, User user, Double price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.userId = user.username;
        this.price = price;
        this.date = Calendar.getInstance();
    }

    private Item(UUID id, String userId, String name, double price, Calendar date) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.price = price;
    }

    static public void prepareStatements() {
        try {
            newItemStatement = connection.prepareStatement("insert into item values (?, ?, ?, ?, ?);");
            getByUserStatement = connection.prepareStatement("select * from item where userId = ?;");
            getByIdStatement = connection.prepareStatement("select * from item where id = ?;");
            updateItemStatement = connection.prepareStatement("update item set name = ?, price = ? where id = ?;");
        } catch (SQLException e) {
            System.out.println("Something went wrong preparing item's statements.");
        }
    }

    public static Item getByUserAndName(User user, String name) {
        List<Item> items = Item.getByUser(user);
        for (Item item : items) {
            if (item.getName().equals(name))
                return item;
        }

        return null;
    }

    public List<Entry> getEntriesAfterDate(Calendar sinceDate) {
        return Entry.getByItemAfterDate(this, sinceDate);
    }

    //Database interactions

    @Override
    public void save() {
        try {
            newItemStatement.setString(1, id.toString());
            newItemStatement.setString(2, userId);
            newItemStatement.setString(3, name);
            newItemStatement.setDouble(4, price);
            newItemStatement.setDate(5, new Date(date.getTimeInMillis()));
            newItemStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong saving item with name: " + name);
            e.printStackTrace();
        }
    }

    /**
     * get all items owned by given user from db.
     */
    public static List<Item> getByUser(User user) {
        List<Item> list = new ArrayList<>();
        try {
            getByUserStatement.setString(1, user.username);
            ResultSet resultSet = getByUserStatement.executeQuery();


            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String userId = resultSet.getString("userId");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Calendar date = Calendar.getInstance();
                date.setTime(resultSet.getDate("date"));

                list.add(new Item(id, userId, name, price, date));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong getting items by user with name: " + user.username);
        }

        return list;
    }

    /**
     * get item by given UUID from db.
     */
    public static Item getById(UUID id) {
        try {
            getByIdStatement.setString(1, id.toString());
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Calendar date = Calendar.getInstance();
                date.setTime(resultSet.getDate("date"));

                return new Item(id, userId, name, price, date);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in get by id.");
        }
        return null;
    }

    /**
     * update this item's name.
     */
    public int updateName(String name) {
        try {
            updateItemStatement.setString(1, name);
            updateItemStatement.setDouble(2, price);
            updateItemStatement.setString(3, this.id.toString());
            updateItemStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        this.name = name;
        return 1;
    }

    /**
     * update this item's price
     */
    public int updatePrice(double price) {
        try {
            updateItemStatement.setString(1, name);
            updateItemStatement.setDouble(2, price);
            updateItemStatement.setString(3, this.id.toString());
            updateItemStatement.executeUpdate();
        }
        catch (SQLException e) {
            return -1;
        }
        this.price = price;
        return 1;
    }

    // Getters/Setters

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Calendar getDate() {
        return date;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Item))
            return false;
        return ((Item) o).getId().equals(getId());
    }
}
