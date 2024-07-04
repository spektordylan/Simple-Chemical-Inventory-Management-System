/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class contains the top header for the application which has file controls, the app name, and settings.*/

package ui.controls.header;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.controls.header.components.FileControls;
import ui.controls.header.components.SettingsButton;

public class AppHeader extends BorderPane {
    public AppHeader() {
        setStyle("-fx-border-color: #bababa; -fx-border-width: 0 0 1px 0;");

        FileControls fileControls = new FileControls();
        
        Text applicationName = new Text("Chemical Inventory Management System");
        applicationName.setFont(Font.font("Segoe UI", 20));
        setAlignment(applicationName, Pos.CENTER);
        
        SettingsButton settingsButton = new SettingsButton();

        // this centers applicationName
        BorderPane.setMargin(settingsButton, new Insets(0, 0, 0, 300));

        setLeft(fileControls);
        setCenter(applicationName);
        setRight(settingsButton);
    }
}
