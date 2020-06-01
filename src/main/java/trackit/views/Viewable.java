// MADE BY: Jacob Hanson-Regalado

package trackit.views;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public interface Viewable {
    /**
     * Utility interface for defining widely-used rendering constants and methods.
     */

    Font TITLE_LARGE = new Font("Monaco", 36);
    Font TITLE_MEDIUM = new Font("Monaco", 24);
    Font TITLE_SMALL = new Font("Monaco", 16);
    Font BOLD_REGULAR = Font.font("Monaco", FontWeight.EXTRA_BOLD, 14);

    Color WHITE = Color.WHITE;
    Color BLACK = Color.BLACK;
    Color LIGHT_GRAY = Color.LIGHTGRAY;
    Color SEAFOAM = Color.web("#2a9d8f");

    Background WHITE_BACKGROUND = new Background((new BackgroundFill(WHITE, new CornerRadii(0), new Insets(0))));
    Background SEAFOAM_BACKGROUND = new Background((new BackgroundFill(SEAFOAM, new CornerRadii(0), new Insets(0))));
    Background LIGHT_GRAY_BACKGROUND = new Background((new BackgroundFill(LIGHT_GRAY, new CornerRadii(0), new Insets(0))));

    Insets PADDING_5PX = new Insets(5, 5, 5, 5);

    Image LOGO = new Image("img/trackit-logo.png");

    static HBox createTitlePane(String title, int size) {
        HBox titlePane = new HBox();
        Label label = new Label(title);
        switch (size) {
            case 1:
                label.setFont(TITLE_SMALL);
                break;
            case 2:
                label.setFont(TITLE_MEDIUM);
                break;
            case 3:
                label.setFont(TITLE_LARGE);
                break;
        }
        label.setTextFill(BLACK);

        titlePane.setBackground(SEAFOAM_BACKGROUND);
        titlePane.setAlignment(Pos.CENTER);
        titlePane.setPadding(new Insets(1, 1, 1, 1));
        titlePane.getChildren().add(label);

        return titlePane;
    }

    static ComboBox<String> createPeriodCb() {
        ComboBox<String> c = new ComboBox<>();
        c.setItems(FXCollections.observableArrayList("Past Month", "Past Year", "All Time"));
        return c;
    }
}
