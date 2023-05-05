package com.ExcelToMySQLConverter.Beans;

import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;

public class ExcelTable {
    private HashMap<String, Vector<Object>> table;

    public ExcelTable(HashMap<String, Vector<Object>> table) {
        this.table = table;
    }

    public HashMap<String, Vector<Object>> getTable() {
        return table;
    }

    public void setTable(HashMap<String, Vector<Object>> table) {
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelTable that = (ExcelTable) o;
        return Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }
}
