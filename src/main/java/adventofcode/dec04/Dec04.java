package adventofcode.dec04;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventofcode.util.FileReaderUtil;

public class Dec04 {

	public static void main(String[] args) {
		new Dec04().printValidator();
	}

	private void printValidator() {
		//Pattern p = Pattern.compile("^#[\\da-f]{6}$");
		FileWriter fw = null;
		try {
			fw = new FileWriter("C:\\jobb\\git\\advent2020\\src\\main\\java\\adventofcode\\dec04\\Validator.java");

			fw.write("package adventofcode.dec04;\r\n");
			fw.write("public class Validator{\r\n");
			fw.write("public boolean validateHcl(String hcl){\r\n");

			char[] input = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

			int counter = 0;

			for (int a = 0; a < input.length; a++) {
				for (int b = 0; b < input.length; b++) {
					for (int c = 0; c < input.length; c++) {
						for (int d = 0; d < input.length; d++) {
							for (int e = 0; e < input.length; e++) {
								for (int f = 0; f < input.length; f++) {
									fw.write(printIf(input[a], input[b], input[c], input[d], input[e], input[f]));
								}
							}
						}
					}
				}

			}
			fw.write("return false;\r\n}\r\n");
			fw.write("}");
			fw.write("}");

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				fw.flush();
				fw.close();
			} catch (Exception ex2) {
				throw new RuntimeException(ex2);
			}
		}
	}

	private String printIf(char a, char b, char c, char d, char e, char f) {
		StringBuilder sb = new StringBuilder();

		//if(hcl.charAt(0) == a && hcl.charAt(1) == b ....) {
		//return true;
		//}
		sb.append("if(hcl.charAt(0)== '#'")
		  .append("&& hcl.charAt(1)==").append("'").append(a).append("'")
		  .append("&& hcl.charAt(2)==").append("'").append(b).append("'")
		  .append("&& hcl.charAt(3)==").append("'").append(c).append("'")
		  .append("&& hcl.charAt(4)==").append("'").append(d).append("'")
		  .append("&& hcl.charAt(5)==").append("'").append(e).append("'")
		  .append("&& hcl.charAt(6)==").append("'").append(f).append("'")
		  .append("){\r\nreturn true;\r\n}\r\n");


		return sb.toString();

	}

	private void doIt() {

		String raw = FileReaderUtil.readFile("dec04.txt");

		String[] passports = raw.split("\\n\\n");
		int valid = 0;
		for (int i = 0; i < passports.length; i++) {
			if (new Passport2(passports[i]).isValid()) {
				valid++;
			}
		}

		System.out.println(valid);

	}


	private static final class Passport {
		private final boolean byr;// (Birth Year)
		private final boolean iyr;//Issue Year)
		private final boolean eyr;//Expiration Year)
		private final boolean hgt;//Height)
		private final boolean hcl;//Hair Color)
		private final boolean ecl;//Eye Color)
		private final boolean pid;//Passport ID)
		private final boolean cid;//Country ID

		private final String BYR = "byr";

		public Passport(String passport) {
			String[] parts = passport.replaceAll("\\n", " ").split(" ");

			Map<String, String> foundFields = new HashMap<>();

			for (String part : parts) {
				String[] fieldVal = part.split(":");
				foundFields.put(fieldVal[0], fieldVal[1]);
			}

			byr = foundFields.containsKey("byr");
			iyr = foundFields.containsKey("iyr");
			eyr = foundFields.containsKey("eyr");
			hgt = foundFields.containsKey("hgt");
			hcl = foundFields.containsKey("hcl");
			ecl = foundFields.containsKey("ecl");
			pid = foundFields.containsKey("pid");
			cid = foundFields.containsKey("cid");

		}


		public boolean isValid() {
			return byr && iyr && eyr && hgt && hcl && ecl && pid;
		}
	}

	private static final class Passport2 {
		private final boolean byr;// (Birth Year)
		private final boolean iyr;//Issue Year)
		private final boolean eyr;//Expiration Year)
		private final boolean hgt;//Height)
		private final boolean hcl;//Hair Color)
		private final boolean ecl;//Eye Color)
		private final boolean pid;//Passport ID)
		private final boolean cid;//Country ID

		public Passport2(String passport) {
			String[] parts = passport.replaceAll("\\n", " ").split(" ");

			Map<String, String> foundFields = new HashMap<>();

			for (String part : parts) {
				String[] fieldVal = part.split(":");
				foundFields.put(fieldVal[0], fieldVal[1]);
			}

			byr = foundFields.containsKey("byr") && validateByr(foundFields.get("byr"));
			iyr = foundFields.containsKey("iyr") && validateIyr(foundFields.get("iyr"));
			eyr = foundFields.containsKey("eyr") && validateEyr(foundFields.get("eyr"));
			hgt = foundFields.containsKey("hgt") && validateHgt(foundFields.get("hgt"));
			hcl = foundFields.containsKey("hcl") && validateHcl(foundFields.get("hcl"));
			ecl = foundFields.containsKey("ecl") && validateEcl(foundFields.get("ecl"));
			pid = foundFields.containsKey("pid") && validatePid(foundFields.get("pid"));
			cid = foundFields.containsKey("cid") && validateCid(foundFields.get("cid"));

		}


		private boolean validateByr(String byr) {
			int val = Integer.parseInt(byr);
			return val >= 1920 && val <= 2002;
		}

		private boolean validateIyr(String byr) {
			int val = Integer.parseInt(byr);
			return val >= 2010 && val <= 2020;
		}

		private boolean validateEyr(String byr) {
			int val = Integer.parseInt(byr);
			return val >= 2020 && val <= 2030;
		}

		private boolean validateHgt(String byr) {
			try {
				if (byr.endsWith("cm")) {
					int val = Integer.parseInt(byr.substring(0, 3));
					return range(val, 150, 193);
				} else if (byr.endsWith("in")) {
					int val = Integer.parseInt(byr.substring(0, 2));
					return range(val, 59, 76);
				}
				return false;
			} catch (Exception ex) {
				return false;
			}
		}

		private boolean validateHcl(String byr) {
			//a # followed by exactly six characters 0-9 or a-f.
			Pattern p = Pattern.compile("^#[\\da-f]{6}$");
			Matcher m = p.matcher(byr);
			return m.matches();
		}

		private boolean validateHcl2(String hcl) {
			if (hcl != null && hcl.length() == 7)
				if (hcl.charAt(0) == '#') {
					if (hcl.charAt(1) == 'a' || hcl.charAt(1) == 'b' || hcl.charAt(1) == 'c' || hcl.charAt(1) == 'd' || hcl.charAt(1) == 'e' || hcl.charAt(1) == 'f'
							|| hcl.charAt(1) == '0' || hcl.charAt(1) == '1' || hcl.charAt(1) == '2' || hcl.charAt(1) == '3' || hcl.charAt(1) == '4' || hcl.charAt(1) == '5' || hcl.charAt(1) == '6' ||
							hcl.charAt(1) == '7' || hcl.charAt(1) == '8' || hcl.charAt(1) == '9') {
						if (hcl.charAt(2) == 'a' || hcl.charAt(2) == 'b' || hcl.charAt(2) == 'c' || hcl.charAt(2) == 'd' || hcl.charAt(2) == 'e' || hcl.charAt(2) == 'f'
								|| hcl.charAt(2) == '0' || hcl.charAt(2) == '1' || hcl.charAt(2) == '2' || hcl.charAt(2) == '3' || hcl.charAt(2) == '4' || hcl.charAt(2) == '5' || hcl.charAt(2) == '6' ||
								hcl.charAt(2) == '7' || hcl.charAt(2) == '8' || hcl.charAt(2) == '9') {
							if (hcl.charAt(3) == 'a' || hcl.charAt(3) == 'b' || hcl.charAt(3) == 'c' || hcl.charAt(3) == 'd' || hcl.charAt(3) == 'e' || hcl.charAt(3) == 'f'
									|| hcl.charAt(3) == '0' || hcl.charAt(3) == '1' || hcl.charAt(3) == '2' || hcl.charAt(3) == '3' || hcl.charAt(3) == '4' || hcl.charAt(3) == '5' || hcl.charAt(3) == '6' ||
									hcl.charAt(3) == '7' || hcl.charAt(3) == '8' || hcl.charAt(3) == '9') {
								if (hcl.charAt(4) == 'a' || hcl.charAt(4) == 'b' || hcl.charAt(4) == 'c' || hcl.charAt(4) == 'd' || hcl.charAt(4) == 'e' || hcl.charAt(4) == 'f'
										|| hcl.charAt(4) == '0' || hcl.charAt(4) == '1' || hcl.charAt(4) == '2' || hcl.charAt(4) == '3' || hcl.charAt(4) == '4' || hcl.charAt(4) == '5' || hcl.charAt(4) == '6' ||
										hcl.charAt(4) == '7' || hcl.charAt(4) == '8' || hcl.charAt(4) == '9') {
									if (hcl.charAt(5) == 'a' || hcl.charAt(5) == 'b' || hcl.charAt(5) == 'c' || hcl.charAt(5) == 'd' || hcl.charAt(5) == 'e' || hcl.charAt(5) == 'f'
											|| hcl.charAt(5) == '0' || hcl.charAt(5) == '1' || hcl.charAt(5) == '2' || hcl.charAt(5) == '3' || hcl.charAt(5) == '4' || hcl.charAt(5) == '5' || hcl.charAt(5) == '6' ||
											hcl.charAt(5) == '7' || hcl.charAt(5) == '8' || hcl.charAt(5) == '9') {
										return true;
									}
								}

							}
						}
					}
				}
			return false;
		}

		private boolean validateEcl(String byr) {
			return byr.equals("amb") || byr.equals("blu") || byr.equals("brn") || byr.equals("gry") || byr.equals("grn") || byr.equals("hzl") || byr.equals("oth");
		}

		private boolean validatePid(String byr) {
			Pattern p = Pattern.compile("^\\d{9}$");
			Matcher m = p.matcher(byr);
			return m.matches();
		}

		private boolean validateCid(String byr) {
			return false;
		}


		private boolean range(int val, int lowIncl, int endIncl) {
			return val >= lowIncl && val <= endIncl;
		}

		public boolean isValid() {
			return byr && iyr && eyr && hgt && hcl && ecl && pid;
		}
	}


}
