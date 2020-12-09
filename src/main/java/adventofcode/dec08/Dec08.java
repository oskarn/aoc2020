package adventofcode.dec08;

import java.util.HashSet;
import java.util.Set;

import adventofcode.util.FileReaderUtil;

public class Dec08 {

	public static void main(String[] args) {
		new Dec08().first();
		new Dec08().second();
	}

	private void second() {
		String[] rows = FileReaderUtil.readRowsAsArray("dec08.txt");




		for (int j = 0; j < rows.length; j++) {
			int acc = 0;
			Set<Integer> executed = new HashSet<>();
			Set<Integer> executed2 = new HashSet<>();
			for (int i = 0; i < rows.length; i++) {
				String[] parts = rows[i].split(" ");
				String instruction = parts[0];
				int arg = Integer.parseInt(parts[1]);

				if (j == i) {
					if (instruction.equals("jmp")) {
						instruction = "nop";
					} else if (instruction.equals("nop")) {
						instruction = "jmp";
					}
				}

				if (executed.contains(i)) {
					if (executed2.contains(i)) {
						//System.out.println("looped");
						break;
					}
					executed2.add(i);
				}
				executed.add(i);


				if (instruction.equals("acc")) {
					acc += arg;
				} else if (instruction.equals("jmp")) {
					i += arg - 1;
				} else if (instruction.equals("nop")) {
					;//nop
				} else {
					throw new RuntimeException("err");
				}
				if (i >= rows.length - 1) {
					System.out.println("ran to finish, acc is " + acc);
				}
			}
		}
		//System.out.println("acc is" + acc);

	}

	private void first() {
		String[] rows = FileReaderUtil.readRowsAsArray("dec08.txt");

		int acc = 0;

		Set<Integer> executed = new HashSet<>();
		for (int i = 0; i < rows.length; i++) {
			if (executed.contains(i)) {
				break;
			}
			executed.add(i);

			String[] parts = rows[i].split(" ");
			String instruction = parts[0];
			int arg = Integer.parseInt(parts[1]);

			if (instruction.equals("acc")) {
				acc += arg;
			} else if (instruction.equals("jmp")) {
				i += arg - 1;
			} else if (instruction.equals("nop")) {
				;//nop
			} else {
				throw new RuntimeException("err");
			}
		}
		System.out.println("first is: " + acc);

	}
}
