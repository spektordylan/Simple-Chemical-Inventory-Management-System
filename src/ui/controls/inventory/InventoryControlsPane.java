/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class makes up the UI pane used to control the inventory, including the search box, filter menu, and mass delete options. */

package ui.controls.inventory;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ui.controls.inventory.components.FilterMenu;
import ui.controls.inventory.components.MassDeleteOptions;
import ui.controls.inventory.components.SearchBox;

public class InventoryControlsPane extends BorderPane {
    public InventoryControlsPane() {
        setPadding(new Insets(0, 50, 0, 50));
        setStyle("-fx-border-color: #bababa; -fx-border-width: 0 0 1px 0;");
        setPrefHeight(40);

        SearchBox searchBox = new SearchBox();
        FilterMenu filterMenu = new FilterMenu();
        MassDeleteOptions massDeleteBox = new MassDeleteOptions();

        HBox rightBox = new HBox(35);
        rightBox.getChildren().addAll(filterMenu, massDeleteBox);

        setCenter(searchBox);
        setRight(rightBox);
    }
}
