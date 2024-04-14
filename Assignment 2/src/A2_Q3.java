import java.util.*;

public class A2_Q3 {
	public static int[] change(int[] values_bills, int price) {
		int maxPrice = 10000;
		int[] dp = new int[2*maxPrice - 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;

		for (int bill : values_bills) {
			for (int i = 2*maxPrice - 2; i > 0; i--) {
				if (i == bill) dp[i] = 1;
				if (bill <= i && dp[i - bill] != Integer.MAX_VALUE) {
					dp[i] = Math.min(dp[i], dp[i - bill] + 1);
				}
			}
		}


		for (int newPrice = price; newPrice <= 2*maxPrice; newPrice++) {

			if (dp[newPrice] != Integer.MAX_VALUE) return new int[]{newPrice, dp[newPrice]};

		}


		return new int[]{price, dp[price]};
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}