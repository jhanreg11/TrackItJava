// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.views;

import javafx.scene.control.*;

public class LoginPage extends AuthPage {
    final TextField usernameTf = new TextField();
    final PasswordField passwordTf = new PasswordField();
    final Button loginBtn = new Button("Login");
    final Hyperlink createAccLink = new Hyperlink("Don't have an account?");

    /**
     * Instantiates AuthPage superclass and provides necessary data.
     */
    public LoginPage() {
        super();
        String[] labels = {"Username:", "Password:"};
        TextField[] textFields = {usernameTf, passwordTf};
        ButtonBase[] btns = {loginBtn, createAccLink};

        setData(labels, textFields, btns, "LOGIN");
    }

    public TextField getUsernameTf() {
        return usernameTf;
    }

    public PasswordField getPasswordTf() {
        return passwordTf;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    public Hyperlink getCreateAccLink() {
        return createAccLink;
    }
}

