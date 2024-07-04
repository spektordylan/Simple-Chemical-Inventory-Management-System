/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class makes up the UI for the settings window, which can be used to change icon size.*/

package ui.controls.header.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.icon.IconGrid;

class SettingsWindow extends Stage {
    SettingsWindow () {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Text settingsHeader = new Text("Settings");
        settingsHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        RadioButton smallSizeButton = new RadioButton("Small");
        RadioButton mediumSizeButton = new RadioButton("Medium");
        RadioButton largeSizeButton = new RadioButton("Large");

        ToggleGroup toggleGroup = new ToggleGroup();
        smallSizeButton.setToggleGroup(toggleGroup);
        mediumSizeButton.setToggleGroup(toggleGroup);
        largeSizeButton.setToggleGroup(toggleGroup);

        Label iconSizeLabel = new Label("Select icon size: ");
        iconSizeLabel.setFont(Font.font("Segoe UI", 14));

        HBox sizeButtons = new HBox(10);
        //sizeButtons.setPadding(new Insets(20));
        sizeButtons.getChildren().addAll(iconSizeLabel, smallSizeButton, mediumSizeButton, largeSizeButton);
        sizeButtons.setAlignment(Pos.CENTER);

        IconGrid iconGrid = IconGrid.getInstance();

        if (iconGrid.getIconSize().equals("Small")) {
            smallSizeButton.setSelected(true);
        }
        else if (iconGrid.getIconSize().equals("Medium")) {
            mediumSizeButton.setSelected(true);
        }
        else {
            largeSizeButton.setSelected(true);
        }

        HBox saveAndCloseButtons = new HBox(10);
        Button saveButton = new Button("Save");
        Button closeButton = new Button("Close");
        saveAndCloseButtons.getChildren().addAll(saveButton, closeButton);
        saveAndCloseButtons.setAlignment(Pos.CENTER);

        saveButton.setOnAction(e -> iconGrid.setIconSize(((RadioButton)toggleGroup.getSelectedToggle()).getText()));
        closeButton.setOnAction(e -> close());

        root.setTop(settingsHeader);
        root.setCenter(sizeButtons);
        root.setBottom(saveAndCloseButtons);

        BorderPane.setAlignment(settingsHeader, Pos.CENTER);

        Scene scene = new Scene(root, 400, 200);
        setScene(scene);
        show();
    }
}
