package trackit.controllers;

import javafx.collections.FXCollections;
import trackit.models.Item;
import trackit.models.UserManager;
import trackit.models.User;
import trackit.views.EditPage;
import trackit.views.Page;
import java.util.List;
import java.util.stream.Collectors;

public class EditController implements Controller {
    EditPage page;

    public EditController() {
        this.page = new EditPage();
        page.getAcctUpdateBtn().setOnAction(e -> updateAcct());
        page.getItemUpdateBtn().setOnAction(e -> updateItem());
        page.getItemCb().valueProperty().addListener(e -> showItemInfo());
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void loadPage() {
        page.clearInfo();
        showUserInfo();
        updateItemCb();
        page.clearFields();
    }

    /**
     * Event for update account button.
     */
    private void updateAcct() {
        String newUsername = page.getUsername();
        String password = page.getPassword();
        String confirmPassword = page.getConfirmPassword();
        User user = UserManager.getCurrUser();

        if (newUsername.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            Page.createErrorAlert("Please fill out at least one field to update your account.");
            return;
        }
        else if (password.isEmpty() ^ confirmPassword.isEmpty()) {
            Page.createErrorAlert("If you want to update your password, please fill out both fields.");
            return;
        }
        else if (!password.isEmpty() && !password.equals(confirmPassword)) {
            Page.createErrorAlert("Your passwords do not match.");
            return;
        }

        if (!newUsername.isEmpty()) {
            int result = user.updateUsername(newUsername);
            if (result == -1) {
                Page.createErrorAlert("Username already taken. No updates performed");
                return;
            }
        }

        if (!password.isEmpty())
            user.updatePassword(password);

        Page.createSuccessAlert("Account successfully updated!");
        page.clearFields();
        showUserInfo();
    }

    /**
     * Event for update item button.
     */
    private void updateItem() {
        String newName = page.getItemName();
        String newPrice = page.getItemPrice();
        String selectedItem = page.getItemCb().getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Page.createErrorAlert("Please select an item to update it.");
            return;
        }
        else if (newPrice.isEmpty() && newName.isEmpty()) {
            Page.createErrorAlert("Please fill out at least one field to update an item.");
            return;
        }

        Item item = Item.getByUserAndName(UserManager.getCurrUser(), selectedItem);
        if (!newPrice.isEmpty()) {
            Double price;
            try {
                price = Double.parseDouble(newPrice);
            } catch (NumberFormatException e) {
                Page.createErrorAlert("Please enter a number if you wish to update the item's price.");
                return;
            }
            item.updatePrice(price);
        }

        if (!newName.isEmpty()) {
            List<Item> items = UserManager.getCurrUser().getItems();
            for (Item i : items) {
                if (i.getName().equals(newName) && !i.equals(item)) {
                    Page.createErrorAlert("You already have an item with this name.");
                    return;
                }
            }
            item.updateName(newName);
        }

        Page.createSuccessAlert("Item successfully updated.");
        page.clearFields();
        updateItemCb();
        if (!newName.isEmpty())
        page.getItemCb().getSelectionModel().select(newName);
        showItemInfo();
    }

    /**
     * Display username/date created in Account box.
     */
    private void showUserInfo() {
        User user = UserManager.getCurrUser();
        page.setUsername(user.getUsername());
        page.setAcctDate(user.getDate());
    }

    /**
     * Display current item name/price/date created in Items box
     */
    private void showItemInfo() {
        String selectedItem = page.getItemCb().getSelectionModel().getSelectedItem();
        if (selectedItem == null)
            return;

        Item item = Item.getByUserAndName(UserManager.getCurrUser(), selectedItem);
        page.setItemName(item.getName());
        page.setItemPrice(item.getPrice());
        page.setItemDate(item.getDate());
    }

    /**
     * Update contents of item combo-box.
     */
    private void updateItemCb() {
        List<String> names = UserManager
                .getCurrUser()
                .getItems()
                .stream()
                .map(i -> i.getName())
                .collect(Collectors.toList());
        page.getItemCb().setItems(FXCollections.observableList(names));
    }


}
