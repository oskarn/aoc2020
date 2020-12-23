package adventofcode.dec19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventofcode.util.FileReaderUtil;

public class Dec19 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec19-2.txt");

		int first = new Dec19().first(rows);
		System.out.println(first);
	}

	private int first(String[] rows) {
		boolean isRules = true;
		List<String> rules = new ArrayList<>();
		List<String> input = new ArrayList<>();
		for (String row : rows) {
			if (row.equals("")) {
				isRules = false;
			}
			if (isRules) {
				rules.add(row);
			} else {
				input.add(row);
			}
		}

		Map<Integer, Rule> integerRuleMap = parseRules(rules);

		int valid = checkInput(input, integerRuleMap);
		return valid;
	}

	private int checkInput(List<String> input, Map<Integer, Rule> integerRuleMap) {
		int valid = 0;

		Pattern pattern = buildRegExp(integerRuleMap);
		System.out.println(pattern);
		for (String inp : input) {
			Matcher matcher = pattern.matcher(inp);
			if (matcher.matches()) {
				System.out.println("matches: " + inp);
				valid++;
			} else {
				System.out.println("doesn't match: " + inp);
			}
		}

		return valid;
	}

	private Pattern buildRegExp(Map<Integer, Rule> ruleMap) {
		Rule rule = ruleMap.get(0);

		String regexp = rule.getRegexp(ruleMap);
		regexp = "^" + regexp + "$";
		Pattern pattern = Pattern.compile(regexp);
		return pattern;

	}

	private Map<Integer, Rule> parseRules(List<String> rules) {

		Map<Integer, Rule> rulesMap = new HashMap<>();

		for (String ruleString : rules) {
			System.out.println("parsing: " + ruleString);
			String[] split = ruleString.split(":");
			int ruleId = Integer.parseInt(split[0]);
			if (split[1].contains("\"")) {
				char matcher = split[1].charAt(2);
				if (rulesMap.containsKey(ruleId)) {
					Rule rule = rulesMap.get(ruleId);
					rule.pattern = matcher + "";
				} else {
					Rule rule = new Rule(ruleId, matcher + "");
					rulesMap.put(ruleId, rule);
				}
			} else {
				String[] subruleList = split[1].split("\\|");
				List<List<Integer>> subRules = new ArrayList<>();
				for (String subRule : subruleList) {
					String[] matchingRuleId = subRule.trim().split(" ");
					List<Integer> rulesToAdd = new ArrayList<>();
					for (String matching : matchingRuleId) {
						int asInt = Integer.parseInt(matching);
						Rule ruleToAdd;
						if (rulesMap.containsKey(asInt)) {
							ruleToAdd = rulesMap.get(asInt);
						} else {
							ruleToAdd = new Rule(asInt);
						}
						rulesToAdd.add(ruleToAdd.id);
					}
					subRules.add(rulesToAdd);
				}

				if (rulesMap.containsKey(ruleId)) {
					Rule rule = rulesMap.get(ruleId);
					rule.rules = subRules;
				} else {
					Rule rule = new Rule(ruleId, subRules);
					rulesMap.put(ruleId, rule);
				}

			}

		}

		return rulesMap;
	}

	private static class Rule {
		int id;

		List<List<Integer>> rules;
		String pattern;

		public Rule(int id, List<List<Integer>> rules) {
			this.id = id;
			this.rules = rules;
		}

		public Rule(int id, String pattern) {
			this.id = id;
			this.pattern = pattern;
		}

		public Rule(int id) {
			this.id = id;
		}

		public String getRegexp(Map<Integer, Rule> ruleMap) {
			if (pattern != null) {
				return pattern;
			} else {
				if (id == 8) {
					String p42 = "(" + ruleMap.get(42).getRegexp(ruleMap) + ")";
					String p42more = "" + ruleMap.get(42).getRegexp(ruleMap) + "+";
					return p42more;
				} else if (id == 11) {
					String p42 = "(" + ruleMap.get(42).getRegexp(ruleMap) + ")";
					String p31 = "(" + ruleMap.get(31).getRegexp(ruleMap) + ")";

					String p4231 = "(" + p42 + p31 + ")";

					String pattern = "(" + p4231 + "|(" + p42 + "PPP" + p31 + "))";
					String result = pattern;
					int iters = 20;
					for (int i = 0; i < iters; i++) {
						if (i == iters - 1) {
							result = result.replace("PPP", p4231);
						} else {
							result = result.replace("PPP", pattern);
						}
					}

					return result;
				}

				String s = "(";
				for (int i = 0; i < rules.size(); i++) {
					List<Integer> ruleList = rules.get(i);
					s += "(";
					for (Integer ruleId : ruleList) {
						s += ruleMap.get(ruleId).getRegexp(ruleMap);
					}
					s += ")";
					if (i < rules.size() - 1) {
						s += "|";
					}
				}
				s += ")";
				return s;
			}
		}
	}
}
