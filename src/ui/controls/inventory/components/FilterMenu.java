/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class defines the UI menu used to filter the inventory by state of matter. */

package ui.controls.inventory.components;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import ui.icon.IconGrid;

public class FilterMenu extends HBox {
    public FilterMenu() {
        setSpacing(10);

        MenuButton filterDropDown = new MenuButton();
        filterDropDown.setFont(Font.font("Segoe UI", 16));
        filterDropDown.setPrefWidth(200);

        filterDropDown.setText("Filter by State");

        MenuItem solidFilter = new MenuItem("Show solids");
        MenuItem liquidFilter = new MenuItem("Show liquids");
        MenuItem gasFilter = new MenuItem("Show gases");

        solidFilter.setOnAction(e -> IconGrid.getInstance().filterSolids());
        liquidFilter.setOnAction(e -> IconGrid.getInstance().filterLiquids());
        gasFilter.setOnAction(e -> IconGrid.getInstance().filterGases());
        
        Button clearFilters = new Button("Clear Filters");
        clearFilters.setFont(Font.font("Segoe UI", 16));

        clearFilters.setOnAction(e -> IconGrid.getInstance().resetGrid());

        filterDropDown.getItems().addAll(solidFilter, liquidFilter, gasFilter);

        getChildren().addAll(filterDropDown,  clearFilters);
    }
}
