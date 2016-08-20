public class TestComb {
    public static void main(String[] args) {
	String[] elements = { "a", "b", "c", "d", "e", "f", "g" };
	int[] indices;
	CombinationGenerator x = new CombinationGenerator(elements.length, 3);
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
}
