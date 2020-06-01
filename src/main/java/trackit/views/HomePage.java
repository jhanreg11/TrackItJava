// MADE BY: Jacob Hanson-Regalado

package trackit.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import trackit.views.panes.EntriesPane;
import trackit.views.panes.FormPane;
import trackit.views.panes.NavPane;

public class HomePage implements Page {
    final ComboBox<String> saleCb = new ComboBox<>();
    final TextField saleUnitsTf = new TextField();
    final TextField saleAmtTf = new TextField();
    final Button saleBtn = new Button("Create sale");

    final ComboBox<String> purchCb = new ComboBox<>();
    final TextField purchUnitfTf = new TextField();
    final TextField purchAmtTf = new TextField();
    final Button purchBtn = new Button("Create Purchase");

    final TextField itemNameTf = new TextField();
    final TextField itemPriceTf = new TextField();
    final Button itemBtn = new Button("Create Item");

    final EntriesPane entriesPane = new EntriesPane(398, 515);

    final ComboBox<String> aggroCb = Viewable.createPeriodCb();
    final Label aggroSaleText = new Label();
    final Label aggroPurchText = new Label();
    final Label aggroNetText = new Label();

    final NavPane nav = new NavPane();

    private final Scene scene;

    public HomePage() {
        BorderPane root = new BorderPane();
        root.setBackground(LIGHT_GRAY_BACKGROUND);

        root.setTop(nav);

        String[] entryLabels = {"Item:", "Number of Units:", "Total Amount (Optional) $:"};
        Pane salePane = createForm(entryLabels,
                new Node[]{saleCb, saleUnitsTf, saleAmtTf},
                saleBtn,
                "New Sale",
                75);
        Pane purchPane = createForm(entryLabels,
                new Node[]{purchCb, purchUnitfTf, purchAmtTf},
                purchBtn,
                "New Purchase",
                75);
        Pane itemPane = createForm(new String[]{"Name:", "Price Per Unit ($):"},
                new Node[]{itemNameTf, itemPriceTf},
                itemBtn,
                "New Item",
                150);

        VBox forms = new VBox();
        forms.getChildren().addAll(salePane, purchPane, itemPane);
        forms.setSpacing(20);

        VBox leftHalf = new VBox();
        leftHalf.setSpacing(20);
        Pane aggros = createAggregates();
        aggros.setBackground(WHITE_BACKGROUND);
        leftHalf.getChildren().addAll(entriesPane, aggros);

        HBox main = new HBox();
        main.setSpacing(10);
        main.getChildren().addAll(leftHalf, forms);
        main.setPadding(new Insets(5, 5, 5, 5));
        root.setCenter(main);

         scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Creates form with title pane attached
     *
     * @param labels    ordered labels for each field
     * @param fields    ordered fields
     * @param button    submit button
     * @param title     title for form
     * @param prefWidth preferred width for text fields
     * @return titled form pane
     */
    private Pane createForm(String[] labels, Node[] fields, Button button, String title, int prefWidth) {
        FormPane root = new FormPane(labels, fields, new ButtonBase[]{button}, prefWidth);

        ((Pane) root.getCenter()).setPadding(new Insets(5, 5, 5, 5));
        root.setBackground(WHITE_BACKGROUND);

        root.setTop(Viewable.createTitlePane(title, 2));

        root.setMaxSize(300, 200);
        return root;
    }

    /**
     * Creates Summary pane for aggregating past transactions.
     * Not created as a class because manipulation interface is already simple enough and only used in HomePage.
     *
     * @return Summary pane
     */
    private Pane createAggregates() {
        double width = 515;
        double height = 110;

        GridPane root = new GridPane();
        root.setPrefSize(width, height);
        root.setVgap(10);
        root.setHgap(10);
        for (int i = 0; i < 3; ++i) root.getColumnConstraints().add(new ColumnConstraints(width / 3));

        HBox titlePane = Viewable.createTitlePane("Summary", 2);
        titlePane.setSpacing(10);
        titlePane.getChildren().add(aggroCb);

        root.add(titlePane, 0, 0, 3, 1);

        String[] texts = {"Total Sales", "Total Purchases", "Net Profit"};
        Label[] fields = {aggroSaleText, aggroPurchText, aggroNetText};
        for (int i = 0; i < 3; ++i) {
            VBox item = new VBox();
            item.setAlignment(Pos.CENTER);
            Label l = new Label(texts[i]);
            l.setFont(TITLE_SMALL);
            fields[i].setFont(TITLE_SMALL);
            item.getChildren().addAll(l, fields[i]);
            root.add(item, i, 1);
        }

        return root;
    }

    public ComboBox<String> getSaleCb() {
        return saleCb;
    }

    public TextField getSaleUnitsTf() {
        return saleUnitsTf;
    }

    public TextField getSaleAmtTf() {
        return saleAmtTf;
    }

    public Button getSaleBtn() {
        return saleBtn;
    }

    public ComboBox<String> getPurchCb() {
        return purchCb;
    }

    public TextField getPurchUnitfTf() {
        return purchUnitfTf;
    }

    public TextField getPurchAmtTf() {
        return purchAmtTf;
    }

    public Button getPurchBtn() {
        return purchBtn;
    }

    public TextField getItemNameTf() {
        return itemNameTf;
    }

    public TextField getItemPriceTf() {
        return itemPriceTf;
    }

    public Button getItemBtn() {
        return itemBtn;
    }

    public EntriesPane getEntriesPane() {
        return entriesPane;
    }

    public ComboBox<String> getAggroCb() {
        return aggroCb;
    }

    public Label getAggroSaleText() {
        return aggroSaleText;
    }

    public Label getAggroPurchText() {
        return aggroPurchText;
    }

    public Label getAggroNetText() {
        return aggroNetText;
    }

    @Override
    public NavPane getNav() {
        return nav;
    }
}

