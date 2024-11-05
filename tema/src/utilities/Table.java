package utilities;

import java.util.ArrayList;

public class Table {

    public ArrayList<ArrayList<Card>> table;

    public ArrayList<ArrayList<Card>> getTable() {
        return table;
    }

    public void setTable(ArrayList<ArrayList<Card>> table) {
        this.table = table;
    }

    public Table() {
        this.table = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.table.add(new ArrayList<>());
        }
    }
}
