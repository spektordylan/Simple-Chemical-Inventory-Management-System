/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class contains the searchbox control used to search the inventory. */

package ui.controls.inventory.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import ui.icon.IconGrid;

public class SearchBox extends HBox {
    private TextField searchField;
    private Button cancelButton;
    private String DEFAULT_STRING = "Search inventory...";

    public SearchBox() {
        searchField = new TextField(DEFAULT_STRING);

        searchField.setPrefColumnCount(23);
        cancelButton = new Button("X");

        cancelButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16");
        cancelButton.setPrefHeight(35);
        cancelButton.setPrefWidth(35);
        searchField.setPrefHeight(35);
        searchField.setFont(Font.font("Segoe UI", 16));
        searchField.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(searchField, cancelButton);

        cancelButton.setOnAction(
            e -> {
                IconGrid.getInstance().filterCurrent();
            }
        );
        
        searchField.setOnMouseClicked(e -> clearDefaultText());

        searchField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                searchField.setText(DEFAULT_STRING);
            }
        });

        searchField.setOnAction(
            e -> {
                IconGrid.getInstance().searchGrid(searchField.getText().toLowerCase());
            }
        );
    }

    // clear default search inventory message if user clicks on searchbox
    private void clearDefaultText() {
        if (searchField.getText().equals(DEFAULT_STRING)) {
            searchField.clear();
        }
    }
}