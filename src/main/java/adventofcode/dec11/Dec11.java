package adventofcode.dec11;

import java.util.Arrays;

import adventofcode.util.FileReaderUtil;

public class Dec11 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec11.txt");
		char[][] matrix = new char[rows.length][rows[0].length()];
		for (int i = 0; i < rows.length; i++) {
			matrix[i] = rows[i].toCharArray();
		}
		System.out.println(new Dec11().first(matrix));
	}

	private int first(char[][] matrix) {

		char[][] next = new char[matrix.length][matrix[0].length];
		while (true) {
			boolean flipped = false;
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					int adjOcc = getNbrAdjacentOccupied(matrix, i, j);
					if (matrix[i][j] == 'L' && adjOcc == 0) {
						flipped = true;
						next[i][j] = '#';
					} else if (matrix[i][j] == '#' && adjOcc > 4) {
						flipped = true;
						next[i][j] = 'L';
					} else {
						next[i][j] = matrix[i][j];
					}
				}
			}
			if (!flipped) {
				break;
			}
			flipped = false;
		}
		int totOcc = 0;
		for (int i = 0; i < next.length; i++) {
			for (int j = 0; j < next[0].length; j++) {
				totOcc += next[i][j] == '#' ? 1 : 0;
			}
		}
		return totOcc;
	}

	private int getNbrAdjacentOccupied(char[][] matrix, int x, int y) {
		int occ = 0;
		if (x > 0 && y > 0) {
			occ += matrix[x - 1][y - 1] == 'L' ? 1 : 0;
		}
		if (x > 0) {
			occ += matrix[x - 1][y] == 'L' ? 1 : 0;
		}

		if(x > 0 && y< )
		for (int i = Math.max(0, x - 1); i < x + 2; x++) {
			occ += matrix[i][y] == 'L' ? 1 : 0;
		}
		for (int i = Math.max(0, y - 1); i < y + 2; y++) {
			occ += matrix[x][i] == 'L' ? 1 : 0;
		}
		return occ;
	}
}
