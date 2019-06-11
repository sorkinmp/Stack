
public class InfixExpression {

	// Variables
	private String infix;

	// Constructors

	/**
	 * Creates a new InfixExpression object, cleans up infix
	 * expression using clean() method, and checks if it is a
	 * valid expression. If it is not valid, throw an exception.
	 * @param input The input string containing the infix expression.
	 */
	// default constructor
	public InfixExpression(String input) {
		infix = input;
		clean();  // clean the infix
		// if invalid expression
		if (isValid() == false) {
			throw new IllegalArgumentException();
		}
	}

	// Methods

	/**
	 * Returns the infix expression.
	 */
	public String toString() {
		return infix;
	}

	/**
	 * Checks to see if parentheses are balanced
	 * in infix expression.
	 * @return true if parentheses are balanced.
	 * False otherwise.
	 */

	private boolean isBalanced() {
		// make a stack
		LinkedStack <Character> openDelimStack = new LinkedStack<Character>();

		int characterCount = infix.length();
		boolean isBalanced = true;
		int index = 0;
		char nextCharacter = ' ';

		// while the parentheses remain balanced, and 
		// index is less than the total character count
		while (isBalanced && (index < characterCount)) {
			nextCharacter = infix.charAt(index);
			switch (nextCharacter) {

			// if there is an "open" character, push to stack
			case '(' : case '[': case '{':
				openDelimStack.push(nextCharacter);
				break;

			case ')' : case ']': case '}':
				// if the stack is empty, return false
				// since that means the parentheses are unbalanced
				if (openDelimStack.isEmpty()) {
					return false;
				}
				else {
					// pop the top of the stack before continuing
					char openDelim = openDelimStack.pop();
					isBalanced = isPaired(openDelim, nextCharacter);
				}
				break;

			default : break;
			}
			index++;
		}

		// if the stack is not empty after performing the above
		// operations, then return false
		if (!openDelimStack.isEmpty()) {
			return false;
		}
		// if nothing has returned false, return isBalanced (true)
		return isBalanced;
	}

	/**
	 * A private helper method to determine if there is an open
	 * and closed expression i.e. a '(' and a ')'.
	 * @param open The open character: '(', '[', or '{'
	 * @param close The close character: ')', ']', or '}' 
	 * @return True if there is a correct pair, false otherwise.
	 */

	private boolean isPaired(char open, char close) {
		return(open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}');  
	}


	/**
	 * Checks to see if infix expression is valid
	 * (are there illegal characters, are the
	 * parentheses balanced, does each operand have
	 * an operator before and after it).
	 * @return true if the infix expression is valid.
	 * False otherwise.
	 */
	private boolean isValid() {

		boolean validChecker = true;
		if(isBalanced() != true) {
			return false;
		}
		String validChars = "+\\-*/%^()1234567890 ";  // all possible valid characters
		String validOperators = "+\\-*/%^";  // all possible valid operations
		int operandCount = 0;
		int operatorCount = 0;
		String cleanInfix = infix.replaceAll(" ", "");
		
		
		for(int i = 0; i < cleanInfix.length(); i++) {
			if(!validChars.contains(""+cleanInfix.charAt(i))) {

				return false;
			}
			// run loop until i is the same as the infix length
			// and as long as the current character is a digit
			while(i != cleanInfix.length() && Character.isDigit(cleanInfix.charAt(i))) {
				
				// if i equals the infix length minus 1 and there is a digit at i
				// or if i equals the infix length minus 2 and there is not a digit at i + 1
				// or if i equals the infix length minus 2 and the digit at i + 1 is a ')'
				// increase operand count by 1
				if((i == cleanInfix.length()-1 && Character.isDigit(cleanInfix.charAt(i))) 
					|| ((i !=cleanInfix.length() - 2 && !Character.isDigit(cleanInfix.charAt(i+1))) 
					|| (i == cleanInfix.length() - 2 && cleanInfix.charAt(i+1) == ')'))) {
					operandCount++;	
				}
				i++;
			}
			
			// if i is not equal to the infix length and validOperator string contains the character at i
			// increase operator count by 1
			if(i != cleanInfix.length() && validOperators.contains("" + cleanInfix.charAt(i))) {
				operatorCount++;
				
				// if i minus 1 is less than 0 or there is not a digit at i - 1 and there's no closed
				// parentheses at i -1 or there is not a digit at i - 1, then the infix is not valid
				if(i - 1 < 0 || (!Character.isDigit(cleanInfix.charAt(i-1)) && cleanInfix.charAt(i-1) != ')') 
				   || (!Character.isDigit(cleanInfix.charAt(i+1)) && cleanInfix.charAt(i+1) != '(') ) {
					return false;
				}
			}
			// if i is not equal to the infix length and the character at i is
			// an open parentheses, and the character at i + 1 is a closed
			// parentheses, the infix is not valid
			if(i != cleanInfix.length() && cleanInfix.charAt(i) == '(') {
				if(cleanInfix.charAt(i + 1) == ')') {
					return false;
				}
			}
			
		}
		// if there are no operators and the operand count minus the operator count
		// is not equal to 1, then the infix is not valid
		if(operatorCount != 1  && operandCount-operatorCount != 1 ) {
			return false;
		}
		return validChecker;
	}

