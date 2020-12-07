package adventofcode.dec05;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import adventofcode.util.FileReaderUtil;

public class Dec05 {

	public static void main(String[] args) {
		new Dec05().two();
	}

	private void two() {
		String[] rows = FileReaderUtil.readRowsAsArray("dec05.txt");
		List<Integer> ids = new ArrayList<>();
		for (String row : rows) {
			var planeRows = getRows();
			for (int i = 0; i < 7; i++) {
				planeRows = getHiLow(planeRows, row.charAt(i));
			}
			var cols = getColumns();
			for (int j = 7; j < 10; j++) {
				cols = getHiLow(cols, row.charAt(j));
			}

			if (planeRows.get(0) == 0 || planeRows.get(0) == 127) {
				continue;
			}

			int currId = (planeRows.get(0) * 8) + cols.get(0);
			ids.add(currId);
		}

		ids.sort(Comparator.comparing(Integer::intValue));

		for (int i = 0; i < ids.size() - 1; i++) {
			int current = ids.get(i);
			if (current == ids.get(i + 1) - 2) {
				System.out.println(current + 1);
			}
		}
	}

	private void one() {
		String[] rows = FileReaderUtil.readRowsAsArray("dec05.txt");
		int highestId = 0;
		for (String row : rows) {
			var planeRows = getRows();
			for (int i = 0; i < 7; i++) {
				planeRows = getHiLow(planeRows, row.charAt(i));
			}
			var cols = getColumns();
			for (int j = 7; j < 10; j++) {
				cols = getHiLow(cols, row.charAt(j));
			}

			int currId = (planeRows.get(0) * 8) + cols.get(0);
			if (currId > highestId) {
				highestId = currId;
			}
		}

	}


	private <T> List<T> getHiLow(List<T> list, char c) {
		//BFBBBBFRLL
		if (c == 'B' || c == 'R') {
			return list.subList((list.size() / 2), list.size());
			//half +1 -> end
		} else if (c == 'F' || c == 'L') {
			//0->half
			return list.subList(0, (list.size() / 2));
		}
		throw new RuntimeException("err");
	}

	private List<Integer> getRows() {
		return IntStream.range(0, 128).boxed().collect(toList());
	}

	private List<Integer> getColumns() {
		return IntStream.range(0, 8).boxed().collect(toList());
	}


}
