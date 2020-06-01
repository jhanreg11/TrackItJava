// MADE BY: Jacob Hanson-Regalado

package trackit.views.panes;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import trackit.views.Viewable;

import java.time.Month;
import java.util.Calendar;

public class EntriesPane extends ScrollPane implements Viewable {
    /**
     * Utility class providing easier interface to manipulate transactions history.
     */

    int numRows = 0;
    final GridPane entries = new GridPane();

    public EntriesPane(int h, int w) {
        setPrefSize(w, h);
        entries.setBackground(WHITE_BACKGROUND);

        ObservableList<ColumnConstraints> cols = entries.getColumnConstraints();
        for (int i = 0; i < 5; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(20);
            cols.add(col);
        }

        addTitle();

        VBox root = new VBox();
        root.getChildren().addAll(Viewable.createTitlePane("Recent Entries", 2), entries);

        setContent(root);
        entries.setBackground(WHITE_BACKGROUND);
        fitToWidthProperty().set(true);
    }

    public void addEntry(String category, String item, Integer units, Double amt, Calendar date) {
        addRow(category, item, units.toString(), String.format("%.2f", amt), parseDate(date), false);
    }

    private void addRow(String col1, String col2, String col3, String col4, String col5, boolean title) {
        HBox catPane = new HBox();
        catPane.getChildren().add(new Label(col1));

        HBox itemPane = new HBox();
        itemPane.getChildren().add(new Label(col2));

        HBox unitsPane = new HBox();
        unitsPane.getChildren().add(new Label(col3));

        HBox amtPane = new HBox();
        amtPane.getChildren().add(new Label(col4));

        HBox datePane = new HBox();
        datePane.getChildren().add(new Label(col5));

        Pane[] panes = {catPane, itemPane, unitsPane, amtPane, datePane};
        for (int i = 0; i < panes.length; ++i) {
            panes[i].setStyle("-fx-border-color: transparent gray gray transparent;" +
                    "-fx-border-width: 3px;" +
                    "-fx-padding: 3px;" +
                    "-fx-alignment: center");
            entries.add(panes[i], i, numRows);
        }

        if (title) {
            for (Pane p : panes)
                ((Label) p.getChildren().get(0)).setFont(BOLD_REGULAR);
        }

        panes[4].setStyle("-fx-border-color: transparent transparent gray transparent;" +
                "-fx-border-width: 3px;" +
                "-fx-padding: 3px;" +
                "-fx-alignment: center");

        numRows++;
    }

    private String parseDate(Calendar date) {
        return String.format("%s/%d/%d",
                Month.of(date.get(Calendar.MONTH) + 1).name().substring(0, 3),
                date.get(Calendar.DAY_OF_MONTH),
                date.get(Calendar.YEAR));
    }

    public GridPane getEntries() {
        return entries;
    }

    public void addTitle() {
        addRow("Category", "Item", "Units", "Amount ($)", "Date", true);
    }
}