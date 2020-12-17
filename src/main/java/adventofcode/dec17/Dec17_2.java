package adventofcode.dec17;

import adventofcode.util.FileReaderUtil;

public class Dec17_2 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec17.txt");

		long first = new Dec17_2().first(rows);

		System.out.println(first);
	}

	int spaceSize = 40;
	int center = spaceSize / 2;

	private long first(String[] rows) {
		char[][][][] space = new char[spaceSize][spaceSize][spaceSize][spaceSize];
		init(space);

		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			char[] charArray = row.toCharArray();
			for (int j = 0; j < charArray.length; j++) {
				char c = charArray[j];
				space[i + center - 1][j + center - 1][center - 1][center - 1] = c;
			}
		}

		int what = countTotalActive(space);
		System.out.println(what);
		int start = ((spaceSize / 2) * -1) + 1;
		int end = (spaceSize / 2) - 1;
		printSpace(space, -1);
		for (int cycle = 0; cycle < 6; cycle++) {
			char[][][][] prev = copy(space);
			for (int x = start; x < end; x++) {
				for (int y = start; y < end; y++) {
					for (int z = start; z < end; z++) {
						for (int w = start; w < end; w++) {
							int xi = x + center;
							int yi = y + center;
							int zi = z + center;
							int wi = w + center;

							int nbrActiveNeighbour = checkSurroundingActive(prev, xi, yi, zi, wi);
							boolean isCurrentActive = '#' == prev[xi][yi][zi][wi];
							if (isCurrentActive) {
								if (nbrActiveNeighbour != 2 && nbrActiveNeighbour != 3) {
									space[xi][yi][zi][wi] = '.';
								}
							} else {
								if (nbrActiveNeighbour == 3) {
									space[xi][yi][zi][wi] = '#';
								}
							}
						}
					}
				}
			}

			printSpace(space, cycle);

		}

		return countTotalActive(space);
	}

	private char[][][][] copy(char[][][][] space) {
		char[][][][] copy = new char[spaceSize][spaceSize][spaceSize][spaceSize];
		for (int x = 0; x < spaceSize; x++) {
			for (int y = 0; y < spaceSize; y++) {
				for (int z = 0; z < spaceSize; z++) {
					for (int w = 0; w < spaceSize; w++) {
						copy[x][y][z][w] = space[x][y][z][w];
					}
				}
			}
		}
		return copy;
	}

	private void printSpace(char[][][][] space, int cycle) {
		System.out.println("Space for cyle: " + (cycle + 1));
		printSpaceForZW(space, -3 + center, 1);
		printSpaceForZW(space, -2 + center, 1);
		printSpaceForZW(space, -1 + center, 1);
		printSpaceForZW(space, 0 + center, 1);
		printSpaceForZW(space, 1 + center, 1);
		printSpaceForZW(space, 2 + center, 1);
	}

	private void printSpaceForZW(char[][][][] space, int z, int w) {
		StringBuffer sb = new StringBuffer();
		sb.append("Z: " + (z - center + 1) + "\r\n");
		boolean anyActive = false;
		for (int xi = 0; xi < spaceSize; xi++) {
			for (int yi = 0; yi < spaceSize; yi++) {
				char c = space[xi][yi][z][w];
				if (c == '#') {
					anyActive = true;
				}
				sb.append(c);
			}
			sb.append("\r\n");
		}
		if (anyActive) {
			System.out.println(sb.toString());
		} else {
			sb.append(" none active");
			System.out.println("Z: " + (z - center + 1) + " none active");
		}
		System.out.println();
	}

	private int countTotalActive(char[][][][] space) {
		int sum = 0;
		for (int x = 0; x < spaceSize; x++) {
			for (int y = 0; y < spaceSize; y++) {
				for (int z = 0; z < spaceSize; z++) {
					for (int w = 0; w < spaceSize; w++) {
						sum += space[x][y][z][w] == '#' ? 1 : 0;
					}
				}
			}
		}
		return sum;
	}

	private int checkSurroundingActive(char[][][][] space, int x, int y, int z, int w) {

		int[] xArr = new int[] {x - 1, x, x + 1};
		int[] yArr = new int[] {y - 1, y, y + 1};
		int[] zArr = new int[] {z - 1, z, z + 1};
		int[] wArr = new int[] {w - 1, w, w + 1};

		int active = 0;
		for (int xi : xArr) {
			for (int yi : yArr) {
				for (int zi : zArr) {
					for (int wi : wArr) {
						if (!(xi == x && yi == y && zi == z && wi == w)) {
							active += '#' == space[xi][yi][zi][wi] ? 1 : 0;
						}
					}
				}
			}
			//System.out.println("(x,y,z) (" + x + "," + y + "," + z + ")  has " + active + " active neighbours");
		}
		return active;
	}


	private void init(char[][][][] space) {
		for (int x = 0; x < spaceSize; x++) {
			for (int y = 0; y < spaceSize; y++) {
				for (int z = 0; z < spaceSize; z++) {
					for (int w = 0; w < spaceSize; w++) {
						space[x][y][z][w] = '.';
					}
				}
			}
		}
	}
}
