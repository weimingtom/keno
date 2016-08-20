import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

public class TestRepeat {
    public static void main(String[] args) throws IOException {
	Vector data = new Vector();
	String text = getFileText("output.txt");
	Pattern pattern = Pattern.compile(
		"([0-9]*), ([0-9]+)*");
	Matcher matcher = pattern.matcher(text);
	int i = 0;
	while (matcher.find()) {
	    //String str = matcher.group(1) + ", " + matcher.group(2);
	    //System.out.println(str);
	    data.add(i, new DataEntry(i, matcher.group(1), matcher.group(2)));
	    i++;
	}
	Collections.sort(data, new Comparator() {
	    @Override
	    public int compare(Object o1, Object o2) {
		DataEntry d1 = (DataEntry)o1;
		DataEntry d2 = (DataEntry)o2;
		return d1.luckyNoSort.compareTo(d2.luckyNoSort);
	    }
	});
	
	/*
	//C(20, 8)
	Map statistics = new HashMap();
	for(i = 1; i < data.size(); i++) {
	    DataEntry entry0 = (DataEntry) data.get(i - 1);
	    DataEntry entry1 = (DataEntry) data.get(i);
	    if(entry0.luckyNoSort.equals(entry1.luckyNoSort)) {
		Object value = statistics.get(entry0.luckyNoSort);
		if(value == null) {
		    statistics.put(entry0.luckyNoSort, new Integer(2));
		    System.out.println(entry0.luckyNoSort);
		} else {
		    Integer newValue = new Integer((Integer)value + 1);
		    statistics.put(entry0.luckyNoSort, newValue);
		    System.out.println(entry0.luckyNoSort + ", " + newValue);
		}
	    }
	}
	*/
	
	//C(20, 5)
	
	String[] eles = { 
		"01", "02", "03", "04", "05", 
		"06", "07", "08", "09", "10",
		"11", "12", "13", "14", "15",
		"16", "17", "18", "19", "20"
	};
	int[] indices;
	CombinationGenerator x = new CombinationGenerator(eles.length, 5);
	StringBuffer combination;
	i = 0;
	//C(20, 5)内容
	StatEntry[] indexArray = new StatEntry[x.getTotal().intValue()];
	//C(20, 5)索引
	Map indexMap = new HashMap();
	while (x.hasMore()) {
	    combination = new StringBuffer();
	    indices = x.getNext();
	    for (int j = 0; j < indices.length; j++) {
		combination.append(eles[indices[j]]);
	    }
	    //System.out.println(combination.toString());
	    String result = combination.toString();
	    if(indexMap.get(result) != null) {
		throw new IllegalArgumentException();
	    }
	    indexMap.put(result, new Integer(i));
	    indexArray[i] = new StatEntry(i, result);
	    i++;
	}
	//15504
	//System.out.println("totoal:" + i);
	for(i = 0; i < data.size(); i++) {
	    //System.out.println("i"+i);
	    DataEntry entry1 = (DataEntry) data.get(i);
	    //System.out.println("entry1.luckyNoSort:"+entry1.luckyNoSort);
	    String[] luckyElements = getInputNumbers(entry1.luckyNoSort);
	    int[] luckyIndices;
	    CombinationGenerator lucyX = new CombinationGenerator(luckyElements.length, 5);
	    StringBuffer luckyCombination;
	    while (lucyX.hasMore()) {
		luckyCombination = new StringBuffer();
		int[] lucyIndices = lucyX.getNext();
		for (int j = 0; j < lucyIndices.length; j++) {
		    luckyCombination.append(luckyElements[lucyIndices[j]]);
		}
		//System.out.println(combination.toString());
		Object value = indexMap.get(luckyCombination.toString());
		if(value == null) {
		    throw new IllegalArgumentException();
		}
		int ind = (Integer) value;
		StatEntry entry = indexArray[ind];
		entry.dataVec.add(entry1);
	    }
	}
	for(i = 0; i < indexArray.length; i++) {
	    StatEntry entry = indexArray[i];
	    if(entry.dataVec.size() > 0) {
		System.out.println(i + "," + entry);
	    }
	}
    }
    
    public final static String getFileText(String filename) throws IOException {
	FileInputStream reader = new FileInputStream(filename);
	byte[] content = new byte[reader.available()];
	reader.read(content, 0, content.length);
	return new String(content, "gb2312");
    }
    
    public static String[] getInputNumbers(String numbers) {
	int subStringLength = 2;
	String[] ret = new String[8];
	if (numbers.length() != ret.length * subStringLength) {
	    throw new IllegalArgumentException();
	}
	for (int i = 0; i < ret.length; i++) {
	    ret[i] = numbers.substring(i * subStringLength, (i + 1)
		    * subStringLength);
	    // System.out.println(ret[i]);
	}
	return ret;
    }
}
