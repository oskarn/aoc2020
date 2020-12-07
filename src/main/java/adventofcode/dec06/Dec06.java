package adventofcode.dec06;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.FileReaderUtil;

public class Dec06 {

	public static void main(String[] args) {
		new Dec06().doIt2();
	}

	private void doIt2() {

		String input = FileReaderUtil.readFile("dec06.txt");

		String[] groups = input.split("\n\n");
		int unq = 0;
		for (String group : groups) {
			Map<String, Integer> groupUnq = new HashMap<>();
			String[] personAnswers = group.split("\n");
			for (String person : personAnswers) {
				for (char c : person.toCharArray()) {
					groupUnq.compute(c + "", (key, val) -> val == null ? 1 : val + 1);
				}
			}
			List<Integer> collect = groupUnq.values().stream().filter(val -> val == personAnswers.length).collect(Collectors.toList());

			unq += collect.size();
		}
		System.out.println(unq);

	}

	private void doIt() {

		String input = FileReaderUtil.readFile("dec06.txt");

		String[] groups = input.split("\n\n");
		int unq = 0;
		for (String group : groups) {
			Set<String> groupUnq = new HashSet<>();
			String[] personAnswers = group.split("\n");
			for (String person : personAnswers) {
				for (char c : person.toCharArray()) {
					groupUnq.add(c + "");
				}
			}
			unq += groupUnq.size();
		}
		System.out.println(unq);

	}
}
