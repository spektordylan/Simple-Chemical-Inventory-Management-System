/* Dylan Spektor
 * CSC 1061 - Computer Science II (java)
 * This ChemicalEntry class is used to store and retrieve information about added chemicals and 
 * is the main data object in the application.*/

package data;

import java.util.Date;
import java.util.Objects;

public class ChemicalEntry {
    private String name;
    private String formula;
    private double quantity;
    private double price;
    private String stateOfMatter;
    private Date dateCreated;
    
    public ChemicalEntry(String name, String formula, double quantity, double price, String stateOfMatter) {
        this.name = name;
        this.formula = formula;
        this.quantity = quantity;
        this.price = price;
        this.stateOfMatter = stateOfMatter;
        dateCreated = new Date(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public String getFormula() {
        return formula;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getStateOfMatter() {
        return stateOfMatter;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof ChemicalEntry)) 
            return false;

        ChemicalEntry other = (ChemicalEntry) o;
        return  name.equals(other.name) && formula.equals(other.formula) &&
                quantity == other.quantity && price == other.price &&
                stateOfMatter.equals(other.stateOfMatter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, formula, quantity, price, stateOfMatter);
    }
}

