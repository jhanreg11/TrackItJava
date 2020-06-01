// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import trackit.views.panes.FormPane;

public abstract class AuthPage implements Viewable, Page {
    /**
     * Abstract super-class for login and create account page.
     */

    String[] labels;
    Node[] fields;
    ButtonBase[] btns;
    String title;
    private Scene scene;
    FormPane root;
    private static final int FIELD_WIDTH = 200;

    public AuthPage() {
        labels = null;
        fields = null;
        btns = null;
        title = null;
    }

    protected void setData(String[] labels, Node[] fields, ButtonBase[] btns, String title) {
        this.labels = labels;
        this.fields = fields;
        this.btns = btns;
        this.title = title;

        root = new FormPane(labels, fields, btns, FIELD_WIDTH);

        Label titleLabel = new Label(title);
        titleLabel.setFont(TITLE_LARGE);
        ImageView logo = new ImageView(LOGO);
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        VBox titlePane = new VBox();
        titlePane.setAlignment(Pos.CENTER);
        titlePane.getChildren().addAll(logo, titleLabel);
        root.setTop(titlePane);

        ((Pane) root.getBottom()).setPadding(new Insets(0, 0, 10, 0));

        scene = new Scene(root, 500, 300);
        scene.setFill(WHITE);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public void clearFields() {
        root.clearFields();
    }
}
