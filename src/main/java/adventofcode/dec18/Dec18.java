package adventofcode.dec18;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventofcode.util.FileReaderUtil;

public class Dec18 {

	public static void main(String[] args) {
		String[] rows = FileReaderUtil.readRowsAsArray("dec18.txt");

		BigInteger sum = new Dec18().first(rows);
		System.out.println(sum);

		BigInteger sum2 = new Dec18().second(rows);
		System.out.println(sum2);

	}

	private BigInteger first(String[] rows) {
		BigInteger totSum = BigInteger.ZERO;

		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			System.out.println("current row: " + row);
			Token[] tokens = tokenize(row);

			long current = calcRow(tokens, 0);
			totSum = totSum.add(BigInteger.valueOf(current));
		}
		return totSum;
	}

	private BigInteger second(String[] rows) {
		BigInteger totSum = BigInteger.ZERO;

		for (int i = 0; i < rows.length; i++) {
			String row = rows[i];
			System.out.println("current row: " + row);
			List<Token> postfix = toPostfix(tokenize(row));

			Expr expr = constructTree(postfix.toArray(Token[]::new));
			totSum = totSum.add(BigInteger.valueOf(expr.eval()));
		}
		return totSum;
	}

	private List<Token> toPostfix(Token[] tokens) {
		List<Token> result = new ArrayList<>();
		Stack<Token> stack = new Stack<>();

		for (int i = 0; i < tokens.length; ++i) {
			Token t = tokens[i];

			if (t.isDigit) {
				result.add(t);
			} else if (t.isOpening) {
				stack.push(t);

			} else if (t.isClosing) {
				while (!stack.isEmpty() && !stack.peek().isOpening) {
					result.add(stack.pop());
				}
				if (!stack.empty()) {
					stack.pop();
				}
			} else {
				while (!stack.isEmpty() && precedence(t) <= precedence(stack.peek())) {
					result.add(stack.pop());
				}
				stack.push(t);
			}
		}

		while (!stack.isEmpty()) {
			result.add(stack.pop());
		}

		System.out.print("postfix: ");
		for (Token t : result) {
			System.out.print(t + " ");
		}
		System.out.println("");

		return result;
	}


	private Expr constructTree(Token[] tokens) {
		Stack<Expr> st = new Stack<>();
		Expr expr, right, left;

		for (int i = 0; i < tokens.length; i++) {
			if ((tokens[i].isDigit)) {
				expr = new Expr(tokens[i].digit);
				st.push(expr);
			} else {
				expr = new Expr(null, null, tokens[i].operator);
				right = st.pop();
				left = st.pop();
				expr.right = right;
				expr.left = left;
				st.push(expr);
			}
		}
		expr = st.peek();
		st.pop();

		return expr;
	}

	private int precedence(Token t) {
		if (t.isOperator) {
			switch (t.operator) {
				case PLUS:
				case MINUS:
					return 2;
				case MULT:
					return 1;
			}
		}
		return -1;
	}

	private long calcRow(Token[] tokens, int index) {
		long current = 0;
		Operator currentOp = null;
		for (int i = index; i < tokens.length; i++) {
			Token token = tokens[i];
			if (token.isDigit) {
				if (currentOp == null) {
					current = token.digit;
				} else {
					current = calcExpr(current, token.digit, currentOp);
				}
			} else if (token.isOperator) {
				currentOp = token.operator;
			} else if (token.isOpening) {

				long subExpr = calcRow(tokens, i + 1);
				if (currentOp != null) {
					current = calcExpr(current, subExpr, currentOp);
				} else {
					current = subExpr;
				}
				int endIndex = findIndex(tokens, i);
				i = endIndex;
			} else if (token.isClosing) {
				return current;
			}
		}
		return current;
	}

	private long calcExpr(long current, long digit, Operator operator) {
		switch (operator) {
			case MULT:
				return current * digit;
			case PLUS:
				return current + digit;
			case MINUS:
				return current - digit;
		}
		throw new RuntimeException("unhandled");
	}

	private int findIndex(Token[] tokens, int index) {
		int counter = 0;
		for (int i = index; i < tokens.length; i++) {
			if (tokens[i].isOpening) {
				counter++;
			} else if (tokens[i].isClosing) {
				counter--;
				if (counter == 0) {
					return i;
				}
			}
		}
		throw new RuntimeException("did not find");
	}

	private Token[] tokenize(String row) {
		String[] split = row.split(" ");

		List<Token> tokens = new ArrayList<>();
		for (String s : split) {
			if (isDigit(s)) {
				tokens.add(new Token(Integer.parseInt(s)));
			} else if (isOp(s)) {
				tokens.add(new Token(getOperator(s)));
			} else if (isOpening(s)) {
				char[] arr = s.toCharArray();
				for (int i = 0; i < arr.length; i++) {
					char curr = arr[i];
					if (curr == '(') {
						tokens.add(new Token(true));
					} else {
						break;
					}
				}
				long digit = getDigitAnywhere(s);
				tokens.add(new Token(digit));
			} else if (isClosing(s)) {
				long digit = getDigitAnywhere(s);
				tokens.add(new Token(digit));
				char[] arr = s.toCharArray();
				for (int i = arr.length - 1; i >= 0; i--) {
					char curr = arr[i];
					if (curr == ')') {
						tokens.add(new Token(false));
					} else {
						break;
					}
				}

			}

		}
		return tokens.toArray(Token[]::new);
	}

	private Operator getOperator(String s) {
		if (s.equals("+")) {
			return Dec18.Operator.PLUS;
		} else if (s.equals("-")) {
			return Dec18.Operator.MINUS;
		} else {
			return Dec18.Operator.MULT;
		}
	}

	private static Pattern fullDigit = Pattern.compile("^\\d+$");
	private static Pattern operatorPattern = Pattern.compile("[\\+\\-\\*]");
	private static Pattern anywhereDigit = Pattern.compile("\\d+");

	private boolean isDigit(String s) {
		Matcher matcher = fullDigit.matcher(s);
		return matcher.matches();
	}

	private long getDigitAnywhere(String s) {
		Matcher matcher = anywhereDigit.matcher(s);
		matcher.find();
		return Long.parseLong(matcher.group());
	}

	private boolean isOp(String s) {
		Matcher matcher = operatorPattern.matcher(s);
		return matcher.matches();
	}

	private boolean isOpening(String s) {
		return s.startsWith("(");
	}

	private boolean isClosing(String s) {
		return s.endsWith(")");
	}

	enum Operator {
		PLUS, MINUS, MULT
	}

	public static class Token {

		@Override
		public String toString() {
			if (isOperator)
				return operator + "";
			if (isDigit)
				return digit + "";
			if (isOpening)
				return "(";
			if (isClosing)
				return ")";
			throw new RuntimeException("can't print");
		}

		private boolean isPrec;
		private boolean isOpening;
		private boolean isClosing;
		private boolean isDigit;
		private boolean isOperator;
		private long digit;
		private Operator operator;

		public Token(long digit) {
			this.isDigit = true;
			this.digit = digit;
		}

		public Token(Operator op) {
			this.isOperator = true;
			this.operator = op;
			isPrec = op != Operator.MULT;
		}

		public Token(boolean isOpening) {
			this.isOpening = isOpening;
			this.isClosing = !isOpening;
		}
	}

	public static class Expr {

		Expr left;
		Expr right;
		Operator operator;
		long number;
		boolean hasNumber = false;

		public Expr(Expr left, Expr right, Operator operator) {
			this.left = left;
			this.right = right;
			this.operator = operator;
		}

		public Expr(Expr left) {
			this.left = left;
		}

		public Expr(long number) {
			this.number = number;
			hasNumber = true;
		}

		long eval() {
			if (hasNumber) {
				return number;
			} else {
				switch (operator) {
					case MULT:
						return left.eval() * right.eval();
					case PLUS:
						return left.eval() + right.eval();
					case MINUS:
						return left.eval() - right.eval();
				}
				throw new RuntimeException("errooooor");
			}
		}
	}
}
