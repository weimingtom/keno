import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class JDBCTable extends Frame {
    private static final long serialVersionUID = 1L;

    AbstractTableModel tm;
    JTable table;
    JScrollPane scrollpane;
    String[] titles;
    Vector records;
    
    public void init() {
	records = new Vector();
	tm = new AbstractTableModel() {
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
	};
    }
    
    private String[] myTitles = {
	    "1", "2", "3"
    };
    
    public void start() {
	titles = new String[myTitles.length];
	for(int i = 0; i < myTitles.length; i++) {
	    titles[i] = myTitles[i];
	}
	Vector recVector = new Vector();
	for(int i = 0; i < titles.length; i++) {
	    recVector.addElement("hello");
	}
	records.addElement(recVector);
	table = new JTable(tm);
	table.setToolTipText("显示全部查询结果");
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.setCellSelectionEnabled(false);
	table.setShowVerticalLines(true);
	table.setShowHorizontalLines(true);
	scrollpane = new JScrollPane(table);
	this.add(scrollpane);
	tm.fireTableStructureChanged();
    }
    
    public static void main(String[] args) {
	JDBCTable f = new JDBCTable();
	f.init();
	f.start();
	f.setSize(400, 300);
	f.setTitle("Show Database in JTable");
	f.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	f.setVisible(true);
    }
}
