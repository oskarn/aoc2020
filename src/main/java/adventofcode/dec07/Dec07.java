package adventofcode.dec07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.FileReaderUtil;

public class Dec07 {

	private Map<String, Bag> bags;

	public static void main(String[] args) {
		new Dec07().first();
	}

	private void first() {

		String[] rows = FileReaderUtil.readRowsAsArray("dec07.txt");

		bags = new HashMap<>();

		for (String row : rows) {
			String[] first = row.split("contain");
			String currentBagId = first[0].replaceAll("bags", "").trim();
			List<BagRel> children = new ArrayList<>();

			if (first[1].equals(" no other bags.")) {
				;//nop
			} else {
				String[] split = first[1].split(",");
				for (String bagString : split) {
					children.add(new BagRel(bagString));
				}
			}
			setChildren(currentBagId, children);
			addParent(children, currentBagId);
		}

		Bag shiny_gold_bag = bags.get("shiny gold");

		count(shiny_gold_bag);
		System.out.println(unq.size());

		int totalBagCount = countBags(shiny_gold_bag) - 1;
		System.out.println(totalBagCount);
	}

	private int countBags(Bag bag) {
		if (bag.children.isEmpty()) {
			return 1;
		}
		int count = 1;
		for (BagRel child : bag.children) {
			count += (child.count * countBags(child.childBag));
		}
		return count;
	}

	private Set<String> unq = new HashSet<>();

	private void count(Bag bag) {
		if (!bag.id.equals("shiny gold")) {
			unq.add(bag.id);
		}
		if (bag.containedIn.size() == 0) {
			return;
		}
		bag.containedIn.forEach(this::count);
	}

	private void addParent(List<BagRel> children, String currentBagId) {
		for (BagRel rel : children) {
			addParent(rel.childBag.id, currentBagId);
		}
	}

	private void addParent(String bagId, String parentId) {
		Bag bag = getOrCreate(bagId);
		Bag parent = getOrCreate(parentId.trim());
		bag.addContainedIn(parent);
	}

	private void setChildren(String currentBagId, List<BagRel> children) {
		Bag bag = getOrCreate(currentBagId);
		bag.setChildren(children);
	}

	private Bag getOrCreate(String bagId) {
		bagId = bagId.trim();
		if (bags.containsKey(bagId)) {
			return bags.get(bagId);
		}
		Bag bag = new Bag(bagId);
		bags.put(bagId, bag);
		return bag;
	}

	private static final class Bag {
		private final String id;
		private List<BagRel> children;

		private List<Bag> containedIn;

		public Bag(String id) {
			this.id = id;
			children = new ArrayList<>();
			containedIn = new ArrayList<>();
		}

		public void setChildren(List<BagRel> children) {
			this.children = children;
		}

		public void addContainedIn(Bag parentId) {
			containedIn.add(parentId);
		}

		@Override
		public String toString() {
			return "Bag{" +
					"id='" + id + '\'' +
					", children=" + children +
					'}';
		}
	}

	private final class BagRel {
		final int count;
		final Bag childBag;

		public BagRel(String bagString) {
			// -> 2 dotted tan bags, 2 bright green bags, 5 light black bags, 3 vibrant silver bags.
			String trimmed = bagString.replaceAll("\\.", "").trim();
			int subBag1Count = Integer.parseInt(trimmed.charAt(0) + "");
			String subBag1Id = trimmed.substring(1);

			if (!subBag1Id.endsWith("s")) {
				subBag1Id = subBag1Id + "s";
			}

			this.count = subBag1Count;
			this.childBag = getOrCreate(subBag1Id.replaceAll("bags", "").trim());
		}

		@Override
		public String toString() {
			return "BagRel{" +
					"count=" + count +
					", bag=" + childBag.id +
					'}';
		}
	}


}