	/**
	 * Puts a single space between adjacent tokens
	 * and removes all trailing or leading spaces.
	 */
	private void clean() {
		// first remove all spaces
		infix = infix.replaceAll(" ", "");
		String fixedString = "";
		// then loop and add space between adjacent characters
		// and remove trailing and leading spaces
		for(int i = 0; i < infix.length(); i++) {
			while(i != infix.length() && Character.isDigit(infix.charAt(i))) {
				fixedString = fixedString + "" + infix.charAt(i);
				i++;
				if(i != infix.length() && !Character.isDigit(infix.charAt(i))) {
					fixedString = fixedString + " ";
				}
			}
			if(i == infix.length()) {
				break;
			}
			if((!Character.isDigit(infix.charAt(i)))) {
				fixedString = fixedString + "" + infix.charAt(i);
			}
			if(i != infix.length()) {
				fixedString = fixedString + " ";
			}
		}
		infix = fixedString;
	}

	/**
	 * Returns the postfix expression that corresponds
	 * with the given infix expression.
	 * @return the postfix expression.
	 */
	public String getPostfixExpression() {
		// create an operator stack to be filled with characters
		LinkedStack<Character> operatorStack = new LinkedStack<Character>();
		// create a postfix string
		String postfixString = "";
		// loops through infix string
		for(int i = 0; i < infix.length(); i++) {
			char nextChar = infix.charAt(i);

			switch(nextChar) {
			// if the character is a number 0-9
			case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':
				String variable = "";
				while(i != infix.length() && Character.isDigit(infix.charAt(i))) {
					variable = variable + infix.charAt(i);
					i++;

				}
				postfixString = postfixString + variable + " ";

				break;
				// if character is an operation
			case '^' :
				operatorStack.push(nextChar);
				break;

			case '+' : case '-' : case '*' : case '/': case '%':
				// while the stack isn't empty and the character has lower or
				// equal to precedence as the following character
				// add the character at the top and pop from the stack
				while(!operatorStack.isEmpty() && precedenceEval(nextChar) <= precedenceEval(operatorStack.peek())) {
					postfixString = postfixString + operatorStack.peek() + " ";
					operatorStack.pop();
				}
				operatorStack.push(nextChar);
				break;
				// if character is a parentheses
			case '(' :
				operatorStack.push(nextChar);
				break;

			case ')':
				char topOperator = operatorStack.pop();
				while(topOperator != '(') {
					postfixString = postfixString + topOperator + " ";
					topOperator = operatorStack.pop();
				}
				break;
			default: break;
			}

		}
		while(!operatorStack.isEmpty()) {
			char topOperator = operatorStack.pop();
			postfixString = postfixString + topOperator + " "; 
		}
		if(postfixString.charAt(postfixString.length()-1) == ' ') {
			postfixString = postfixString.substring(0, postfixString.length()-1);
		}

		return postfixString;
	}

	/**
	 * A helper method to determine precedence of operations.
	 * @param precedenceInput A character representing an operation.
	 * @return An int representing the precedence of the operation.
	 * The higher the int, the greater the precedence. Returns -1
	 * if the character input is not a valid operation character.
	 */
	private int precedenceEval(char precedenceInput) {
		switch(precedenceInput) {
		case '-' : case '+':
			return 0;

		case '*' : case '/': case '%':
			return 1;

		case '^':
			return 2;

		default:
			return -1;
		}

	}

	/**
	 * Evaluate the infix expression after retrieving
	 * the resultant postfix expression.
	 * @return The result of the postfix expression.
	 */
	public int evaluatePostFix() {
		// create string stack to evaluate postfix
		LinkedStack<String> evalStack = new LinkedStack<String>();
		String postfixString = getPostfixExpression();
		for(int i = 0; i < postfixString.length(); i++) {
			char nextChar = postfixString.charAt(i);
			switch(nextChar) {
			// if character is 0-9
			case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':

				String variable = "";
				while(i != postfixString.length() && Character.isDigit(postfixString.charAt(i))) {
					variable = variable + postfixString.charAt(i);
					i++;
				}
				evalStack.push(variable);
				break;

			// if character is an operator
			case '+' : case '-' : case '*' : case '/' : case '%': case '^':
				int operandTwo = Integer.parseInt(evalStack.pop());
				int operandOne = Integer.parseInt(evalStack.pop());
				int result = 0;
				if(nextChar == '+') {
					result = operandOne + operandTwo;
				}
				else if(nextChar == '-') {
					result = operandOne - operandTwo;
				}
				else if(nextChar == '*') {
					result = operandOne * operandTwo;
				}
				else if(nextChar == '/') {
					result = operandOne / operandTwo;
				}
				else if(nextChar == '%') {
					result = operandOne % operandTwo;
				}
				else if(nextChar == '^') {
					result = (int) Math.pow(operandOne, operandTwo);
				}
				// push the result to the evaluation stack
				evalStack.push("" + result);
				break;

			default: break;
			}
		}
		return Integer.parseInt(evalStack.pop());
	}

