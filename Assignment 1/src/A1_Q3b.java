import java.util.*;

public class A1_Q3b {

	public static int protocol_3b(int[] arrangement) {

		int maxD = 0;

		// Initialize variables
		HashSet<Integer> classMap = new HashSet<>(); // <Course, Index of student>
		int lengthOfCurrent = 0 ;

		for (int student = 0; student < arrangement.length; student++) {


			if (classMap.contains(arrangement[student])) {

				while (classMap.contains(arrangement[student])) {
					classMap.remove(arrangement[student - lengthOfCurrent]);
					lengthOfCurrent--;
				}
			}

			lengthOfCurrent++;


			if (maxD < lengthOfCurrent)
				maxD = lengthOfCurrent;

			classMap.add(arrangement[student]);


		}


		return maxD;
	}

	public static void main (String[] args) {
		int[] array = {1,2,3,1,2,3,4};
		System.out.println(protocol_3b(array));
	}

}