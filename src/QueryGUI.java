import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class QueryGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private String[] myTitles1 = { "序号", "时间和期数", "幸运号码" };
    private KenoTableModel tm1;
    private String[] titles1;
    private Vector records1;
    private JTable jTable1;
    private JScrollPane scrollpane1;

    private String[] myTitles2 = { "序号", "20选5组合", "重复次数" };
    private KenoTableModel tm2;
    private String[] titles2;
    private Vector records2;
    private JTable jTable2;
    private JScrollPane scrollpane2;

    private String[] myTitles3 = { "序号", "时间", "间隔"/*"全部幸运号码"*/ };
    private KenoTableModel tm3;
    private String[] titles3;
    private Vector records3;
    private JTable jTable3;
    private JScrollPane scrollpane3;
    
    private Vector data = new Vector();
    private StatEntry[] indexArray;
    private Map indexMap;
    
    public QueryGUI() {
	super("快乐十分彩票");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	// this.setSize(500, 500);

	JPanel panel1 = new JPanel();
	panel1.add(new JLabel("历史数据"));
	
	JPanel panel2 = new JPanel();
	JButton button1 = new JButton("读取数据");
	button1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (data.size() > 0) {
		    data.clear();
		}
		if (records1.size() > 0) {
		    records1.clear();
		}
		fillHistoryData(records1);
		tm1.fireTableDataChanged();
	    }
	});
	panel2.add(button1);
	JButton button2 = new JButton("计算重复组合");
	button2.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (records2.size() == 0) {
		    if (data.size() == 0) {
			fillHistoryData(records1);
			tm1.fireTableDataChanged();
		    }
		    fillStatData(records2);
		    tm2.fireTableDataChanged();
		}
	    }
	});
	panel2.add(button2);
	panel2.add(new JLabel("(c)版权所有"));
	panel2.add(new JLabel("All rights reserved"));



	this.add(panel1, BorderLayout.NORTH);
	this.add(panel2, BorderLayout.SOUTH);

	JPanel panel3 = new JPanel();
	// panel3.setSize(500, 500);

	panel3.setLayout(new GridLayout(1, 3));
	initTM1();
	panel3.add(scrollpane1);
	initTM2();
	panel3.add(scrollpane2);
	initTM3();
	panel3.add(scrollpane3);

	this.add(panel3, BorderLayout.CENTER);

	// this.pack();
	this.setSize(800, 400);
	this.setVisible(true);
    }

    private void initTM1() {
	records1 = new Vector();
	titles1 = new String[myTitles1.length];
	for (int i = 0; i < myTitles1.length; i++) {
	    titles1[i] = myTitles1[i];
	}
	// fillHistoryData(records1);
	tm1 = new KenoTableModel(titles1, records1);
	tm1.fireTableStructureChanged();
	jTable1 = new JTable(tm1);
	jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	jTable1.setSelectionForeground(Color.WHITE);
	scrollpane1 = new JScrollPane(jTable1);
    }

    private void initTM2() {
	records2 = new Vector();
	titles2 = new String[myTitles2.length];
	for (int i = 0; i < myTitles2.length; i++) {
	    titles2[i] = myTitles2[i];
	}
	// fillStatData(records2);
	tm2 = new KenoTableModel(titles2, records2);
	tm2.fireTableStructureChanged();
	jTable2 = new JTable(tm2);
	jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	jTable2.setSelectionForeground(Color.WHITE);
	scrollpane2 = new JScrollPane(jTable2);
	jTable2.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		//System.out.println(jTable2.getSelectedRow());
		records3.clear();
		fillRepeatData(records3, jTable2.getSelectedRow());
		//tm3.fireTableStructureChanged();
		tm3.fireTableDataChanged();
	    }
	});
    }

    private void initTM3() {
	records3 = new Vector();
	titles3 = new String[myTitles3.length];
	for (int i = 0; i < myTitles3.length; i++) {
	    titles3[i] = myTitles3[i];
	}
	// fillStatData(records2);
	tm3 = new KenoTableModel(titles3, records3);
	tm3.fireTableStructureChanged();
	jTable3 = new JTable(tm3);
	jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	jTable3.setSelectionForeground(Color.WHITE);
	scrollpane3 = new JScrollPane(jTable3);
    }
    
    // TODO:测试用
    private void fillTestData(Vector rec) {
	Vector recVector = new Vector();
	for (int i = 0; i < titles1.length; i++) {
	    recVector.addElement("hello");
	}
	rec.addElement(recVector);
    }

    private void fillHistoryData(Vector rec) {
	// Vector data = new Vector();
	String text;
	try {
	    text = getFileText("output.txt");
	    Pattern pattern = Pattern.compile("([0-9]*), ([0-9]+)*");
	    Matcher matcher = pattern.matcher(text);
	    int i = 0;
	    while (matcher.find()) {
		data.add(i,
			new DataEntry(i, matcher.group(1), matcher.group(2)));
		i++;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	for (int i = 0; i < data.size(); i++) {
	    Vector recVector = new Vector();
	    DataEntry entry = (DataEntry) data.elementAt(i);
	    recVector.addElement(Integer.toString(i));
	    recVector.addElement(entry.time);
	    recVector.addElement(entry.luckyNo);
	    rec.addElement(recVector);
	}
    }

    private void fillStatData(Vector rec) {
	String[] eles = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
		"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
		"20" };
	int[] indices;
	CombinationGenerator x = new CombinationGenerator(eles.length, 5);
	StringBuffer combination;
	int i = 0;
	// C(20, 5)内容
	indexArray = new StatEntry[x.getTotal().intValue()];
	// C(20, 5)索引
	indexMap = new HashMap();
	while (x.hasMore()) {
	    combination = new StringBuffer();
	    indices = x.getNext();
	    for (int j = 0; j < indices.length; j++) {
		combination.append(eles[indices[j]]);
	    }
	    // System.out.println(combination.toString());
	    String result = combination.toString();
	    if (indexMap.get(result) != null) {
		throw new IllegalArgumentException();
	    }
	    indexMap.put(result, new Integer(i));
	    indexArray[i] = new StatEntry(i, result);
	    i++;
	}
	// 15504
	// System.out.println("totoal:" + i);
	for (i = 0; i < data.size(); i++) {
	    // System.out.println("i"+i);
	    DataEntry entry1 = (DataEntry) data.get(i);
	    // System.out.println("entry1.luckyNoSort:"+entry1.luckyNoSort);
	    String[] luckyElements = getInputNumbers(entry1.luckyNoSort);
	    int[] luckyIndices;
	    CombinationGenerator lucyX = new CombinationGenerator(
		    luckyElements.length, 5);
	    StringBuffer luckyCombination;
	    while (lucyX.hasMore()) {
		luckyCombination = new StringBuffer();
		int[] lucyIndices = lucyX.getNext();
		for (int j = 0; j < lucyIndices.length; j++) {
		    luckyCombination.append(luckyElements[lucyIndices[j]]);
		}
		// System.out.println(combination.toString());
		Object value = indexMap.get(luckyCombination.toString());
		if (value == null) {
		    throw new IllegalArgumentException();
		}
		int ind = (Integer) value;
		StatEntry entry = indexArray[ind];
		entry.dataVec.add(entry1);
	    }
	}
	for (i = 0; i < indexArray.length; i++) {
	    StatEntry entry = indexArray[i];
	    Vector recVector = new Vector();
	    recVector.addElement(Integer.toString(i));
	    recVector.addElement(entry.numbers);
	    recVector.addElement(Integer.toString(entry.dataVec.size()));
	    rec.addElement(recVector);
	}
    }
    
    private void fillRepeatData(Vector rec, int index) {
	if(indexArray == null || indexMap == null) {
	    throw new IllegalArgumentException();
	}
	StatEntry entry = indexArray[index];
	Vector dataVec = entry.dataVec;
	int prev = 0;
	for (int i = 0; i < dataVec.size(); i++) {
	    DataEntry dataEntry = (DataEntry) dataVec.elementAt(i);
	    Vector recVector = new Vector();
	    //recVector.addElement(Integer.toString(i));
	    recVector.addElement(Integer.toString(dataEntry.id));
	    recVector.addElement(dataEntry.time);
	    //recVector.addElement(dataEntry.luckyNo);
	    recVector.addElement(Integer.toString(dataEntry.id - prev));
	    prev = dataEntry.id; 
	    rec.addElement(recVector);
	}
    }

    private String getFileText(String filename) throws IOException {
	FileInputStream reader = new FileInputStream(filename);
	byte[] content = new byte[reader.available()];
	reader.read(content, 0, content.length);
	return new String(content, "gb2312");
    }

    private String[] getInputNumbers(String numbers) {
	int subStringLength = 2;
	String[] ret = new String[8];
	if (numbers.length() != ret.length * subStringLength) {
	    throw new IllegalArgumentException();
	}
	for (int i = 0; i < ret.length; i++) {
	    ret[i] = numbers.substring(i * subStringLength, (i + 1)
		    * subStringLength);
	}
	return ret;
    }

    public static void main(String[] args) {
	new QueryGUI();
    }
}
