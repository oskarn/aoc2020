package adventofcode.dec09;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.FileReaderUtil;

public class Dec09 {

	private int preambleSize = 25;

	public static void main(String[] args) {
		Dec09 dec09 = new Dec09();
		long first = dec09.first();
		System.out.println("first: " + first);
		dec09.second(first);
	}

	private long first() {
		String[] lines = FileReaderUtil.readRowsAsArray("dec09.txt");
		List<Long> numbers = Arrays.stream(lines).map(Long::parseLong).collect(Collectors.toList());

		for (int i = preambleSize; i < numbers.size(); i++) {
			var sums = computeSums(numbers, i);
			if (!sums.contains(numbers.get(i))) {
				return (numbers.get(i));
			}
		}
		return -1;
	}

	private void second(long toFind) {
		String[] lines = FileReaderUtil.readRowsAsArray("dec09.txt");
		List<Long> numbers = Arrays.stream(lines).map(Long::parseLong).collect(Collectors.toList());
		for (int i = 0; i < numbers.size(); i++) {
			var cont = findContigious(numbers, i, toFind);
			if (cont != null) {
				System.out.println("left:" + cont.left);
				System.out.println("right:" + cont.right);
				System.out.println(cont.left + cont.right);
			}
		}
	}

	private Pair findContigious(List<Long> list, int start, long max) {
		long sum = 0;
		long low = Long.MAX_VALUE;
		long high = 0;
		for (int i = start; i < list.size(); i++) {
			Long current = list.get(i);
			if(current < low){
				low = current;
			}
			if(current > high){
				high = current;
			}
			sum += current;
			if (sum > max) {
				return null;
			}
			if (sum == max && i != start) {
				return new Pair(low, high);
			}
		}
		return null;
	}

	private Set<Long> computeSums(List<Long> list, int currentIndex) {

		Set<Long> sums = new HashSet<>();
		for (int i = currentIndex - preambleSize; i < currentIndex; i++) {
			for (int j = i + 1; j < currentIndex; j++) {
				long a = list.get(i);
				long b = list.get(j);
				if (a != b) {
					sums.add(a + b);
				}
			}
		}
		return sums;

	}

	private static class Pair {
		long left;
		long right;

		public Pair(long left, long right) {
			this.left = left;
			this.right = right;
		}
	}
}
