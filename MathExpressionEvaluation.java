import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Stack;

public class MathExpressionEvaluation {
	
	static Stack<String> operatorStack=new Stack<String>();
	static Stack<String> operandStack=new Stack<String>();
	
	static final String operatorRegEx="[/*+()-]";
	static final String operandRegEx="^[0-9]+$";
	
	public static void main(String args[]) throws IOException
	{
		// Reading input from the user
		BufferedReader buf=new BufferedReader(new InputStreamReader (System.in));
		String readerinput=buf.readLine();
		
		// Tokenize Input String
		StringTokenizer inputArray=tokenizeString(readerinput,false);
		
		// Convert input to Postfix Notation
		String postfix=convertToPostfix(inputArray);
		
		// Print Postfix Notation
		System.out.println("Postfix Notation: "+postfix); 
		
		// Tokenize Postfix Notation
		StringTokenizer postfixArray=tokenizeString(postfix,true);
		
		// Evaluate Postfix - compute the value and store in operandStack
		String result=evaluatePostfix(postfixArray);
		
		// We are done!
		System.out.println("Result: "+result); // Print output value
	}
	
	private static StringTokenizer tokenizeString(String tokenStr,boolean isInPostFix) {
		
		if(!isInPostFix)	
			return new StringTokenizer(tokenStr,"*/()+-",true);
		return new StringTokenizer(tokenStr," ");
	}
	
	private static String convertToPostfix(StringTokenizer inputArray)  {
		String currentToken="";
		while(inputArray.hasMoreTokens())
		{		
			currentToken=inputArray.nextToken();
			
			// operand
			if(currentToken.matches(operandRegEx))
			{
				operandStack.push(currentToken);
				continue;
			}
			
			// not operator
			if(!currentToken.matches(operatorRegEx))
			{
				System.out.println("Ignoring a token which is not understood...");
				continue;
			}

			// if stack is empty OR input char is '(' OR precedence is more than top of stack - push character
			if(operatorStack.isEmpty() || currentToken.equals("(")) 
			{
				operatorStack.push(currentToken);
				continue;
			}

			int precedenceCurrentToken =  precedence(currentToken);
			int precedenceOnStack = precedence((String)operatorStack.peek());
			
			// input operator has less precedence
			if (precedenceCurrentToken <= precedenceOnStack)
				convertExpressionToPostFix(currentToken);
			else
				operatorStack.push(currentToken);
		}

		if(!operatorStack.isEmpty())
			convertExpressionToPostFix(currentToken);
		return (String)operandStack.pop();
	}
	
	private static void convertExpressionToPostFix(String operator) { 
		String lhs="", rhs="", op="";

		// loop as long as precedence of c is less than stack top
		while(!operatorStack.isEmpty() && precedence(operator) <= precedence((String)operatorStack.peek())) 
		{
			op=(String)operatorStack.pop(); //pop out operator
			if(!op.equals("(")) // pop 2 operands and concatenate if operator is not (
			{
				rhs=operandStack.pop();
				lhs=(String)operandStack.pop();
				operandStack.push(lhs+ " "+rhs+" "+op);
			}
			else // operator on stack top is (
				break;
		}
		
		// Push current token on the stack
		if(!operator.equals(")") && !operator.equals(" "))
			operatorStack.push(operator);
	}

	private static int precedence(String inputStr) 
	{	// Returns Precedence of given token
		int p=-1;
		char firstChar = inputStr.charAt(0);
		switch(firstChar)
		{
			case '*': case '/':p= 2; break; 
			case '+': case '-':p= 1; break; 
			case '(': case ')':p= 0; break;
		}
		return p;
	}
	
	// Evaluates the given postfix expression
	private static String evaluatePostfix(StringTokenizer postfixArray)
	{
		String currentToken="";
		while(postfixArray.hasMoreTokens())
		{		
			currentToken=postfixArray.nextToken();
			// is it an operand?
			if(currentToken.matches(operandRegEx))
				operandStack.push(currentToken);
			// or is it operator?
			else if(currentToken.matches(operatorRegEx)) 
				operate(currentToken);
		}
		return operandStack.pop();
	}
	
	private static void operate(String currentToken) 
	{
		int right = Integer.parseInt(operandStack.pop());
		int left = Integer.parseInt(operandStack.pop());
		int tempResult=0;
		char c=currentToken.charAt(0);
		switch(c)
		{
			case '*': tempResult=left*right; break;
			case '+': tempResult=left+right; break;
			case '-': tempResult=left-right; break;
			case '/': 	try 
						{
							tempResult=left/right; 
						}
						catch (ArithmeticException e)
						{	
							System.out.println(e.toString()); 
							System.exit(0);
						}
			  			break;
		}
		operandStack.push(String.valueOf(tempResult));
	}
}