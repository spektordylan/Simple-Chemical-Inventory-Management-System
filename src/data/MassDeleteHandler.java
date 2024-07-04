/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This singleton class is a simple handler used to control the mass delete icons feature. */

package data;

import ui.icon.IconGrid;

public class MassDeleteHandler {
    private static MassDeleteHandler instance;

    private boolean deleteMode = false;

    public static MassDeleteHandler getInstance() {
        if (instance == null) {
            instance = new MassDeleteHandler();
        }
        return instance;
    }

    public boolean getDeleteMode() {
        return deleteMode;
    }

    public void toggleDeleteMode() {
        deleteMode = !deleteMode;

        if (!(deleteMode)) {
            IconGrid.getInstance().resetIconColors();
        }
    }
}