	/**
	 * Directly evaluates the infix expression without
	 * first converting to postifx expression.
	 * @return the integer result of the calculations
	 * of the infix expression.
	 */
	public int evaluate() {
		LinkedStack<Character> operatorStack = new LinkedStack<Character>();
		LinkedStack<String> valueStack = new LinkedStack<String>();

		for(int i = 0; i < infix.length(); i++) {
			char nextChar = infix.charAt(i);

			switch(nextChar) {
			case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':
				String variable = "";
				// while i is not equal to the infix length and there is a digit at i
				// add the character to the string
				while(i != infix.length() && Character.isDigit(infix.charAt(i))) {
					variable = variable + infix.charAt(i);
					i++;

				}
				valueStack.push(variable);
				break;

			// if the character is a '^' push that to the operator stack
			case '^' :
				operatorStack.push(nextChar);
				break;

			case '+' : case '-' : case '*' : case '/' : case '%':
				// while the operator stack is not empty and there is a lower precedence
				// between the current character and the character on the top of the operator stack
				while(!operatorStack.isEmpty() && precedenceEval(nextChar) <= precedenceEval(operatorStack.peek()) ) {
					char topOperator = operatorStack.pop();
					int operandTwo = Integer.parseInt(valueStack.pop());
					int operandOne = Integer.parseInt(valueStack.pop());
					int result = 0;
					// run through and complete the desired operation, storing in result
					if(topOperator == '+') {
						result = operandOne + operandTwo;
					}
					else if(topOperator == '-') {
						result = operandOne - operandTwo;
					}
					else if(topOperator == '*') {
						result = operandOne * operandTwo;
					}
					else if(topOperator == '/') {
						result = operandOne / operandTwo;
					}
					else if(topOperator == '%') {
						result = operandOne % operandTwo;
					}
					else if(topOperator == '^') {

						result = (int) Math.pow(operandOne, operandTwo);
					}
					valueStack.push("" + result);
				}
				operatorStack.push(nextChar);
				break;

			// with open parentheses, simply push to operator stack
			case '(' :
				operatorStack.push(nextChar);
				break;

			case ')':
				char topOperator = operatorStack.pop();
				// run loop until you reach an open parentheses
				// complete the operation of topOperator and store in result
				while(topOperator != '(') {

					int operandTwo = Integer.parseInt(valueStack.pop());
					int operandOne = Integer.parseInt(valueStack.pop());
					int result = 0;
					if(topOperator == '+') {
						result = operandOne + operandTwo;
					}
					else if(topOperator == '-') {
						result = operandOne - operandTwo;
					}
					else if(topOperator == '*') {
						result = operandOne * operandTwo;
					}
					else if(topOperator == '/') {
						result = operandOne / operandTwo;
					}
					else if(topOperator == '%') {
						result = operandOne % operandTwo;
					}
					else if(topOperator == '^') {

						result = (int) Math.pow(operandOne, operandTwo);
					}
					valueStack.push("" + result);
					topOperator = operatorStack.pop();

				}

				break;
			default: break;
			} // end switch case
		} // end for loop
		// while the operator stack is not empty, run the remaining operations
		while(!operatorStack.isEmpty()) {
			char topOperator = operatorStack.pop();
			int operandTwo = Integer.parseInt(valueStack.pop());
			int operandOne = Integer.parseInt(valueStack.pop());
			int result = 0;
			if(topOperator == '+') {
				result = operandOne + operandTwo;
			}
			else if(topOperator == '-') {
				result = operandOne - operandTwo;
			}
			else if(topOperator == '*') {
				result = operandOne * operandTwo;
			}
			else if(topOperator == '/') {
				result = operandOne / operandTwo;
			}
			else if(topOperator == '%') {
				result = operandOne % operandTwo;
			}
			else if(topOperator == '^') {
				result = (int) Math.pow(operandOne, operandTwo);
			}
			valueStack.push("" + result);

		}  // end while loop
		// finally return the value in the value stack
		return Integer.parseInt(valueStack.peek());
	}
}
