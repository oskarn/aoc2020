package adventofcode.dec14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventofcode.util.FileReaderUtil;

public class Dec14 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec14.txt");

		long first = new Dec14().first(rows);
		System.out.println(first);

		long second = new Dec14().second(rows);
		System.out.println(second);
	}

	private long second(String[] rows) {
		Pattern pattern = Pattern.compile("\\d+");

		Map<Long, Long> memory = new HashMap<>();
		String currentMask = "";
		for (String row : rows) {
			String[] split = row.split(" = ");
			if (split[0].equals("mask")) {
				currentMask = split[1];
			} else {
				long mem = getMem(pattern.matcher(split[0]));
				long val = Long.parseLong(split[1]);

				String binary = pad(Long.toBinaryString(mem));
				List<Long> addresses = maskMem(currentMask, binary);

				for (long l : addresses) {
					memory.put(l, val);
				}
			}
		}

		long sum = memory.values().stream().mapToLong(Long::longValue).sum();
		return sum;
	}

	private List<Long> maskMem(String currentMask, String binary) {
		char[] asArr = binary.toCharArray();

		for (int i = 0; i < currentMask.length(); i++) {
			if (currentMask.charAt(i) == '1') {
				asArr[i] = '1';
			} else if (currentMask.charAt(i) == '0') {
				;//nop;
			} else {
				asArr[i] = 'F';
			}
		}

		List<char[]> result = new ArrayList<>();
		result.add(asArr);
		for (int i = 0; i < asArr.length; i++) {
			if (asArr[i] == 'F') {
				result = splitArr(result, i);
			}
		}

		List<Long> mems = new ArrayList<>();
		for (char[] arr : result) {
			mems.add(Long.parseUnsignedLong(new String(arr), 2));

		}

		return mems;
	}

	private List<char[]> splitArr(List<char[]> arrs, int index) {
		List<char[]> result = new ArrayList<>();
		for (char[] arr : arrs) {
			char[] clone = arr.clone();
			clone[index] = '0';
			arr[index] = '1';
			result.add(clone);
			result.add(arr);
		}
		return result;
	}


	private int getMem(Matcher matcher1) {
		Matcher matcher = matcher1;
		matcher.find();
		return Integer.parseInt(matcher.group());
	}


	private long first(String[] rows) {
		Pattern pattern = Pattern.compile("\\d+");

		Map<Integer, Long> memory = new HashMap<>();
		String currentMask = "";
		for (String row : rows) {
			String[] split = row.split(" = ");
			if (split[0].equals("mask")) {
				currentMask = split[1];
			} else {
				int mem = getMem(pattern.matcher(split[0]));
				long val = Long.parseLong(split[1]);

				String binary = pad(Long.toBinaryString(val));
				String toInsert = mask(currentMask, binary);
				long parsed = Long.parseUnsignedLong(toInsert, 2);
				memory.put(mem, parsed);
			}
		}

		long sum = memory.values().stream().mapToLong(Long::longValue).sum();
		return sum;
	}

	private String pad(String binary) {
		StringBuilder binaryBuilder = new StringBuilder(binary);
		while (binaryBuilder.length() < 36) {
			binaryBuilder.insert(0, "0");
		}
		binary = binaryBuilder.toString();
		return binary;
	}

	private String mask(String currentMask, String binary) {
		char[] asArr = binary.toCharArray();
		for (int i = 0; i < currentMask.length(); i++) {
			if (currentMask.charAt(i) == '1') {
				asArr[i] = '1';
			} else if (currentMask.charAt(i) == '0')
				asArr[i] = '0';
		}
		return new String(asArr);
	}
}
