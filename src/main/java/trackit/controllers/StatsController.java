// MADE BY: Jacob Hanson-Regalado

package trackit.controllers;

import javafx.scene.chart.XYChart;
import trackit.models.Entry;
import trackit.models.Item;
import trackit.models.User;
import trackit.models.UserManager;
import trackit.views.Page;
import trackit.views.StatsPage;
import trackit.views.panes.RankingPane;

import java.time.Month;
import java.util.*;

public class StatsController implements Controller {
    StatsPage page;

    public StatsController() {
        this.page = new StatsPage();

        // set initial combobox vals
        page.getProfitPane().getPeriodCb().getSelectionModel().select(0);
        page.getSellersPane().getPeriodCb().getSelectionModel().select(0);
        page.getPurchPane().getPeriodCb().getSelectionModel().select(0);
        page.getChartCb().getSelectionModel().select(0);

        page.getChartCb().valueProperty().addListener(e -> updateChart());
        page.getSellersPane().getPeriodCb().valueProperty().addListener(e -> updateTopSellers());
        page.getPurchPane().getPeriodCb().valueProperty().addListener(e -> updateTopPurchs());
        page.getProfitPane().getPeriodCb().valueProperty().addListener(e -> updateTopProfits());
    }

    @Override
    public void loadPage() {
        updateChart();
        updateTopProfits();
        updateTopPurchs();
        updateTopSellers();
        resetCbs();
    }

    @Override
    public Page getPage() {
        return page;
    }

    /**
     * Event for updating graph.
     */
    private void updateChart() {
        User user = UserManager.getCurrUser();
        String period = page.getChartCb().getSelectionModel().getSelectedItem();

        XYChart.Series profitSeries = new XYChart.Series();
        XYChart.Series saleSeries = new XYChart.Series();
        XYChart.Series purchSeries = new XYChart.Series();
        profitSeries.setName("Total Profits");
        saleSeries.setName("Total Sales");
        purchSeries.setName("Total Purchases");

        Calendar startDate = Controller.getStartDate(period);
        Calendar currDate = Calendar.getInstance();

        int dateField = Calendar.DAY_OF_YEAR;
        if (period.equals("All Time")) {
            startDate = user.getDate();
            if (currDate.getTimeInMillis() - startDate.getTimeInMillis() > 3e10) {
                dateField = Calendar.MONTH;
                startDate.set(Calendar.DAY_OF_MONTH, 1);
            }
        } else if (period.equals("Past Year")) {
            dateField = Calendar.MONTH;
            startDate.set(Calendar.DAY_OF_MONTH, 1);
        }
        Calendar endDate = (Calendar) startDate.clone();
        endDate.set(dateField, startDate.get(dateField) + 1);

        while (startDate.before(currDate)) {
            List<Entry> entries = user.getEntriesInRange(startDate, endDate);
            double profits = 0;
            double purchases = 0;
            double sales = 0;
            for (Entry e : entries) {
                profits += e.getAmt();
                if (e.getAmt() < 0)
                    purchases -= e.getAmt();
                else
                    sales += e.getAmt();
            }
            String label = getChartXLabel(startDate, endDate);
            profitSeries.getData().add(new XYChart.Data<String, Number>(label, profits));
            saleSeries.getData().add(new XYChart.Data<String, Number>(label, sales));
            purchSeries.getData().add(new XYChart.Data<String, Number>(label, purchases));

            startDate.set(dateField, startDate.get(dateField) + 1);
            endDate.set(dateField, endDate.get(dateField) + 1);
        }

        page.getChart().getData().clear();
        page.getChart().getData().addAll(purchSeries, saleSeries, profitSeries);
    }

    /**
     * Event for updating top sellers pane.
     */
    private void updateTopSellers() {
        List<Item> items = UserManager.getCurrUser().getItems();
        String period = page.getSellersPane().getPeriodCb().getSelectionModel().getSelectedItem();
        Calendar sinceDate = Controller.getStartDate(period);

        HashMap<Item, Double> sums = new HashMap<>();
        for (Item item : items) {
            List<Entry> entries = item.getEntriesAfterDate(sinceDate);
            double amt = 0;
            for (Entry e : entries) {
                if (e.getDate().after(sinceDate) && e.getAmt() > 0)
                    amt += e.getAmt();
            }

            sums.put(item, amt);
        }

        updateRankingPane(page.getSellersPane(), items, sums);
    }

    /**
     * Event for updating top purchasers pane.
     */
    private void updateTopPurchs() {
        List<Item> items = UserManager.getCurrUser().getItems();
        String period = page.getPurchPane().getPeriodCb().getSelectionModel().getSelectedItem();
        Calendar sinceDate = Controller.getStartDate(period);

        HashMap<Item, Double> sums = new HashMap<>();
        for (Item item : items) {
            List<Entry> entries = item.getEntriesAfterDate(sinceDate);
            double amt = 0;
            for (Entry e : entries) {
                if (e.getDate().after(sinceDate) && e.getAmt() < 0)
                    amt -= e.getAmt();
            }
            sums.put(item, amt);
        }

        updateRankingPane(page.getPurchPane(), items, sums);
    }

    /**
     * Event for updating top profits pane.
     */
    private void updateTopProfits() {
        List<Item> items = UserManager.getCurrUser().getItems();
        String period = page.getProfitPane().getPeriodCb().getSelectionModel().getSelectedItem();
        Calendar sinceDate = Controller.getStartDate(period);

        HashMap<Item, Double> sums = new HashMap<>();
        for (Item item : items) {
            List<Entry> entries = item.getEntriesAfterDate(sinceDate);
            double amt = 0;
            for (Entry e : entries) {
                if (e.getDate().after(sinceDate))
                    amt += e.getAmt();
            }

            sums.put(item, amt);
        }

        updateRankingPane(page.getProfitPane(), items, sums);
    }

    /**
     * Event for resetting all time-period combobox selections.
     */
    private void resetCbs() {
        page.getSellersPane().getPeriodCb().getSelectionModel().select(0);
        page.getProfitPane().getPeriodCb().getSelectionModel().select(0);
        page.getPurchPane().getPeriodCb().getSelectionModel().select(0);
        page.getChartCb().getSelectionModel().select(0);
    }

    /* UTILITY METHODS */

    /**
     * Updates the given ranking pane given possible items and sum of valid transactions for each item.
     *
     * @param pane  pane to update
     * @param items List of valid items, passed as a parameter for performance because each caller already constructs this list.
     * @param sums  Item to sum of valid transactions pairs
     */
    private static void updateRankingPane(RankingPane pane, List<Item> items, HashMap<Item, Double> sums) {
        pane.removeRankings();

        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return sums.get(o2).compareTo(sums.get(o1));  // descending order
            }
        });

        for (int i = 0; i < Math.min(5, items.size()); ++i) {
            Item item = items.get(i);
            pane.setRow(i + 1, item.getName(), sums.get(item));
        }
    }

    /**
     * Generates correct label for one point on x axis of graph.
     *
     * @param start start date of point
     * @param end   end date of point
     * @return chart label
     */
    private static String getChartXLabel(Calendar start, Calendar end) {
        int secondHalfOfLabel = end.get(Calendar.DAY_OF_MONTH);
        if (end.getTimeInMillis() - start.getTimeInMillis() > 9e7)
            secondHalfOfLabel = end.get(Calendar.YEAR);
        return String.format("%s/%d", Month.of(end.get(Calendar.MONTH) + 1).name().substring(0, 3), secondHalfOfLabel);
    }
}
