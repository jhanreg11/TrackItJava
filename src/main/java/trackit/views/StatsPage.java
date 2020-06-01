// MADE BY: Jacob Hanson-Regalado

package trackit.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trackit.views.panes.NavPane;
import trackit.views.panes.RankingPane;

public class StatsPage implements Page {
    /**
     * Statistics Page.
     */

    final RankingPane sellersPane = new RankingPane("Top Sellers", 5);
    final RankingPane purchPane = new RankingPane("Top Purchases", 5);
    final RankingPane profitPane = new RankingPane("Top Profiters", 5);

    final ComboBox<String> chartCb = Viewable.createPeriodCb();
    final NumberAxis yAxis = new NumberAxis();
    final CategoryAxis xAxis = new CategoryAxis();
    final LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);

    final NavPane nav = new NavPane();
    private final Scene scene;

    public StatsPage() {
        BorderPane root = new BorderPane();
        root.setBackground(LIGHT_GRAY_BACKGROUND);
        root.setTop(nav);

        HBox rankPanes = new HBox();
        rankPanes.setAlignment(Pos.CENTER);
        rankPanes.setSpacing(15);
        rankPanes.getChildren().addAll(sellersPane, purchPane, profitPane);

        BorderPane chartPane = new BorderPane();

        HBox titlePane = Viewable.createTitlePane("Recent Trends", 2);
        titlePane.setSpacing(10);
        titlePane.getChildren().add(chartCb);
        chartPane.setTop(titlePane);

        chartPane.setCenter(chart);
        chartPane.setPadding(PADDING_5PX);
        chart.setMinSize(815, 300);
        chart.setMaxSize(815, 300);
        chart.setBackground(WHITE_BACKGROUND);
        chart.setAnimated(false);
        yAxis.setLabel("Amount ($)");

        VBox centerPane = new VBox();
        centerPane.setSpacing(10);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.getChildren().addAll(rankPanes, chartPane);

        root.setCenter(centerPane);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public RankingPane getSellersPane() {
        return sellersPane;
    }

    public RankingPane getPurchPane() {
        return purchPane;
    }

    public RankingPane getProfitPane() {
        return profitPane;
    }

    public LineChart<String, Number> getChart() {
        return chart;
    }

    public NavPane getNav() {
        return nav;
    }

    public ComboBox<String> getChartCb() {
        return chartCb;
    }
}

