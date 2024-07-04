/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This singleton IconGrid class provides the main UI element that displays, searches, and filters 
 * the ChemicalIcons. It also manages adding/removing objects from the EntryToIconMap. */

package ui.icon;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import data.ChemicalEntry;
import data.EntryToIconMap;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class IconGrid extends ScrollPane {
    private static IconGrid instance; // singleton instance

    private FlowPane root = new FlowPane();
    private boolean useFormulaForIcons = true;
    private double SMALL_ICON_SIZE = 80;
    private double MEDIUM_ICON_SIZE = 105;
    private double LARGE_ICON_SIZE = 150;
    private double iconSize = MEDIUM_ICON_SIZE;

    private String filterState = null; 

    private IconGrid() {
        root.setPadding(new Insets(30, 25, 30, 25));
        setContent(root);
        setFitToWidth(true);
        setFitToHeight(true);
        setStyle("-fx-focus-color: transparent");
    }

    public static IconGrid getInstance() {
        if (instance == null) {
            instance = new IconGrid();
        }
        return instance;
    }

    public void addIcon(ChemicalEntry entry) {
        ChemicalIcon icon = new ChemicalIcon(entry, iconSize, useFormulaForIcons);

        EntryToIconMap.associate(entry, icon);

        // insert most recent one at beginning
        root.getChildren().add(0, icon);
    }

    
    public void removeIcon(ChemicalEntry entry) {
        root.getChildren().remove(EntryToIconMap.getAssociatedIcon(entry));
        EntryToIconMap.deleteEntry(entry);
    }

    public void setIconSize(String sizeOption) {
        boolean oldUseFormulaForIcons = useFormulaForIcons;

        if (sizeOption.equals("Small")) {
            iconSize = SMALL_ICON_SIZE;
            useFormulaForIcons = true;
        }
        else if (sizeOption.equals("Medium")) {
            iconSize = MEDIUM_ICON_SIZE;
            useFormulaForIcons = true;
        }
        else {
            iconSize = LARGE_ICON_SIZE;
            useFormulaForIcons = false;
        }

        for (ChemicalIcon icon : EntryToIconMap.getIcons()) {
            icon.setIconSize(iconSize);
        }

        if (oldUseFormulaForIcons != useFormulaForIcons) {
            updateIconLabels();
        }
    }

    public String getIconSize() {
        if (iconSize == SMALL_ICON_SIZE) {
            return "Small";
        }
        else if (iconSize == MEDIUM_ICON_SIZE) {
            return "Medium";
        }
        else {
            return "Large";
        }
    }

    private void updateIconLabels() {
        for (ChemicalIcon icon : EntryToIconMap.getIcons()) {
            icon.updateDisplayedInfo(useFormulaForIcons);
        }
    }

    public boolean getIconDisplaySetting() {
        return useFormulaForIcons;
    }


    public void searchGrid(String query) {
        for (ChemicalEntry entry : EntryToIconMap.getEntries()) {
            ChemicalIcon icon = EntryToIconMap.getAssociatedIcon(entry);
            if (!(entry.getFormula().toLowerCase().contains(query) || entry.getName().toLowerCase().contains(query))) {
                root.getChildren().remove((icon));
            } else if (!root.getChildren().contains(icon) 
                      && (filterState == null || entry.getStateOfMatter() == filterState)) {
                root.getChildren().add(0, icon);
            }
        }
    }

    public void filterSolids() {
        filterState = "Solid";
        filterCurrent();
    }

    public void filterLiquids() {
        filterState = "Liquid";
        filterCurrent();
    }

    public void filterGases() {
        filterState = "Gas";
        filterCurrent();
    }

    public void filterCurrent() {
        if (filterState != null) {
            root.getChildren().clear();
            for (ChemicalIcon icon : EntryToIconMap.getIcons()) {
                if (!(icon.getEntry().getStateOfMatter().equals(filterState))) {
                    root.getChildren().remove(icon);
                } else if (!(root.getChildren().contains(icon))) {
                    root.getChildren().add(0, icon);
                }
            }
        } else {
            resetGrid();
        }
    }
    
    public void resetGrid() {
        root.getChildren().clear();
        for (ChemicalIcon icon : EntryToIconMap.getIcons()) {
            root.getChildren().add(0, icon);
        }
        filterState = null;
    }

    public void resetIconColors() {
        for (ChemicalIcon icon : EntryToIconMap.getIcons()) {
            icon.resetColor();
            icon.resetDeleteState();
        }
    }

    public void massDeleteEntries() {
        List<ChemicalEntry> entriesToDelete = new ArrayList<>();

        for (ChemicalIcon icon : EntryToIconMap.getIcons()) {
            if (icon.getDeleteState()) {
                entriesToDelete.add(icon.getEntry());
            }
        }

        for (ChemicalEntry entry : entriesToDelete) {
            removeIcon(entry);
        }
    }

    public Set<ChemicalEntry> getVisibleEntries () {
        Set<ChemicalEntry> visibleEntries = new LinkedHashSet<>();

        for (Node icon : root.getChildren()) {
            visibleEntries.add(((ChemicalIcon)icon).getEntry());
        }

        return visibleEntries;
    }
}