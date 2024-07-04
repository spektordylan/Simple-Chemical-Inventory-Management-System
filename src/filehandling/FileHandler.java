/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This FileHandler class performs report generation and appending as well as adding entries from a file. */

package filehandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import data.ChemicalEntry;
import data.EntryToIconMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ui.controls.header.components.FileControls;
import ui.icon.IconGrid;

public class FileHandler {
    private static FileHandler instance;

    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    private Date lastReportDate;
    private SimpleDateFormat reportDateFormat;
    private SimpleDateFormat timeDateFormat;
    private FileChooser addEntryFileChooser;
    private FileChooser reportFileChooser;
    private final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("0.00");

    private FileHandler () {
        reportDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        timeDateFormat = new SimpleDateFormat("hh:mm a");

        addEntryFileChooser = new FileChooser();
        reportFileChooser = new FileChooser();    

        String userHome = System.getProperty("user.home");
        File documentsDirectory = new File(userHome, "Documents");
        reportFileChooser.setInitialDirectory(documentsDirectory);

        ExtensionFilter extensionFilter = new ExtensionFilter("Text Files (*.txt)", "*.txt");
        
        // Set extension filter to file chooser
        addEntryFileChooser.getExtensionFilters().add(extensionFilter);
        reportFileChooser.getExtensionFilters().add(extensionFilter);
    }
    

    private String getLastReportDate() {
        return reportDateFormat.format(lastReportDate);
    }

    public void addEntriesFromFile() {
        int entriesAdded = 0;
        File file = addEntryFileChooser.showOpenDialog(new Stage());
        if (file != null && file.getName().endsWith(".txt")) {   
            int loopCount = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null && loopCount < 1000) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String name = line.trim();
                    String formula = reader.readLine().trim();
                    double quantity = Double.parseDouble(reader.readLine().trim());
                    double price = Double.parseDouble(reader.readLine().trim());
                    String stateOfMatter = reader.readLine().trim();

                    if (stateOfMatter.toLowerCase().startsWith("s")) {
                        stateOfMatter = "Solid";
                    }
                    else if (stateOfMatter.toLowerCase().startsWith("l")) {
                        stateOfMatter = "Liquid";
                    } else if (stateOfMatter.toLowerCase().startsWith("g")) {
                        stateOfMatter = "Gas";
                    } else {
                        continue;
                    }

                    ChemicalEntry entry = new ChemicalEntry(name, formula, quantity, price, stateOfMatter);
                    if (!(EntryToIconMap.getEntries().contains(entry))) {
                        IconGrid.getInstance().addIcon(entry);
                        entriesAdded++;
                    }

                    loopCount++;
                }

