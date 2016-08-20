public class TestKenoComb {
    public static void main(String[] args) {
	String[] elements;
	elements = getInputNumbers("0102030405060708");
	int[] indices;
	CombinationGenerator x = new CombinationGenerator(elements.length, 5);
	StringBuffer combination;
	while (x.hasMore()) {
	    combination = new StringBuffer();
	    indices = x.getNext();
	    for (int i = 0; i < indices.length; i++) {
		combination.append(elements[indices[i]]);
	    }
	    System.out.println(combination.toString());
	}
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
