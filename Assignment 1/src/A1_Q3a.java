import java.util.*;

public class A1_Q3a {

	private static final int BIGNUM = 1999999999;

	public static int protocol_3a(int[] arrangement) {

		int minD = arrangement.length;

		HashMap<Integer, Integer> courseToIndexMap = new HashMap<Integer, Integer>();

		// For loop going through arrangements (O(n))
		for (int student = 0; student < arrangement.length; student++) {
			int course = arrangement[student];

			Integer lastIndexOfCourse = courseToIndexMap.get(course);

			if (lastIndexOfCourse != null) {
				int studentDistance = student - lastIndexOfCourse;

				if (studentDistance < minD)
					minD = studentDistance;

			}

			courseToIndexMap.put(course, student);

		}


		return minD;
	}

}
