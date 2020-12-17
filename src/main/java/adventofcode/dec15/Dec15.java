package adventofcode.dec15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Dec15 {

	//private static int[] input = new int[] {0, 3, 6};//{0, 14, 1, 3, 7, 9};
	private static int[] input = new int[] {0, 14, 1, 3, 7, 9};

	public static void main(String[] args) {
		int first = new Dec15().first();
		System.out.println(first);
	}

	private int first() {

		int[] spokenNumbers = new int[30000000];
		//int[] spokenNumbers = new int[2020];
		/*Map<Integer, Integer> unique = new HashMap<>();*/
		Set<Integer> unq = new HashSet<>();
		Map<Integer, List<Integer>> hurr = new HashMap<>();
		boolean wasFirst = true;
		for (int i = 0; i < spokenNumbers.length; i++) {
			if (i % 10000 == 0) {
				System.out.println(i);
			}
			if (i < input.length) {
				spokenNumbers[i] = input[i];
				unq.add(input[i]);
				addOrUpdate(hurr, input[i], i);
				continue;
			}

			if (wasFirst) {
				spokenNumbers[i] = 0;
				unq.add(0);
				addOrUpdate(hurr, 0, i);
				wasFirst = false; //0 has always been said before
			} else {
				int roundWhenWasLastSpoken = findRound(spokenNumbers, i, hurr);

				int toSpeak = i - roundWhenWasLastSpoken;
				spokenNumbers[i] = toSpeak;
				if (!unq.contains(toSpeak)) {
					wasFirst = true;
				} else {
					wasFirst = false;
				}
				unq.add(toSpeak);
				addOrUpdate(hurr, toSpeak, i);
			}
		}

		return spokenNumbers[spokenNumbers.length - 1];
	}


	private int findRound(int[] spokenNumbers, int currentIndex, Map<Integer, List<Integer>> hurr) {

		int toFind = spokenNumbers[currentIndex - 1];
		/*for (int i = currentIndex - 2; i >= 0; i--) {
			if (spokenNumbers[i] == toFind) {
				return i + 1;
			}
		}*/

		List<Integer> integers = hurr.get(toFind);
		return integers.get(integers.size() - 2) +1;

		//throw new RuntimeException("did not find");
	}

	private void addOrUpdate(Map<Integer, List<Integer>> hurr, int spoken, int index) {
		if (hurr.containsKey(spoken)) {
			hurr.get(spoken).add(index);
		} else {
			hurr.put(spoken, of(index));
		}
	}

	private List<Integer> of(int i) {
		List<Integer> list = new ArrayList<>();
		list.add(i);
		return list;
	}
}
