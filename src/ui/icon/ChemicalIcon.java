/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This ChemicalIcon class contains the main UI element used in the IconGrid to represent ChemicalEntry objects.*/

package ui.icon;

import java.util.Objects;

import data.ChemicalEntry;
import data.MassDeleteHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ui.entry.ChemicalEntryWindow;

public class ChemicalIcon extends Button {
    private Text label;
    private Rectangle icon;
    private boolean setToDelete = false;
    private Color normalColor;
    private ChemicalEntry entry;

    private final int MAX_DISPLAYED_FORMULA_LENGTH = 13;
    private final int MAX_DISPLAYED_NAME_LENGTH = 39;

    public ChemicalIcon(ChemicalEntry entry, double iconSize, boolean useFormulaForIcons) {
        this.entry = entry;
        label = new Text();

        icon = new Rectangle(iconSize, iconSize);
        icon.setArcWidth(iconSize / 3);
        icon.setArcHeight(iconSize / 3);
        icon.setStroke(Color.BLACK);

        updateDisplayedInfo(useFormulaForIcons);

        label.setWrappingWidth(iconSize);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Segoe UI", 16));

        if (entry.getStateOfMatter().equals("Solid")) {
            normalColor = Color.LIGHTGRAY;
            
        } else if (entry.getStateOfMatter().equals("Liquid")) {
            normalColor = Color.LIGHTGOLDENRODYELLOW;
        } else {
            normalColor = Color.DARKSEAGREEN;
        }
        icon.setFill(normalColor);

        setGraphic(new StackPane(icon, label));

        setBackground(Background.EMPTY);
        setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.EMPTY)));
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setFocusTraversable(false);

        setOnMouseClicked(e -> {
            if (!(MassDeleteHandler.getInstance().getDeleteMode())) {
                ChemicalEntryWindow entryPage = new ChemicalEntryWindow(entry);
                entryPage.show();
            }
            else if (setToDelete == false) {
                setToDelete = true;
                icon.setFill(Color.RED);
            }
            else {
                setToDelete = false;
                resetColor();
            }
        });
    }

    // this is specifically done to include the "..." so the formulas arent cut off in a way that
    // creates a whole different formula, which can be misleading
    // this is public because IconGrid uses it
    public void updateDisplayedInfo(boolean useFormulaForIcons) {
        if (useFormulaForIcons) {
            if (entry.getFormula().length() >= MAX_DISPLAYED_FORMULA_LENGTH) {
                label.setText(entry.getFormula().substring(0, MAX_DISPLAYED_FORMULA_LENGTH) + "...");
            }
            else { // necessary to avoid StringIndexOutOfBoundsException
                label.setText(entry.getFormula());
            }
        }
        else {
            if (entry.getName().length() >= MAX_DISPLAYED_NAME_LENGTH) {
                label.setText(entry.getName().substring(0, MAX_DISPLAYED_NAME_LENGTH) + "...");
            }
            else {
                label.setText(entry.getName());
            }
        }
    }

    public void setIconSize(double iconSize) {
        icon.setWidth(iconSize);
        icon.setHeight(iconSize);
        label.setWrappingWidth(iconSize);
    }

    public boolean getDeleteState() {
        return setToDelete;
    }

    public void resetDeleteState() {
        setToDelete = false;
    }

    public void resetColor() {
        icon.setFill(normalColor);
    }

    public ChemicalEntry getEntry() {
        return entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof ChemicalIcon)) 
            return false;

        ChemicalIcon other = (ChemicalIcon) o;
        return  entry.equals(other.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entry);
    }
}
