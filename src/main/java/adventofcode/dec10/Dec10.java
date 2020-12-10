package adventofcode.dec10;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.FileReaderUtil;

public class Dec10 {

	public static void main(String[] args) {
		//too low: 193434623148032
		var lines = FileReaderUtil.readRowsAsArray("dec10.txt");
		new Dec10().doit3(lines);

	}

	private void doIt(String[] lines) {
		int[] ints = Arrays.stream(lines).mapToInt(Integer::parseInt).sorted().toArray();
		ints = addStartEnd(ints);

		int ones = 0;
		int threes = 0;
		for (int i = 0; i < ints.length - 1; i++) {
			int diff = ints[i + 1] - ints[i];
			if (diff == 1) {
				ones++;
			} else if (diff == 3) {
				threes++;
			}
		}
		System.out.println("ones: " + ones + "threes: " + threes);
		System.out.println("ones * threes: " + (ones * threes));
	}

	private int[] addStartEnd(int[] arr) {
		List<Integer> collect = Arrays.stream(arr).boxed().collect(Collectors.toList());
		collect.add(0, 0);

		collect.add(0, -4);
		collect.add(0, -7);
		//padding

		collect.add(collect.get(collect.size() - 1) + 3);

		//padding
		collect.add(collect.get(collect.size() - 1) + 3);
		collect.add(collect.get(collect.size() - 1) + 3);
		return collect.stream().mapToInt(Integer::intValue).toArray();
	}

	private void doit3(String[] lines) {
		int[] ints = Arrays.stream(lines).mapToInt(Integer::parseInt).sorted().toArray();
		ints = addStartEnd(ints);
		int[] splits = new int[ints.length];
		for (int i = ints.length - 1; i >= 0; i--) {
			int current = ints[i];
			splits[i] = 1;
			if (ints.length > i + 2) {
				int lookahead1 = ints[i + 2];
				int diff = lookahead1 - current;
				if (diff == 2 || diff == 3) {
					splits[i] = 2;
				}
			}
			if (ints.length > i + 3) {
				int lookahead = ints[i + 3];
				int diff = lookahead - current;
				if (diff == 3) {
					splits[i] = 3;

				}
			}
		}

		long[] sums = new long[ints.length];
		sums[sums.length - 1] = 1L;
		for (int i = splits.length - 2; i >= 0; i--) {
			if (splits[i] == 1) {
				sums[i] = sums[i + 1];
			} else if (splits[i] == 2) {
				sums[i] = sums[i + 1] + sums[i + 2];
			} else if (splits[i] == 3) {
				sums[i] = sums[i + 1] + sums[i + 2] + sums[i + 3];
			}
		}
		System.out.println(sums[0]);
	}
}