                if (entriesAdded == 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No Entries Added");
                    alert.setContentText("No valid entries were found in the file. Please make sure it is formatted correctly.");
                    alert.showAndWait(); 
                }
                else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(entriesAdded + " Entries Added");
                    alert.setContentText("Successfully added " + entriesAdded + " entries to chemical inventory.");
                    alert.showAndWait(); 
                }
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("File Reading Error");
                alert.setContentText("We ran into an error while processing the file. Please make sure it is formatted correctly and does not contain duplicate entries.");
                alert.showAndWait(); 

                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Entries Added");
                alert2.setHeaderText(entriesAdded + " Entries Added");
                alert2.setContentText("The application was able to parse " + entriesAdded + " valid entries from the file.");
                alert2.showAndWait(); 
            }
        }
    }


    public void generateReport() {
        Date currentDate = new Date(System.currentTimeMillis());

        if (lastReportDate != null
        && reportDateFormat.format(currentDate).equals(getLastReportDate())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Append Existing Report");
            alert.setHeaderText("Report Already Created Today");
            alert.setContentText("A report has already been created today. Would you like to append the latest changes to the existing report?");
            
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            ButtonType cancelButton = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    appendExistingReport(currentDate);
                } else if (response == noButton) {
                    generateNewReport(currentDate);
                }
            });  
        } else {
            generateNewReport(currentDate);
        }
    }


    private void generateNewReport(Date currentDate) {
        reportFileChooser.setInitialFileName("Chemical Inventory Report " + reportDateFormat.format(currentDate) + ".txt");
        File reportFile = reportFileChooser.showSaveDialog(null);

        if (reportFile == null) {
            return;
        }
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile));
            
            writer.write("Chemical Inventory Report " + reportDateFormat.format(currentDate));

            generateReportInformation(writer);

            lastReportDate = currentDate;

            FileControls.updateReportDate("Report last updated: " + getLastReportDate());

            writer.close();
        }
        catch (Exception e) {
            errorGeneratingReport();
        }
    }


    private void appendExistingReport(Date currentDate) {
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Select Existing Report");
        alert2.setHeaderText("Select Existing Report");
        alert2.setContentText("Please select the existing report file from today.");
        ButtonType okButton = new ButtonType("OK");
        alert2.getButtonTypes().setAll(okButton);

        alert2.showAndWait().ifPresent(newresponse -> {
            File file = reportFileChooser.showOpenDialog(new Stage());

            if (file != null && file.getName().endsWith(".txt")) {
                try {
                    BufferedReader firstLineReader = new BufferedReader(new FileReader(file));

                    if (!(firstLineReader.readLine().equals("Chemical Inventory Report " + reportDateFormat.format(currentDate)))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect Report Selected");
                        alert.setContentText("The selected file was not created today based on its header. Please select today's report.");
                        alert.showAndWait(); 
                        firstLineReader.close();
                    }
                    else {
                        firstLineReader.close();
                        
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                        writer.write("\n\nThe above report was generated at " + timeDateFormat.format(lastReportDate));

                        writer.write("\nThe information below represents the current inventory at " + timeDateFormat.format(currentDate));

                        generateReportInformation(writer);

                        lastReportDate = currentDate;

                        writer.close();
                    }
                } catch (Exception e) {
                    errorGeneratingReport();
                }
            }
        });
    }


    private void generateReportInformation(BufferedWriter writer) throws Exception {
        Set<ChemicalEntry> inventory = IconGrid.getInstance().getVisibleEntries();
        writer.write("\n\nTotal Items in Inventory: " + inventory.size());

        List<String> chemicalList = new ArrayList<>();
        
        double totalPrice = 0;
        double solidQuantity = 0;
        double liquidQuantity = 0;
        double gasQuantity = 0;

        boolean useKilograms = false;
        boolean useLiters = false;

        for (ChemicalEntry entry : inventory) {
            totalPrice = totalPrice + entry.getPrice();

            if (entry.getStateOfMatter().equals("Solid")) {
                solidQuantity = solidQuantity + entry.getQuantity();
            }
            else if (entry.getStateOfMatter().equals("Liquid")) {
                liquidQuantity = liquidQuantity + entry.getQuantity();
            } else {
                gasQuantity = gasQuantity + entry.getQuantity();
            }

            chemicalList.add(entry.getName() + " (" + entry.getStateOfMatter() + ")");
        }

        if (solidQuantity >= 1000) {
            solidQuantity = solidQuantity / 1000;
            useKilograms = true;
        }
        if (liquidQuantity >= 1000) {
            liquidQuantity = liquidQuantity / 1000;
            useLiters = true;
        }

        String solidUnits = (useKilograms) ? " kg" : " g";
        String liquidUnits = (useLiters) ? " L" : " mL";

        writer.write("\nTotal Price of Inventory: " + DOUBLE_FORMAT.format(totalPrice));
        writer.write("\nTotal Quantity of Solids: " + DOUBLE_FORMAT.format(solidQuantity) + solidUnits);
        writer.write("\nTotal Quantity of Liquids: " + DOUBLE_FORMAT.format(liquidQuantity) + liquidUnits);
        writer.write("\nTotal Quantity of Gases: " + DOUBLE_FORMAT.format(gasQuantity) + " L");


        writer.write("\n\nChemicals in Inventory:");

        int entriesAdded = 0;

        for (String chemical : chemicalList) {
            writer.write("\n" + chemical);
            entriesAdded++;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Report Successfully Generated");
        alert.setContentText("Report information generated for " + entriesAdded + " entries.");
        alert.showAndWait(); 
    }

    
    private void errorGeneratingReport() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Report Error");
        alert.setContentText("There was an error when generating the report.");
        alert.showAndWait(); 
    }
}