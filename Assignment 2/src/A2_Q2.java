import java.math.BigInteger;
import java.util.Scanner;

public class A2_Q2 {

	// Static array to store computed Fibonacci numbers
	private static BigInteger[] fArray = new BigInteger[10000001]; // Adjust the size based on the maximum value of N

	public static String mod_fibonacci(int N, BigInteger K) {
		if (N == 1)
			return "X";
		if (N == 2)
			return "Y";

		BigInteger previousStringLength = fibonacci(N - 2);

		if (K.compareTo(previousStringLength) > 0)
			return mod_fibonacci(N-1, K.subtract(previousStringLength));
		else
			return mod_fibonacci(N-2, K);
	}

	// Fibonacci function with memoization
	private static BigInteger fibonacci(int n) {
		if (n <= 0)
			return BigInteger.ZERO;
		if (n == 1 || n == 2)
			return BigInteger.ONE;
		if (fArray[n] != null)
			return fArray[n];

		// Recursively compute Fibonacci number and store in the array
		fArray[n] = fibonacci(n - 1).add(fibonacci(n - 2));
		return fArray[n];
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}