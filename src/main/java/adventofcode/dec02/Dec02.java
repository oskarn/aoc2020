package adventofcode.dec02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.FileReaderUtil;

public class Dec02 {

	public static void main(String[] args) {
		new Dec02().doIt();
	}

	private void doIt() {
		String two = FileReaderUtil.readFile("dec02.txt");

		String[] split = two.split("\\n");
		List<Row> list = Arrays.stream(split).map(row -> {
			String[] parts = row.split(" ");
			String[] minMax = parts[0].split("-");
			String c = parts[1].substring(0, parts[1].length() - 1);
			return new Row(Integer.parseInt(minMax[0]), Integer.parseInt(minMax[1]), c, parts[2]);
		})
							   .filter(this::validateRow2)
							   .collect(Collectors.toList());

		System.out.println(list.size());
	}

	private boolean validateRow2(Row row) {
		boolean first = row.password.charAt(row.min - 1) == row.character;
		boolean second = row.password.charAt(row.max - 1) == row.character;
		return first ^ second;
	}

	private boolean validateRow(Row row) {
		int matches = 0;
		for (char c : row.password.toCharArray()) {
			if (c == row.character) {
				matches++;
			}
		}
		return matches >= row.min && matches <= row.max;
	}

	private static class Row {
		int min;
		int max;
		char character;
		String password;

		public Row(int min, int max, String character, String password) {
			this.min = min;
			this.max = max;
			this.character = character.charAt(0);
			this.password = password;
		}
	}

}
