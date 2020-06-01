// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.models;

import java.sql.Connection;

public interface Model {
    Connection connection = DatabaseConnection.getConnection();

    /**
     * method for saving model to database
     */
    void save();

}
