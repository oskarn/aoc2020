package adventofcode.dec11;

import java.util.Arrays;

import javax.sound.midi.Soundbank;

import adventofcode.util.FileReaderUtil;

public class Dec11 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec11.txt");
		char[][] matrix = new char[rows.length][rows[0].length()];
		for (int i = 0; i < rows.length; i++) {
			matrix[i] = rows[i].toCharArray();
		}

		char[][] trans = new char[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				trans[i][j] = matrix[j][i];
			}
		}

		//System.out.println(new Dec11().first(trans));
		System.out.println(new Dec11().second(trans));
	}

	private int second(char[][] matrix) {
		printMatrix(matrix);
		while (true) {
			char[][] next = new char[matrix.length][matrix[0].length];
			boolean flipped = false;
			for (int x = 0; x < matrix.length; x++) {
				for (int y = 0; y < matrix[0].length; y++) {
					int adjOcc = getNbrVisiblyOccupied(matrix, x, y);
					if (matrix[x][y] == 'L' && adjOcc == 0) {
						flipped = true;
						next[x][y] = '#';
					} else if (matrix[x][y] == '#' && adjOcc >= 5) {
						flipped = true;
						next[x][y] = 'L';
					} else {
						next[x][y] = matrix[x][y];
					}
				}
			}
			if (!flipped) {
				break;
			}
			matrix = next;
			printMatrix(matrix);
		}
		int totOcc = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				totOcc += matrix[i][j] == '#' ? 1 : 0;
			}
		}
		return totOcc;
	}

	private char safeGet(char[][] matrix, int x, int y) {
		if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length) {
			return matrix[x][y];
		} else {
			return 'X';
		}
	}

	private int getNbrVisiblyOccupied(char[][] matrix, int x, int y) {
		int occ = 0;

		//N
		for (int yi = y - 1; yi >= 0; yi--) {
			char val = safeGet(matrix, x, yi);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}

		//S
		for (int yi = y + 1; yi < matrix[0].length; yi++) {
			char val = safeGet(matrix, x, yi);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}

		//W
		for (int xi = x - 1; xi >= 0; xi--) {
			char val = safeGet(matrix, xi, y);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}

		//E
		for (int xi = x + 1; xi < matrix.length; xi++) {
			char val = safeGet(matrix, xi, y);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}

		//NW
		for (int yi = y - 1, xi = x - 1; yi >= 0 && xi >= 0; yi--, xi--) {
			char val = safeGet(matrix, xi, yi);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}


		//NE
		for (int yi = y - 1, xi = x + 1; yi >= 0 && xi < matrix.length; yi--, xi++) {
			char val = safeGet(matrix, xi, yi);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}


		//SW
		for (int xi = x - 1, yi = y + 1; xi >= 0 && yi < matrix[0].length; xi--, yi++) {
			char val = safeGet(matrix, xi, yi);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}

		//SE
		for (int yi = y + 1, xi = x + 1; yi < matrix[0].length && xi < matrix.length; yi++, xi++) {
			char val = safeGet(matrix, xi, yi);
			if (val == 'X') {
				break;
			}
			if (val == '#') {
				occ++;
				break;
			}
			if (val == 'L') {
				break;
			}
		}

		return occ;
	}

	private int first(char[][] matrix) {

		printMatrix(matrix);
		while (true) {
			char[][] next = new char[matrix.length][matrix[0].length];
			boolean flipped = false;
			for (int x = 0; x < matrix.length; x++) {
				for (int y = 0; y < matrix[0].length; y++) {
					int adjOcc = getNbrAdjacentOccupied(matrix, x, y);
					if (matrix[x][y] == 'L' && adjOcc == 0) {
						flipped = true;
						next[x][y] = '#';
					} else if (matrix[x][y] == '#' && adjOcc >= 4) {
						flipped = true;
						next[x][y] = 'L';
					} else {
						next[x][y] = matrix[x][y];
					}
				}
			}
			if (!flipped) {
				break;
			}
			matrix = next;
			printMatrix(matrix);
		}
		int totOcc = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				totOcc += matrix[i][j] == '#' ? 1 : 0;
			}
		}
		return totOcc;
	}

	private void printMatrix(char[][] matrix) {
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[j][i]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private int getNbrAdjacentOccupied(char[][] matrix, int x, int y) {
		int occ = 0;

		for (int xi = Math.max(0, x - 1); xi < Math.min(x + 2, matrix.length); xi++) {
			for (int yi = Math.max(0, y - 1); yi < Math.min(y + 2, matrix[0].length); yi++) {
				if (!(xi == x && yi == y)) {
					occ += matrix[xi][yi] == '#' ? 1 : 0;
				}
			}
		}
		return occ;
	}
}
