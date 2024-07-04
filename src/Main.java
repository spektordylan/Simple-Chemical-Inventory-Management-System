/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This main class contains the actual javafx application and is ran to open the application. */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.controls.header.AppHeader;
import ui.controls.inventory.InventoryControlsPane;
import ui.icon.IconGrid;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppHeader appHeader = new AppHeader();

        InventoryControlsPane searchAndFilterPane = new InventoryControlsPane();

        VBox topPane = new VBox(10);
        topPane.getChildren().addAll(appHeader, searchAndFilterPane);
        topPane.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setTop(topPane);
        root.setCenter(IconGrid.getInstance());

        Scene scene = new Scene(root, 1920, 1080);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chemical Inventory Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
