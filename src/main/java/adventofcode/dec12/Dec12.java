package adventofcode.dec12;

import adventofcode.util.FileReaderUtil;

public class Dec12 {

	public static void main(String[] args) {

		String[] instructions = FileReaderUtil.readRowsAsArray("dec12.txt");

		var sum = new Dec12().first(instructions);
		System.out.println("first: " + sum);

		var sum2 = new Dec12().second(instructions);
		System.out.println("second: " + sum2);
	}

	private int second(String[] instructions) {
		Boat2 b = new Boat2();
		for (String ins : instructions) {
			b.move(ins);
		}

		return Math.abs(b.currX) + Math.abs(b.currY);
	}

	private int first(String[] instructions) {

		Boat b = new Boat();
		for (String ins : instructions) {
			b.move(ins);
		}

		return Math.abs(b.currX) + Math.abs(b.currY);

	}

	private static class Boat2 {
		int wpx = 10;
		int wpy = 1;

		int currX = 0;
		int currY = 0;

		void move(String instruction) {
			char move = instruction.charAt(0);
			int val = Integer.parseInt(instruction.substring(1));

			switch (move) {
				case 'N':
					wpy += val;
					break;
				case 'S':
					wpy -= val;
					break;
				case 'E':
					wpx += val;
					break;
				case 'W':
					wpx -= val;
					break;
				case 'L':
					rotate(360 - val);
					break;
				case 'R':
					rotate(val);
					break;
				case 'F':
					forward(val);
					break;
			}
		}

		private void forward(int val) {
			while (val > 0) {
				currX += wpx;
				currY += wpy;
				val--;
			}
		}

		void rotate(int degrees) {

			if (degrees == 90) {
				int tmp = wpy;
				wpy = -wpx;
				wpx = tmp;
			} else if (degrees == 180) {
				wpx = -wpx;
				wpy = -wpy;
			} else if (degrees == 270) {
				int tmp = wpy;
				wpy = wpx;
				wpx = -tmp;
			}
		}
	}

	private static class Boat {
		int currX = 0;
		int currY = 0;

		int heading = 90;


		void move(String instruction) {
			char move = instruction.charAt(0);
			int val = Integer.parseInt(instruction.substring(1));

			switch (move) {
				case 'N':
					currY += val;
					break;
				case 'S':
					currY -= val;
					break;
				case 'E':
					currX += val;
					break;
				case 'W':
					currX -= val;
					break;
				case 'L':
					heading -= val;
					heading = (heading + 360) % 360;
					break;
				case 'R':
					heading += val;
					heading = heading % 360;
					break;
				case 'F':
					if (heading == 0) {
						currY += val;
					} else if (heading == 90) {
						currX += val;
					} else if (heading == 180) {
						currY -= val;
					} else if (heading == 270) {
						currX -= val;
					}
					break;
			}
		}
	}
}
