import java.util.Arrays;


public class DataEntry {
    public int id;
    public String time;
    public String luckyNo;
    public String luckyNoSort;
    
    public DataEntry(int id, String time, String luckyNo) {
	this.id = id;
	this.time = time;
	this.luckyNo = luckyNo;
	this.luckyNoSort = getSortNumbers(luckyNo);
    }
    
    @Override
    public String toString() {
	return "{id = "+id+", time = " + time + ", luckyNo = " + luckyNo + "}";
    }
    
    public static String getSortNumbers(String numbers) {
	int subStringLength = 2;
	String[] nums = new String[8];
	if (numbers.length() != nums.length * subStringLength) {
	    throw new IllegalArgumentException();
	}
	for (int i = 0; i < nums.length; i++) {
	    nums[i] = numbers.substring(i * subStringLength, (i + 1)
		    * subStringLength);
	}
	Arrays.sort(nums);
	StringBuffer buffer = new StringBuffer();
	for(int i = 0; i < nums.length; i++) {
	    buffer.append(nums[i]);
	}
	return buffer.toString();
    }
}
