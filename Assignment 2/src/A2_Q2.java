import java.math.BigInteger;
import java.util.Scanner;

public class A2_Q2 {

	public static String mod_fibonacci(int N, BigInteger K) {
		if (N == 1)
			return "X";
		if (N == 2)
			return "Y";

		BigInteger previousStringLength = fibonacci(N - 1);

		if (K.compareTo(previousStringLength) <= 0)
			return mod_fibonacci(N-1, K);
		else
			return mod_fibonacci(N-2, K.subtract(previousStringLength));




	}


	private static BigInteger fibonacci(int n) {
		if (n <= 0)
			return BigInteger.ZERO;

		if (n == 1 || n == 2)
			return BigInteger.ONE;

		double phi = (1 + Math.sqrt(5)) / 2; // Golden ratio

		return BigInteger.valueOf(Math.round((Math.pow(phi, n) - Math.pow(1 - phi, n)) / Math.sqrt(5)));
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
