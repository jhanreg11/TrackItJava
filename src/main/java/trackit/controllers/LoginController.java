// MADE BY: Jacob Hanson-Regalado

package trackit.controllers;

import trackit.TrackIt;
import trackit.models.UserManager;
import trackit.views.LoginPage;
import trackit.views.Page;

public class LoginController implements Controller {
    LoginPage page;

    public LoginController() {
        this.page = new LoginPage();
        page.getLoginBtn().setOnAction(e -> login());
        page.getCreateAccLink().setOnAction(e -> TrackIt.setViewer("create"));
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void loadPage() {
        page.clearFields();
    }

    /**
     * Event for login button.
     */
    private void login() {
        String username = page.getUsernameTf().getText();
        String password = page.getPasswordTf().getText();
        if (username.isEmpty() || password.isEmpty()) {
            Page.createErrorAlert("Please enter both a username and password");
            return;
        }

        int result = UserManager.login(username, password);

        if (result == -1) {
            Page.createErrorAlert("Incorrect username or password. Please try again");
            return;
        }

        page.clearFields();
        TrackIt.setViewer("home");
    }
}
