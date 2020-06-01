// MADE BY: Jacob Hanson-Regalado

package trackit.views.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import trackit.views.Viewable;


public class FormPane extends BorderPane {
    /**
     * Utility class for creating forms.
     */

    private final Node[] fields;
    private final ButtonBase[] btns;

    public FormPane(String[] labels, Node[] fields, ButtonBase[] btns, double fieldWidth) {
        this.fields = fields;
        this.btns = btns;

        GridPane fieldPane = createGridFields(labels, fieldWidth);
        this.setCenter(fieldPane);

        HBox btnPane = createBtnPane();
        btnPane.setSpacing(5);
        btnPane.setPadding(new Insets(0, 0, 5, 0));
        this.setBottom(btnPane);
    }

    private GridPane createGridFields(String[] labelStrs, double fieldWidth) {
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(5);
        Label[] labels = new Label[labelStrs.length];

        for (int i = 0; i < fields.length; ++i) {
            if (i < labelStrs.length) {
                labels[i] = new Label(labelStrs[i]);
                pane.add(labels[i], 0, i);
            }

            pane.add(fields[i], 1, i);
            if (fields[i] instanceof TextField)
                ((TextField) fields[i]).setPrefWidth(fieldWidth);
        }
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(Viewable.PADDING_5PX);
        return pane;
    }

    private HBox createBtnPane() {
        HBox pane = new HBox();
        pane.getChildren().addAll(this.btns);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    // utility method for clearing content of all fields
    public void clearFields() {
        for (Node n : fields) {
            if (n instanceof TextField)
                ((TextField) n).setText("");
            else if (n instanceof ComboBox<?>)
                ((ComboBox) n).getSelectionModel().select(0);
        }
    }
}
