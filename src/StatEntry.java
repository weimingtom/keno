import java.util.Vector;


public class StatEntry {
    public int index;
    public String numbers;
    public Vector dataVec = new Vector();
    
    public StatEntry(int index, String numbers) {
	this.index = index;
	this.numbers = numbers;
    }
    
    @Override
    public String toString() {
	return "{" + numbers + "," + dataVec.size() + "}";
    }
}
