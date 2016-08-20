import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class KenoTableModel extends AbstractTableModel {
    private String[] titles;
    private Vector records;
    
    public KenoTableModel(String[] titles, Vector records) {
	this.titles = titles;
	this.records = records;
    }
    
    public int getColumnCount() {
	return titles.length;
    }

    @Override
    public int getRowCount() {
	return records.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
	if(!records.isEmpty()) {
	    return ((Vector)records.elementAt(row)).elementAt(column);
	} else {
	    return null;
	}
    }
    
    @Override
    public String getColumnName(int column) {
	return titles[column];
    }
  
    @Override
    public void setValueAt(Object value, int row, int column) {
	//
    }
    
    @Override
    public Class getColumnClass(int c) {
	return getValueAt(0, c).getClass();
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }
}
