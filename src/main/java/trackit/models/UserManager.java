// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.models;

public class UserManager {
    /**
     * Static class for controlling current user and preparing database.
     */

    private static User currUser;

    /**
     * Attempts to create new user.
     *
     * @param username given username
     * @param password given password
     * @return success (1) or failure (-1)
     */
    public static int createUser(String username, String password) {
        User user = User.getById(username);
        if (user != null)
            return -1;

        user = new User(username, password);
        user.save();
        currUser = user;
        return 1;
    }

    /**
     * Attempts to log in user.
     *
     * @param username given username
     * @param password given password
     * @return success (1) or failure (-1)
     */
    public static int login(String username, String password) {
        User user = User.getById(username);
        if (user == null)
            return -1;
        if (!user.password.equals(password))
            return -1;

        currUser = user;
        return 1;
    }

    public static User getCurrUser() {
        return currUser;
    }
}
