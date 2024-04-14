import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;

	protected Assignment() {
	}

	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}

	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {

		// Weight is more important than deadline so it goes first
		if (a1.weight < a2.weight) return 1;

		if (a1.weight > a2.weight) return -1;

		if (a1.deadline > a2.deadline) return 1;

		if (a1.deadline < a2.deadline) return -1;

		return 0;
	}
}
