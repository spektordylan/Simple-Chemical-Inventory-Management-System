/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This stage class contains the window used to add new entries and display existing entries' information.
 * This class validates all input to ensure duplicates are not added, and manages the editing and deletion of current entries. */

package ui.entry;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import data.ChemicalEntry;
import data.EntryToIconMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.icon.IconGrid;


public class ChemicalEntryWindow extends Stage {
    // fields instantiated here so they can be accessed outside of render() method
    private ChemicalEntry entry;
    private VBox root;
    private GridPane gridPane;
    private final DecimalFormat PRICE_FORMAT = new DecimalFormat("#.00");

    private HBox paneForStateOfMatterButtons = new HBox(10);
    private HBox windowControlButtons = new HBox(10);
    private RadioButton solidRadioButton = new RadioButton("Solid");
    private RadioButton liquidRadioButton = new RadioButton("Liquid");
    private RadioButton gasRadioButton = new RadioButton("Gas");

    
    // Create TextFields
    private TextField nameField = new TextField();
    private TextField formulaField = new TextField();
    private TextField quantityField = new TextField();
    private Label quantityFieldAndUnit = new Label("    ", quantityField);
    private TextField priceField = new TextField();

    // constructor for adding a new entry
    public ChemicalEntryWindow() {
        render();

        // Header label
        Label headerLabel = new Label("Create New Entry");
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button saveAndCloseButton = new Button("Save and Close");
        Button cancelButton = new Button("Cancel");

        root.setAlignment(Pos.CENTER);

        windowControlButtons.getChildren().addAll(saveAndCloseButton, cancelButton);

        cancelButton.setOnAction(e -> close());
        saveAndCloseButton.setOnAction(
            e ->{
                  ChemicalEntry createdEntry = saveEntry();
                  if (createdEntry != null) {
                    IconGrid.getInstance().addIcon(createdEntry);
                    IconGrid.getInstance().filterCurrent(); // done if new entry added that is different from current filter
                    close();
                  }
            });

        // Add header and GridPane to the VBox
        root.getChildren().addAll(headerLabel, gridPane, windowControlButtons);
        
        Scene scene = new Scene(root, 400, 400);
        setScene(scene);
        show();
    }



    // constructor to open existing entry
    public ChemicalEntryWindow(ChemicalEntry entry) {
        this.entry = entry;

        render();
        
        Button saveButton = new Button("Save");
        Button closeButton = new Button("Close");
        Button deleteButton = new Button("Delete");
        
        saveButton.setOnAction(
            e -> {
                ChemicalEntry newEntry = saveEntry();
                if (newEntry != null) {
                    IconGrid.getInstance().removeIcon(this.entry);
                    this.entry = newEntry;
                    IconGrid.getInstance().addIcon(newEntry);
                    IconGrid.getInstance().filterCurrent(); // done if new entry added that is different from current filter
                }
            });
        
        closeButton.setOnAction(e -> close());
        deleteButton.setOnAction(e -> delete());

        windowControlButtons.getChildren().addAll(saveButton, closeButton, deleteButton);

        Label headerLabel = new Label("Entry for " + entry.getName());
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
        Label lastUpdatedLabel = new Label("Last updated: " + dateFormat.format(entry.getDateCreated()));
        lastUpdatedLabel.setStyle("-fx-font-size: 14px");


        // Populate TextFields
        nameField.setText(entry.getName());
        formulaField.setText(entry.getFormula());
        quantityField.setText(String.valueOf(entry.getQuantity()));
        setQuantityUnit();
        priceField.setText(String.valueOf(PRICE_FORMAT.format(entry.getPrice())));


        if (entry.getStateOfMatter().equals("Solid")) {
            solidRadioButton.setSelected(true);
            quantityFieldAndUnit.setText("g");
        } else if (entry.getStateOfMatter().equals("Liquid")) {
            liquidRadioButton.setSelected(true);
            quantityFieldAndUnit.setText("mL");
        } else {
            gasRadioButton.setSelected(true);
            quantityFieldAndUnit.setText("L");
        }

        // Add header and GridPane to the VBox
        root.getChildren().addAll(headerLabel, lastUpdatedLabel, gridPane, windowControlButtons);

        Scene scene = new Scene(root, 400, 400);
        setScene(scene);
    }

