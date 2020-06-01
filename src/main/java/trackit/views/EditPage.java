// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import trackit.views.panes.FormPane;
import trackit.views.panes.NavPane;
import java.time.Month;
import java.util.Calendar;

public class EditPage implements Page, Viewable {
    final TextField usernameTf = new TextField();
    final PasswordField passwordTf = new PasswordField();
    final PasswordField confirmPasswordTf = new PasswordField();
    final Label acctDateLabel = new Label("Account created:");
    final Label usernameLabel = new Label("Current username:");
    final Button acctBtn = new Button("Update account");

    final Label itemNameLabel = new Label("Item name:");
    final Label itemPriceLabel = new Label("Current price per unit ($):");
    final Label itemDateCreateLabel = new Label("Date created:");
    final ComboBox<String> itemCb = new ComboBox<>();
    final TextField itemNameTf = new TextField();
    final TextField itemPriceTf = new TextField();
    final Button itemUpdateBtn = new Button("Update item");

    final NavPane nav = new NavPane();

    final BorderPane root = new BorderPane();
    final Scene scene;

    public EditPage() {
        root.setBackground(LIGHT_GRAY_BACKGROUND);

        VBox topPane = new VBox();
        topPane.setSpacing(5);
        topPane.setAlignment(Pos.TOP_CENTER);
        Label title = new Label("Edit Information");
        title.setFont(Viewable.TITLE_LARGE);
        topPane.getChildren().addAll(nav, title);
        root.setTop(topPane);

        BorderPane acctPane = new BorderPane();
        acctPane.setRight(new FormPane(new String[]{"New username:", "Create new password:", "Confirm new password:"},
                new Node[]{usernameTf, passwordTf, confirmPasswordTf},
                new ButtonBase[]{acctBtn},
                150));
        acctPane.setTop(Viewable.createTitlePane("Account", 3));
        VBox acctLeftSide = new VBox();
        acctLeftSide.setSpacing(10);
        acctLeftSide.setAlignment(Pos.CENTER_LEFT);
        acctLeftSide.getChildren().addAll(usernameLabel, acctDateLabel);
        acctPane.setLeft(acctLeftSide);
        acctPane.setStyle("-fx-background-color: white;" +
                "-fx-max-width: 550px;" +
                "-fx-min-height: 240px");

        BorderPane itemPane = new BorderPane();
        itemPane.setRight(new FormPane(new String[]{"Item:", "New item name:", "New price per unit ($):"},
                new Node[]{itemCb, itemNameTf, itemPriceTf},
                new ButtonBase[]{itemUpdateBtn},
                150));
        itemPane.setTop(Viewable.createTitlePane("Items", 3));
        VBox itemLeftSide = new VBox();
        itemLeftSide.setSpacing(10);
        itemLeftSide.setAlignment(Pos.CENTER_LEFT);
        itemLeftSide.getChildren().addAll(itemNameLabel, itemPriceLabel, itemDateCreateLabel);
        itemPane.setLeft(itemLeftSide);
        itemPane.setStyle("-fx-background-color: white;" +
                "-fx-min-height: 240px;" +
                "-fx-max-width: 550px;");

        VBox panes = new VBox();
        panes.setSpacing(10);
        panes.setAlignment(Pos.CENTER);
        panes.getChildren().addAll(acctPane, itemPane);
        root.setCenter(panes);

        scene = new Scene(root, SCENE_HEIGHT, SCENE_HEIGHT);
    }

    public void setAcctDate(Calendar date) {
        acctDateLabel.setText(buildDate("Account created:  ", date));
    }

    public void setUsername(String username) {
        usernameLabel.setText("Current username:  " + username);
    }

    public void setItemName(String name) {
        itemNameLabel.setText("Current item name:  " + name);
    }

    public void setItemPrice(double price) {
        itemPriceLabel.setText(String.format("Current price per unit ($):  %.2f", price));
    }

    public void setItemDate(Calendar date) {
        itemDateCreateLabel.setText(buildDate("Item created:  ", date));
    }

    public String getUsername() {
        return usernameTf.getText();
    }

    public String getPassword() {
        return passwordTf.getText();
    }

    public String getConfirmPassword() {
        return confirmPasswordTf.getText();
    }

    public ComboBox<String> getItemCb() {
        return itemCb;
    }

    public String getItemName() {
        return itemNameTf.getText();
    }

    public String getItemPrice() {
        return itemPriceTf.getText();
    }

    public void clearFields() {
        for (TextField t : new TextField[]{usernameTf, passwordTf, confirmPasswordTf, itemNameTf, itemPriceTf})
            t.setText("");
    }

    public void clearInfo() {
        setUsername("");
        setItemName("");
        itemPriceLabel.setText("Current price per unit ($): ");
        itemDateCreateLabel.setText("Item created: ");
        acctDateLabel.setText("Account created: ");
    }

    public Button getAcctUpdateBtn() {
        return acctBtn;
    }

    public Button getItemUpdateBtn() {
        return itemUpdateBtn;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public NavPane getNav() {
        return nav;
    }

    private static String buildDate(String initial, Calendar date) {
        StringBuilder dateStr = new StringBuilder(initial);
        String month = Month.of(date.get(Calendar.MONTH) + 1).name().toLowerCase();
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        dateStr.append(month);
        dateStr.append('/');
        dateStr.append(date.get(Calendar.DAY_OF_MONTH));
        dateStr.append('/');
        dateStr.append(date.get(Calendar.YEAR));
        return dateStr.toString();
    }
}
