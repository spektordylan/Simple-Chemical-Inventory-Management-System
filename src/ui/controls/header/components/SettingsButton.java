/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class contains the button used to open settings. */

package ui.controls.header.components;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class SettingsButton extends Button {
    public SettingsButton() {
        setPrefHeight(45);
        setText("Settings");
        setFont(Font.font("Segoe UI", 16));
        setOnAction(e -> new SettingsWindow());
    }
}
