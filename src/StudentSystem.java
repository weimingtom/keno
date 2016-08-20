import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class StudentSystem extends JFrame {
    private String[] columnNames = { "ID", "姓名", "学号", "分数" };
    private Object[][] rowData = { { "001", "卞炎安", "200753225130", "95" },
	    { "002", "周天宇", "200753225112", "98"

	    }, { "003", "陆登基", "200753225132", "99" },
	    { "004", "陈少", "200753225132", "99" },
	    { "005", "陆挺", "200753225132", "99" },
	    { "006", "啊为", "200753225132", "99" },
	    { "007", "小滔滔", "200753225133", "99" },

	    { "008", "郭楠", "200753225132", "99" },
	    { "009", "营长", "200753225132", "99" },
	    { "010", "子论", "200753225132", "99" }

    };

    private JTable jTable = new JTable(rowData, columnNames);

    private JSpinner jspiRowHeight = new JSpinner(new SpinnerNumberModel(16, 1,
	    50, 1));

    private JSpinner jspiRowMargin = new JSpinner(new SpinnerNumberModel(1, 1,
	    50, 1));

    private JCheckBox jchkShowGrid = new JCheckBox("showGrid", true);

    private JComboBox jcboAutoResizeMode = new JComboBox(new String[] {
	    "AUTO_RESIZE_OFF", "AUTO_RESIZE_LAST_COLUMN",
	    "AUTO_RESIZE_SUBSEQUENT_COLUMNS", "AUTO_RESIZE_NEXT_COLUMN",
	    "AUTO_RESIZE_ALL_COLUMNS" });

    public StudentSystem() {
	super("学籍管理系统");
	JPanel panel1 = new JPanel();
	panel1.add(new JLabel("行高"));
	panel1.add(jspiRowHeight);
	panel1.add(new JLabel("行间距"));
	panel1.add(jspiRowMargin);
	panel1.add(jchkShowGrid);

	JPanel panel2 = new JPanel();
	panel2.add(new JLabel("列调整方式"));
	panel2.add(jcboAutoResizeMode);

	add(panel1, BorderLayout.SOUTH);
	add(panel2, BorderLayout.NORTH);
	add(new JScrollPane(jTable));
	setSize(500, 300);
	setVisible(true);

	jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	jTable.setGridColor(Color.BLUE);
	jTable.setSelectionBackground(Color.RED);
	jTable.setSelectionForeground(Color.WHITE);

	jspiRowHeight.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		jTable.setRowHeight(((Integer) (jspiRowHeight.getValue()))
			.intValue());
	    }
	});

	jspiRowMargin.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		jTable.setRowMargin(((Integer) (jspiRowMargin.getValue()))
			.intValue());

	    }
	});

	jchkShowGrid.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		jTable.setShowGrid(jchkShowGrid.isSelected());
	    }
	});

	jcboAutoResizeMode.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String selectedItem = (String) jcboAutoResizeMode
			.getSelectedItem();

		if (selectedItem.equals("AUTO_RESIZE_OFF"))
		    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		else if (selectedItem.equals("AUTO_RESIZE_LAST_COLUMN"))
		    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		else if (selectedItem.equals("AUTO_RESIZE_SUBSEQUENT_COLUMNS"))
		    jTable
			    .setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		else if (selectedItem.equals("AUTO_RESIZE_NEXT_COLUMN"))
		    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

		else if (selectedItem.equals("AUTO_RESIZE_ALL_COLUMNS"))
		    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    }
	});
    }

    public static void main(String[] args) {
	new StudentSystem();
    }
}
