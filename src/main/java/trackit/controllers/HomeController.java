// MADE BY: Jacob Hanson-Regalado

package trackit.controllers;

import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import trackit.models.Entry;
import trackit.models.Item;
import trackit.models.User;
import trackit.models.UserManager;
import trackit.views.HomePage;
import trackit.views.Page;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController implements Controller {
    HomePage page;

    public HomeController() {
        this.page = new HomePage();

        page.getAggroCb().getSelectionModel().select(0);

        page.getItemBtn().setOnAction(e -> createItem());
        page.getPurchBtn().setOnAction(e -> createPurch());
        page.getSaleBtn().setOnAction(e -> createSale());
        page.getAggroCb().valueProperty().addListener(e -> updateAggregates());
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void loadPage() {
        updateAggregates();
        updateEntries();
        updateItemCbs();
        clearFields();
    }

    /**
     * Event for creating a new item.
     */
    private void createItem() {
        String itemName = page.getItemNameTf().getText();
        String ppu = page.getItemPriceTf().getText();
        User currUser = UserManager.getCurrUser();
        double price;

        if (itemName.isEmpty() || ppu.isEmpty()) {
            Page.createErrorAlert("Please fill out all fields");
            return;
        }
        if (itemName.length() > 100) {
            Page.createErrorAlert("Please enter a name less than 100 characters");
            return;
        }
        try {
            price = Double.parseDouble(ppu);
        } catch (NumberFormatException e) {
            Page.createErrorAlert("Please enter a number for the price");
            return;
        }

        Item existingItem = Item.getByUserAndName(currUser, itemName);
        if (existingItem != null) {
            Page.createErrorAlert("You've already created an item with that name");
            return;
        }

        Item newItem = new Item(itemName, currUser, price);
        newItem.save();
        Page.createSuccessAlert("Success! You have created a new item");
        loadPage();
    }

    /**
     * Event for creating a new sale.
     */
    private void createSale() {
        String itemName = page.getSaleCb().getSelectionModel().getSelectedItem();
        String unitStr = page.getSaleUnitsTf().getText();
        String amtStr = page.getSaleAmtTf().getText();

        if (!checkEntryFields(itemName, unitStr, amtStr))
            return;

        User user = UserManager.getCurrUser();
        Item item = Item.getByUserAndName(user, itemName);
        int numUnits = Integer.parseInt(unitStr);
        double amt = item.getPrice() * numUnits;
        if (!amtStr.isEmpty())
            amt = Double.parseDouble(amtStr);

        Entry newEntry = new Entry(item, user, amt, numUnits);
        newEntry.save();
        Page.createSuccessAlert("Success! You have created a new sale");
        loadPage();
    }

    /**
     * Event for creating a new purchase
     */
    private void createPurch() {
        String itemName = page.getPurchCb().getSelectionModel().getSelectedItem();
        String unitStr = page.getPurchUnitfTf().getText();
        String amtStr = page.getPurchAmtTf().getText();

        if (!checkEntryFields(itemName, unitStr, amtStr))
            return;

        int numUnits = Integer.parseInt(unitStr);
        User user = UserManager.getCurrUser();
        Item item = Item.getByUserAndName(user, itemName);

        double amt = -item.getPrice() * numUnits;
        if (!amtStr.isEmpty())
            amt = -Double.parseDouble(amtStr);

        Entry newEntry = new Entry(item, user, amt, numUnits);
        newEntry.save();
        Page.createSuccessAlert("Success! You have created a new sale");
        loadPage();

    }

    /**
     * Event for updating entries pane.
     */
    private void updateEntries() {
        GridPane entriesPane = page.getEntriesPane().getEntries();
        entriesPane.getChildren().clear();
        page.getEntriesPane().addTitle();

        User user = UserManager.getCurrUser();
        List<Entry> entries = user.getEntriesAfterDate(user.getDate());

        for (Entry e : entries) {
            String type = e.getAmt() < 0 ? "Purchase" : "Sale";
            String item = e.getItem().getName();
            double amt = Math.abs(e.getAmt());
            int units = Math.abs(e.getUnits());
            Calendar date = e.getDate();

            page.getEntriesPane().addEntry(type, item, units, amt, date);
        }
    }

    /**
     * Event for updating summary pane.
     */
    private void updateAggregates() {
        String period = page.getAggroCb().getSelectionModel().getSelectedItem();
        User user = UserManager.getCurrUser();
        Calendar sinceDate = Controller.getStartDate(period);

        double profits = getAggregate(sinceDate, "profit");
        double sales = getAggregate(sinceDate, "sale");
        double purchs = getAggregate(sinceDate, "purch");

        page.getAggroPurchText().setText(String.format("$%.2f", purchs));
        page.getAggroSaleText().setText(String.format("$%.2f", sales));
        if (profits < 0)
            page.getAggroNetText().setText(String.format("-$%.2f", -profits));
        else
            page.getAggroNetText().setText(String.format("$%.2f", profits));
    }

    /**
     * Event for updating item combobox's in transaction forms.
     */
    private void updateItemCbs() {
        ObservableList<String> purchItems = page.getPurchCb().getItems();
        ObservableList<String> saleItems = page.getSaleCb().getItems();
        purchItems.clear();
        saleItems.clear();

        List<Item> items = UserManager.getCurrUser().getItems();
        items.forEach(item -> {
            saleItems.add(item.getName());
            purchItems.add(item.getName());
        });
    }

    /**
     * Event for clearing all form textfields.
     */
    private void clearFields() {
        page.getSaleAmtTf().setText("");
        page.getSaleUnitsTf().setText("");
        page.getPurchAmtTf().setText("");
        page.getPurchUnitfTf().setText("");
        page.getItemNameTf().setText("");
        page.getItemPriceTf().setText("");
    }

    /* UTILITY METHODS */

    /**
     * Validates the entry fields for a new transaction.
     *
     * @param itemName selected item from combobox
     * @param numUnits user input for units field
     * @param amt      user input for amount field
     * @return whether the inputs are valid or not
     */
    private static boolean checkEntryFields(String itemName, String numUnits, String amt) {
        if (itemName == null || numUnits.isEmpty()) {
            Page.createErrorAlert("Please select an item and enter a number of units");
            return false;
        }
        try {
            Integer.parseInt(numUnits);
        } catch (NumberFormatException e) {
            Page.createErrorAlert("Please enter an integer for the units");
            return false;
        }
        if (!amt.isEmpty()) {
            try {
                Double.parseDouble(amt);
            } catch (NumberFormatException e) {
                Page.createErrorAlert("Please enter a number for the total amount, or leave it blank");
                return false;
            }
        }
        return true;
    }

    /**
     * Sums sales/profits/purchases since a certain date.
     *
     * @param sinceDate date to start summing after
     * @param type      type of transactions to sum ["sale" | "purch" | "profit"]
     * @return sum of all valid transactions over given time period
     */
    private static Double getAggregate(Calendar sinceDate, String type) {
        List<Entry> filteredEntries = UserManager.getCurrUser().getEntriesAfterDate(sinceDate).stream().filter(entry -> {
            if (type.equals("purch"))
                return entry.getAmt() < 0;
            else if (type.equals("sale"))
                return entry.getAmt() > 0;
            return true;
        }).collect(Collectors.toList());

        double a = 0;
        for (Entry e : filteredEntries)
            a += e.getAmt();

        if (type.equals("purch") && a != 0)
            return -a;
        return a;
    }
}
