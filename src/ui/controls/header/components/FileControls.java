/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class contains the UI controls used to perform file functions and displays the last report date in the header. */

package ui.controls.header.components;

import filehandling.FileHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ui.entry.ChemicalEntryWindow;

public class FileControls extends HBox {
    private static Text lastReportDate;

    public FileControls() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        MenuButton addEntryDropdown = new MenuButton("+");
        addEntryDropdown.setPrefHeight(45);
        MenuItem addEntryItem = new MenuItem("Add entry");
        MenuItem addEntryFromFile = new MenuItem("Add from file...");

        addEntryDropdown.getItems().addAll(addEntryItem, addEntryFromFile);
        addEntryDropdown.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        addEntryItem.setOnAction(e -> new ChemicalEntryWindow());
        addEntryFromFile.setOnAction(e -> FileHandler.getInstance().addEntriesFromFile()); 

        Button generateReportButton = new Button("Generate Report");
        generateReportButton.setPrefHeight(45);
        generateReportButton.setFont(Font.font("Segoe UI", 16));
        generateReportButton.setOnAction(e -> FileHandler.getInstance().generateReport());

        lastReportDate = new Text("Report last generated: Never");
        lastReportDate.setFont(Font.font("Segoe UI", 14));

        getChildren().addAll(addEntryDropdown, generateReportButton, lastReportDate);
    }

    public static void updateReportDate(String reportDate) {
        lastReportDate.setText(reportDate);
    }
}
