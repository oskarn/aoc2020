package adventofcode.dec16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import adventofcode.util.FileReaderUtil;

public class Dec16 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec16.txt");

		long first = new Dec16().first(rows);
		System.out.println(first);

		long second = new Dec16().second(rows);
		System.out.println(second);
	}

	private long second(String[] rows) {
		Pattern pattern = Pattern.compile("\\d+");
		int type = 0;
		Map<String, Field> validValues = new HashMap<>();

		List<int[]> validOther = new ArrayList<>();
		int[] yourTicket = null;
		for (String row : rows) {
			if (row.equals("")) {
				continue;
			}
			if (row.equals("your ticket:")) {
				type = 1;
				continue;
			}
			if (row.equals("nearby tickets:")) {
				type = 2;
				continue;
			}
			if (type == 0) {
				String[] split = row.split(":");
				String[] parts = split[1].split(" or ");
				checkAddValid2(split[0].trim(), pattern.matcher(parts[0]), validValues);
				checkAddValid2(split[0].trim(), pattern.matcher(parts[1]), validValues);
			} else if (type == 1) {
				String[] parts = row.split(",");
				yourTicket = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
			} else if (type == 2) {
				String[] parts = row.split(",");
				boolean isValid = true;
				for (String field : parts) {
					int val = Integer.parseInt(field);
					if (validValues.values().stream().noneMatch(p -> p.validValues.contains(val))) {
						isValid = false;
						break;
					}
				}
				if (isValid) {
					validOther.add(Arrays.stream(parts).mapToInt(Integer::parseInt).toArray());
				}
			}
		}
		Map<Integer, Poss> fieldMap = new HashMap<>();
		for (int i = 0; i < yourTicket.length; i++) {
			fieldMap.put(i, new Poss(i));
		}

		for (int[] other : validOther) {
			for (int i = 0; i < other.length; i++) {
				int val = other[i];

				Set<String> validFields = getValidForField(val, validValues);
				Poss poss = fieldMap.get(i);
				poss.merge(validFields);
			}
		}

		Map<Integer, String> finalOrder = new HashMap<>();

		List<Poss> collect = fieldMap.values().stream().collect(Collectors.toList());
		for (int i = 0; i < collect.size(); i++) {
			Poss poss = collect.get(i);
			if (poss.maybeFields.size() == 1) {
				Set<String> maybeFields = poss.maybeFields;
				String field = maybeFields.stream().findFirst().get();
				finalOrder.put(poss.pos, field);
				boolean removed = removeSingle(collect, poss, field);
				if (removed) {
					i = 0;
				}
			}
		}


		long product = 1;

		for (var entry : finalOrder.entrySet()) {
			if (entry.getValue().startsWith("departure")) {
				product *= yourTicket[entry.getKey()];
			}
		}
		return product;
	}

	private boolean removeSingle(List<Poss> fields, Poss poss, String field) {
		boolean changed = false;
		for (Poss p : fields) {
			if (poss.pos != p.pos) {
				if (p.maybeFields.contains(field)) {
					p.maybeFields.remove(field);
					changed = true;
				}
			}
		}
		return changed;
	}

	private Set<String> getValidForField(int val, Map<String, Field> validValues) {
		Set<String> collect = validValues.entrySet().stream()
										 .filter(entry -> entry.getValue().validValues.contains(val))
										 .map(entry -> entry.getKey())
										 .collect(Collectors.toSet());
		return collect;

	}

	private class Poss {
		int pos;
		Set<String> maybeFields;

		public Poss(int pos) {
			this.pos = pos;
			//this.maybeFields = new HashSet<>();
		}

		public void merge(Set<String> validFields) {
			if (maybeFields == null) {
				maybeFields = validFields;
			} else {
				Set<String> result = new HashSet<>();
				maybeFields.stream().filter(validFields::contains).forEach(result::add);
				validFields.stream().filter(maybeFields::contains).forEach(result::add);
				maybeFields = result;
			}
		}
	}

	private class Field {
		String field;
		Set<Integer> validValues;

		public Field(String field) {
			this.field = field;
			this.validValues = new HashSet<>();
		}
	}

	private class Ticket {

	}

	private long first(String[] rows) {

		Pattern pattern = Pattern.compile("\\d+");

		long sum = 0;

		int type = 0;
		Set<Integer> valid = new HashSet<>();
		for (String row : rows) {
			if (row.equals("")) {
				continue;
			}
			if (row.equals("your ticket:")) {
				type = 1;
				continue;
			}
			if (row.equals("nearby tickets:")) {
				type = 2;
				continue;
			}
			if (type == 0) {
				String[] parts = row.split(":")[1].split(" or ");
				checkAddValid(pattern.matcher(parts[0]), valid);
				checkAddValid(pattern.matcher(parts[1]), valid);
			} else if (type == 1) {
				String[] parts = row.split(",");
			} else if (type == 2) {
				String[] parts = row.split(",");
				for (String field : parts) {
					int val = Integer.parseInt(field);
					if (!valid.contains(val)) {
						sum += val;
					}
				}
			}
		}
		return sum;
	}

	private void checkAddValid2(String key, Matcher matcher, Map<String, Field> valid) {
		matcher.find();
		int low = Integer.parseInt(matcher.group());
		matcher.find();
		int high = Integer.parseInt(matcher.group());

		if (valid.containsKey(key)) {
			addValid(valid.get(key).validValues, low, high);
		} else {
			Field field = new Field(key);
			valid.put(key, field);
			addValid(field.validValues, low, high);
		}


	}

	private void addValid2(Set<Integer> valid, int low, int high) {
		for (int i = low; i <= high; i++) {
			valid.add(i);
		}
	}

	private void checkAddValid(Matcher matcher, Set<Integer> valid) {
		matcher.find();
		int low = Integer.parseInt(matcher.group());
		matcher.find();
		int high = Integer.parseInt(matcher.group());
		addValid(valid, low, high);
	}

	private void addValid(Set<Integer> valid, int low, int high) {
		for (int i = low; i <= high; i++) {
			valid.add(i);
		}
	}


}
