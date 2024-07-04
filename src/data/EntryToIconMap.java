/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This EntryToIconMap class is the main data structure used to link ChemicalEntry and ChemicalIcon objects together.
 * It performs the addition and deletion of objects from the program. A linked hashmap is used to allow for sorting by 
 * date in the IconGrid. */

package data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import ui.icon.ChemicalIcon;

public class EntryToIconMap {
    private static HashMap<ChemicalEntry, ChemicalIcon> entryIconAssociation = new LinkedHashMap<>();

    private EntryToIconMap() {};

    public static void associate(ChemicalEntry entry, ChemicalIcon icon) {
        entryIconAssociation.put(entry, icon);
    }

    public static ChemicalIcon getAssociatedIcon(ChemicalEntry entry) {
        return entryIconAssociation.get(entry);
    }

    public static Set<ChemicalEntry> getEntries() {
        return entryIconAssociation.keySet();
    }

    public static Set<ChemicalIcon> getIcons() {
        return new LinkedHashSet<>(entryIconAssociation.values());
    }

    public static void deleteEntry(ChemicalEntry entry) {
        entryIconAssociation.remove(entry);
    }
}