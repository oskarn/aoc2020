package adventofcode.dec13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.FileReaderUtil;

public class Dec13 {

	public static void main(String[] args) {
		String[] lines = FileReaderUtil.readRowsAsArray("dec13.txt");

		int first = new Dec13().first(lines);
		System.out.println("first: " + first);

		long second = new Dec13().second(lines);
		System.out.println("second: " + second);

	}

	private long second(String[] lines) {
		String[] parts = lines[1].split(",");

		List<Bus> tmp = new ArrayList<>();
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			if (part.equals("x")) {
				continue;
			}
			Bus b = new Bus(Integer.parseInt(part), i);
			tmp.add(b);

		}
		Bus[] buses = tmp.toArray(Bus[]::new);


		int inc = findIncrement(buses);
		System.out.println("increment is:" + inc);
		long t = inc;
		long i = 1;
		while (!checkT(buses, t)) {
			t = (inc + 29) * i - 29;
			if (t % 1000000000L == 0) {
				System.out.println(t);
			}
			i++;
		}

		return t;
	}

	private int findIncrement(Bus[] buses) {
		int t = 0;
		Bus[] tocheck = new Bus[] {buses[0], buses[2]};
		while (!checkT(tocheck, t)) {
			t += 1;
		}
		return t;
	}

	private boolean checkT(Bus[] buses, long t) {
		for (int i = 0, busesLength = buses.length; i < busesLength; i++) {
			Bus b = buses[i];
			if ((t + b.offset) % (b.line) == 0) {
				continue;
			} else {
				if (i >= 7) {
					System.out.println("found " + (i + 1) + " matching buses of " + (busesLength) + " and t was: " + t);

				}
				return false;
			}
		}
		return true;
	}

	private static class Bus {
		int line;
		int offset;

		public Bus(int line, int offset) {
			this.line = line;
			this.offset = offset;
		}
	}

	private int first(String[] lines) {

		int earliest = Integer.parseInt(lines[0]);

		String[] parts = lines[1].split(",");

		List<Pair> x = Arrays.stream(parts)
							 .filter(p -> !p.equals("x"))
							 .map(Integer::parseInt)
							 .map(line -> {
								 int times = earliest / line;
								 int lineNext = line * (times + 1);
								 return new Pair(line, lineNext);


							 }).sorted(Comparator.comparing(p -> p.right)).collect(Collectors.toList());

		var best = x.get(0);

		return best.left * (best.right - earliest);
	}

	private static class Pair {
		int left;
		int right;

		public Pair(int left, int right) {
			this.left = left;
			this.right = right;
		}
	}
}
