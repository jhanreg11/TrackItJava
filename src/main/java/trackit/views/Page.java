// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.views;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import trackit.views.panes.NavPane;

public interface Page extends Viewable {
    /**
     * Interface for creating pages in application.
     * Each page should also provide getters for all necessary objects to be used by controllers.
     */

    double SCENE_WIDTH = 825;
    double SCENE_HEIGHT = 600;

    /**
     * Utility method for creating error alerts.
     *
     * @param text message to display.
     */
    static void createErrorAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.show();
    }

    /**
     * Utility method for creating succes alerts.
     *
     * @param text message to display.
     */
    static void createSuccessAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.setTitle("Success!");
        alert.show();
    }

    /**
     * Creates scene representing the page to be placed on stage.
     *
     * @return the scene to show
     */
    Scene getScene();

    /**
     * Gets Nav pane for scene (if available).
     *
     * @return navigation bar or null
     */
    default NavPane getNav() {
        return null;
    }
}
