import java.util.*;

public class A2_Q3 {
	public static int[] change(int[] values_bills, int price) {
		int[] dp = new int[price + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;

		for (int bill : values_bills) {
			for (int i = price; i > 0; i--) {
				if (bill <= i && dp[i - bill] != Integer.MAX_VALUE) {
					dp[i] = Math.min(dp[i], dp[i - bill] + 1);
				}
			}
		}

		if (dp[price] == Integer.MAX_VALUE) {
			// No valid combination of bills to make the price
			return new int[]{price, -1};
		} else {
			return new int[]{price, dp[price]};
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}