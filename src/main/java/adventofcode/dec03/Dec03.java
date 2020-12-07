package adventofcode.dec03;

import adventofcode.util.FileReaderUtil;

public class Dec03 {

	public static void main(String[] args) {
		new Dec03().one();
		new Dec03().two();
	}

	private void two() {
		String[] rows = FileReaderUtil.readRowsAsArray("dec03.txt");
		int width = rows[0].length();

		/*
    Right 1, down 1.
    Right 3, down 1. (This is the slope you already checked.)
    Right 5, down 1.
    Right 7, down 1.
    Right 1, down 2.
*/
		long a = checkSlope(rows, width, 1, 1);
		long b = checkSlope(rows, width, 3, 1);
		long c = checkSlope(rows, width, 5, 1);
		long d = checkSlope(rows, width, 7, 1);
		long e = checkSlope(rows, width, 1, 2);
		System.out.println(b);
		System.out.println(a*b*c*d*e);


	}

	private int checkSlope(String[] rows, int width, int right, int down) {
		int yPos = 0;
		int nbrCollisions = 0;
		for (int i = 0; i < rows.length - down; i = i + down) {
			String row = rows[i + down];
			yPos = yPos + right;
			yPos = yPos > width - 1 ? yPos - width : yPos;
			char posToCheck = row.charAt(yPos);
			if (posToCheck == '#') {
				nbrCollisions++;
			}
		}
		return nbrCollisions;
	}

	private void one() {

		String[] rows = FileReaderUtil.readRowsAsArray("dec03.txt");
		int width = rows[0].length();
		int yPos = 0;
		int nbrCollisions = 0;
		for (int i = 0; i < rows.length - 1; i++) {
			String row = rows[i + 1];
			yPos = yPos + 3;
			yPos = yPos > width - 1 ? yPos - width : yPos;
			char posToCheck = row.charAt(yPos);
			if (posToCheck == '#') {
				nbrCollisions++;
			}
		}
		System.out.println(nbrCollisions);
	}
}
