// MADE BY: Jacob Hanson-Regalado

package trackit.controllers;

import trackit.TrackIt;
import trackit.models.UserManager;
import trackit.views.CreateAccPage;
import trackit.views.Page;

public class CreateAccController implements Controller {
    final CreateAccPage page;

    public CreateAccController() {
        this.page = new CreateAccPage();
        page.getCreateBtn().setOnAction(e -> createAcc());
        page.getLoginLink().setOnAction(e -> TrackIt.setViewer("login"));
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
     * Event for creating account.
     */
    private void createAcc() {
        String username = page.getUsernameTf().getText();
        String password = page.getPasswordTf().getText();
        String confirmPassword = page.getConfirmPasswordTf().getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Page.createErrorAlert("Please fill out all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Page.createErrorAlert("The passwords you entered do not match");
            return;
        }

        int result = UserManager.createUser(username, password);

        if (result == -1) {
            Page.createErrorAlert("Username already taken");
            return;
        }

        page.clearFields();
        TrackIt.setViewer("home");
    }
}
