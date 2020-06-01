// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit.views.panes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import trackit.views.Viewable;

public class RankingPane extends GridPane implements Viewable {
    /**
     * Utility class for providing easy interface to Rankings panes
     */

    final int maxRows;
    final ComboBox<String> periodCb = Viewable.createPeriodCb();

    public RankingPane(String title, int maxRows) {
        this.maxRows = maxRows;
        setMinSize(260, 190);
        setMaxSize(260, 190);
        setBackground(WHITE_BACKGROUND);

        ObservableList<RowConstraints> rows = getRowConstraints();
        for (int i = 0; i < maxRows + 1; ++i) {
            RowConstraints r = new RowConstraints();
            r.setPercentHeight(100.0 / (maxRows + 1));
            rows.add(r);
        }

        ColumnConstraints rankCol = new ColumnConstraints();
        rankCol.setPercentWidth(15);
        ColumnConstraints nameCol = new ColumnConstraints();
        nameCol.setPercentWidth(60);
        ColumnConstraints valCol = new ColumnConstraints();
        valCol.setPercentWidth(25);

        getColumnConstraints().addAll(rankCol, nameCol, valCol);

        HBox titlePane = Viewable.createTitlePane(title, 1);
        titlePane.setSpacing(10);
        titlePane.getChildren().add(periodCb);
        add(titlePane, 0, 0, 3, 1);

        for (int i = 0; i < maxRows; ++i) {
            HBox rankPane = new HBox();
            rankPane.setAlignment(Pos.CENTER);
            if (i != maxRows - 1)
                rankPane.setStyle("-fx-border-color: transparent transparent gray transparent;");
            rankPane.getChildren().add(new Label((i + 1) + "."));
            add(rankPane, 0, i + 1);
        }
    }

    public void setRow(Integer rank, String name, Double val) {
        HBox namePane = new HBox();
        namePane.setAlignment(Pos.CENTER);
        namePane.getChildren().add(new Label(name));
        add(namePane, 1, rank);

        HBox valPane = new HBox();
        valPane.setAlignment(Pos.CENTER);

        if (rank != maxRows) {
            namePane.setStyle("-fx-border-color: transparent transparent gray transparent");
            valPane.setStyle("-fx-border-color: transparent transparent gray transparent");
        }

        String valStr;
        if (val < 0)
            valStr = String.format("-$%.2f", -val);
        else
            valStr = String.format("$%.2f", val);
        valPane.getChildren().add(new Label(valStr));
        add(valPane, 2, rank);
    }

    public ComboBox<String> getPeriodCb() {
        return periodCb;
    }

    public void removeRankings() {
        getChildren().removeIf(node -> GridPane.getRowIndex(node) != 0 && GridPane.getColumnIndex(node) != 0);
    }

}