    private void setQuantityUnit() {
        if (checkValidNumber(quantityField.getText())) {
            if (getEnteredStateOfMatter() == "Liquid") {
                quantityFieldAndUnit.setText("mL");
            }
            else if (getEnteredStateOfMatter() == "Solid") {
                quantityFieldAndUnit.setText("g");
            }
            else if (getEnteredStateOfMatter() == "Gas") {
                quantityFieldAndUnit.setText("L");
            }
        }
    }



    private boolean checkValidNumber(String input) {
        try {
            // try parsing input as number
            double value = Double.parseDouble(input);
            
            // quantity and price cannot be negative
            return value >= 0;
        } catch (NumberFormatException e) {
            // input is not a valid number
            return false;
        }
    }

    private String getEnteredStateOfMatter() {
        if (solidRadioButton.isSelected()) {
            return "Solid";
        }
        else if (liquidRadioButton.isSelected()) {
            return "Liquid";
        }
        else if (gasRadioButton.isSelected()) {
            return "Gas";
        }
        else{
            return null;
        }
    }

    
    private ChemicalEntry saveEntry() {
        if (!checkValidNumber(quantityField.getText()) || !checkValidNumber(priceField.getText()) || getEnteredStateOfMatter() == null
        || nameField.getText().isBlank() || formulaField.getText().isBlank() || priceField.getText().isBlank() || quantityField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Entry Form Incomplete");
            alert.setContentText("Please make sure the information you have entered is valid and you have filled out all fields.");
            alert.showAndWait();
            return null;
        }

        else {
            ChemicalEntry newEntry = new ChemicalEntry(nameField.getText(), formulaField.getText(), Double.parseDouble(quantityField.getText()), 
            Double.parseDouble(priceField.getText()), getEnteredStateOfMatter());

            if (EntryToIconMap.getEntries().contains(newEntry)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Duplicate Entry");
                alert.setContentText("An entry with the entered information already exists.");
                alert.showAndWait();
                return null;
            } else {
                return newEntry;
            }
        }
    }


    private void render() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        
        gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        quantityField.setPrefColumnCount(11);

        windowControlButtons.setAlignment(Pos.CENTER);

        paneForStateOfMatterButtons.getChildren().addAll(solidRadioButton, liquidRadioButton, gasRadioButton);
        
        ToggleGroup toggleGroup = new ToggleGroup();
        solidRadioButton.setToggleGroup(toggleGroup);
        liquidRadioButton.setToggleGroup(toggleGroup);
        gasRadioButton.setToggleGroup(toggleGroup);

        // Add Labels and TextFields to GridPane
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);

        gridPane.add(new Label("Formula:"), 0, 1);
        gridPane.add(formulaField, 1, 1);

        gridPane.add(new Label("Quantity:"), 0, 2);
        gridPane.add(quantityFieldAndUnit, 1, 2);

        gridPane.add(new Label("Price:"), 0, 3);
        gridPane.add(priceField, 1, 3);

        gridPane.add(new Label("State of Matter:"), 0, 4);
        gridPane.add(paneForStateOfMatterButtons, 1, 4);

        solidRadioButton.setOnAction(
          e -> {
                setQuantityUnit();
          });
        
        liquidRadioButton.setOnAction(
          e -> {
                setQuantityUnit();
          });

        gasRadioButton.setOnAction(
          e -> {
                setQuantityUnit();
          });
    }

    private void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Entry");
        alert.setContentText("Are you sure you want to delete this entry?");
    
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                IconGrid.getInstance().removeIcon(entry);
                close();
            }
        });
    }
}
