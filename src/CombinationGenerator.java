import java.math.BigInteger;

/**
 * 系统化生成组合
 * @author Administrator
 * @see http://www.merriampark.com/comb.htm
 */
public class CombinationGenerator {
    private int[] a; //子集数组
    private int n; //总长度
    private int r; //子集长度
    private BigInteger numLeft; //剩下的组合数（作为当前状态）
    private BigInteger total; //组合数C(n, r)

    /**
     * 构造函数
     * @param n 全集长度
     * @param r 子集长度
     */
    public CombinationGenerator(int n, int r) {
	if (r > n) {
	    throw new IllegalArgumentException();
	}
	if (n < 1) {
	    throw new IllegalArgumentException();
	}
	this.n = n;
	this.r = r;
	a = new int[r];
	BigInteger nFact = getFactorial(n);
	BigInteger rFact = getFactorial(r);
	BigInteger nminusrFact = getFactorial(n - r);
	total = nFact.divide(rFact.multiply(nminusrFact));
	reset();
    }

    /**
     * 重置，把子集填充为0-r
     */
    public void reset() {
	for (int i = 0; i < a.length; i++) {
	    a[i] = i;
	}
	numLeft = new BigInteger(total.toString());
    }

    /**
     * 返回剩余的组合数
     * @return
     */
    public BigInteger getNumLeft() {
	return numLeft;
    }

    /**
     * 还有更多组合吗？
     * @return
     */
    public boolean hasMore() {
	return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    /**
     * 组合总数
     * @return
     */
    public BigInteger getTotal() {
	return total;
    }

    /**
     * 计算阶乘（非递归）
     * @param n
     * @return
     */
    private static BigInteger getFactorial(int n) {
	BigInteger fact = BigInteger.ONE;
	for (int i = n; i > 1; i--) {
	    fact = fact.multiply(new BigInteger(Integer.toString(i)));
	}
	return fact;
    }

    /**
     * 生成下一个组合
     * @return
     */
    public int[] getNext() {
	if (numLeft.equals(total)) {
	    numLeft = numLeft.subtract(BigInteger.ONE);
	    return a;
	}
	int i = r - 1; //最后一个数
	//System.out.println(i+","+(n-r+i));
	while (a[i] == n - r + i) {
	    //System.out.println("<<<");
	    i--;
	} //右面的子集已经到达最大
	a[i] = a[i] + 1; //允许增大
	for (int j = i + 1; j < r; j++) {
	    //System.out.println(">>>o"+j+","+a[j]);
	    a[j] = a[i] + j - i;//实际上是a[i+1] = a[i] + 1
	    //System.out.println(">>>"+j+","+a[j]);
	} //把后面已达到最大的数字复位为原来的最小值+1
	numLeft = numLeft.subtract(BigInteger.ONE);
	return a;
    }
}
