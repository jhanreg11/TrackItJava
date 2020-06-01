// MADE BY: Jacob Hanson-Regalado

package trackit.views.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import trackit.views.Viewable;


public class NavPane extends HBox implements Viewable {
    /**
     * Utility class for creating navigation bar
     */

    final Hyperlink homeLink = new Hyperlink("HOME");
    final Hyperlink statsLink = new Hyperlink("STATISTICS");
    final Hyperlink editLink = new Hyperlink("EDIT");
    final Hyperlink logOutLink = new Hyperlink("LOGOUT");
    final ImageView logo = new ImageView(LOGO);

    public NavPane() {
        HBox links = new HBox();
        links.setSpacing(10);
        links.setAlignment(Pos.CENTER);
        links.getChildren().addAll(homeLink, statsLink, editLink, logOutLink);

        for (Hyperlink l : new Hyperlink[]{homeLink, statsLink, editLink, logOutLink}) {
            l.setFont(TITLE_SMALL);
            l.setTextFill(BLACK);
            l.setStyle("-fx-underline: false");
            l.setOnMouseEntered(e -> l.setStyle("-fx-underline: true"));
            l.setOnMouseExited(e -> l.setStyle("-fx-underline: false"));
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        logo.setFitHeight(36);
        logo.setFitWidth(36);

        this.setPadding(new Insets(5, 5, 5, 5));

        this.getChildren().addAll(logo, spacer, links);
        this.setBackground(SEAFOAM_BACKGROUND);
    }

    public Hyperlink getHomeLink() {
        return homeLink;
    }

    public Hyperlink getStatsLink() {
        return statsLink;
    }

    public Hyperlink getLogOutLink() {
        return logOutLink;
    }

    public Hyperlink getEditLink() {
        return editLink;
    }
}
