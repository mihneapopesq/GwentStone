package utilities;

import java.util.ArrayList;

/**
 * Represents the game table having 4 rows with maximum 5 cards each
 */
public class Table {

    /**
     * The rows of cards on the table
     */
    private ArrayList<ArrayList<Card>> table;

    /**
     * Constructs an empty game table with 4 rows
     */
    public Table() {
        this.table = new ArrayList<>();
        for (int i = 0; i < ConstantsConfig.MAX_ROWS; i++) {
            this.table.add(new ArrayList<>());
        }
    }

    /**
     * Gets the table with all rows of cards
     *
     * @return the table as an ArrayList of ArrayLists of cards
     */
    public ArrayList<ArrayList<Card>> getTable() {
        return table;
    }

    /**
     * Sets the table with new rows of cards
     *
     * @param table the new table to set
     */
    public void setTable(final ArrayList<ArrayList<Card>> table) {
        this.table = table;
    }
}
