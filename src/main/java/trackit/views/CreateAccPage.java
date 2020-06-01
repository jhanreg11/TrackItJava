// MADE BY: Jacob Hanson-Regalado

package trackit.views;

import javafx.scene.control.*;

public class CreateAccPage extends AuthPage {
    final TextField usernameTf = new TextField();
    final PasswordField passwordTf = new PasswordField();
    final PasswordField confirmPasswordTf = new PasswordField();
    final Button createBtn = new Button("Create Account");
    final Hyperlink loginLink = new Hyperlink("Already have an account?");

    /**
     * Instantiates AuthPage superclass and provides necessary data.
     */
    public CreateAccPage() {
        super();

        String[] labels = {"Username:", "Password:", "Confirm Password:"};
        TextField[] fields = {usernameTf, passwordTf, confirmPasswordTf};
        ButtonBase[] btns = {createBtn, loginLink};

        setData(labels, fields, btns, "Create Account");
    }

    public TextField getUsernameTf() {
        return usernameTf;
    }

    public TextField getPasswordTf() {
        return passwordTf;
    }

    public TextField getConfirmPasswordTf() {
        return confirmPasswordTf;
    }

    public Button getCreateBtn() {
        return createBtn;
    }

    public Hyperlink getLoginLink() {
        return loginLink;
    }
}
