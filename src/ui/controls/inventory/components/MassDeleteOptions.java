/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This class contains the UI control for deleting multiple entries, and communicates with the MassDeleteHandler. */

package ui.controls.inventory.components;

import data.MassDeleteHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import ui.icon.IconGrid;

public class MassDeleteOptions extends HBox {
    public MassDeleteOptions() {
        setSpacing(5);
 
        Button deleteEntries = new Button("Delete Multiple Entries");
        Button doneSelectingToDelete = new Button("Done");
        Button cancelDeleting = new Button("Cancel");

        deleteEntries.setFont(Font.font("Segoe UI", 16));
        doneSelectingToDelete.setFont(Font.font("Segoe UI", 16));
        cancelDeleting.setFont(Font.font("Segoe UI", 16));

        doneSelectingToDelete.setVisible(false);
        cancelDeleting.setVisible(false);

        deleteEntries.setOnAction(
            e -> {
                  if (!(MassDeleteHandler.getInstance().getDeleteMode())) {
                    MassDeleteHandler.getInstance().toggleDeleteMode();
                    doneSelectingToDelete.setVisible(true);
                    cancelDeleting.setVisible(true);
                  }
            }
        );

        
        doneSelectingToDelete.setOnAction(
            e -> { 
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Action");
                alert.setHeaderText("Mass Delete Entries");
                alert.setContentText("Are you sure you want to mass delete the selected entries? This action cannot be undone.");
            
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        IconGrid.getInstance().massDeleteEntries();
                        MassDeleteHandler.getInstance().toggleDeleteMode();
                        doneSelectingToDelete.setVisible(false);
                        cancelDeleting.setVisible(false);
                    }
                });
            }
        );

        cancelDeleting.setOnAction(
            e -> {
              MassDeleteHandler.getInstance().toggleDeleteMode();
              doneSelectingToDelete.setVisible(false);
              cancelDeleting.setVisible(false);
            }
        );

        getChildren().addAll(deleteEntries, doneSelectingToDelete, cancelDeleting);
    }
}