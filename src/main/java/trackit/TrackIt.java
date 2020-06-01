// MADE BY: Jacob Hanson-Regalado
// STUDENT ID: 1732241

package trackit;

import javafx.application.Application;
import javafx.stage.Stage;
import trackit.controllers.*;
import trackit.models.*;
import trackit.views.panes.NavPane;
import java.util.*;

public class TrackIt extends Application {
    private static final HashMap<String, Controller> views = new HashMap<>();
    private static Stage window;

    @Override
    public void start(Stage stage) {
        window = stage;

        User.prepareStatements();
        Item.prepareStatements();
        Entry.prepareStatements();

        views.put("login", new LoginController());
        views.put("create", new CreateAccController());
        views.put("home", new HomeController());
        views.put("stats", new StatsController());
        views.put("edit", new EditController());

        stage.setTitle("TrackIt");
        setViewer("login");

        createNavActions(views.get("home").getPage().getNav());
        createNavActions(views.get("stats").getPage().getNav());
        createNavActions(views.get("edit").getPage().getNav());
    }

    /**
     * Utility method for switching between pages.
     *
     * @param view key in views to get page to show.
     */
    public static void setViewer(String view) {
        window.hide();
        window.setScene(views.get(view).getPage().getScene());
        views.get(view).loadPage();
        window.show();
    }

    /**
     * Utility method to set navbar actions for each necessary page.
     *
     * @param nav nullable navbar to set actions for
     */
    private static void createNavActions(NavPane nav) {
        if (nav == null)
            return;

        nav.getHomeLink().setOnAction(e -> setViewer("home"));
        nav.getStatsLink().setOnAction(e -> setViewer("stats"));
        nav.getLogOutLink().setOnAction(e -> setViewer("login"));
        nav.getEditLink().setOnAction(e -> setViewer("edit"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
