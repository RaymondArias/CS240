// Name: Arias, Raymond
// Project: 3
// Due: 06/04/15
// Course: cs-240-02-sp15
//
// Description:
//				The program converts infix expression into postfix and prefix notation
//				expressions.
import java.util.Arrays;
import java.util.Stack;


public class InfixExpression {
	
	public static String[] convertToPostfix(String[] infixExpression)
	{
		Stack <String> postfixStack = new Stack<>();
		String []retStr = new String[infixExpression.length];
		int counter = 0;
		
		if(!isBalanced(infixExpression))
			throw new IllegalArgumentException("Error expression is not balanced");
		if(!hasCorrectOperations(infixExpression))
			throw new IllegalArgumentException("Error the expression is not correct, missing either operand or operator");
		
		
		for(int i = 0; i < infixExpression.length; i++)
		{
			if((Character.isAlphabetic(infixExpression[i].charAt(0)) || (Character.isDigit(infixExpression[i].charAt(0)))))
			{
				retStr[counter] = infixExpression[i];
				counter++;
			}
			else if(infixExpression[i].equals("("))
			{
				postfixStack.push(infixExpression[i]);
					
			}
			else if((postfixStack.isEmpty() || (postfixStack.peek().equals("("))))
			{
				if(isOperator(infixExpression[i]))
				{
					postfixStack.push(infixExpression[i]);
				}
			}
			else if(infixExpression[i].equals(")"))
			{
				
				while(!postfixStack.peek().equals("("))
				{

					retStr[counter] = postfixStack.pop();
					counter++;
					if(postfixStack.isEmpty())
						break;
						
				}
				if(!postfixStack.isEmpty())
					postfixStack.pop();
					
				
			}
			else
			{
				if(higherPrecedence(infixExpression[i], postfixStack))
				{
					if(isOperator(infixExpression[i]))
					{
						postfixStack.push(infixExpression[i]);
					}
				}
				else if(samePrecedence(infixExpression[i], postfixStack) || (!higherPrecedence(infixExpression[i], postfixStack)))
				{
					if(isOperator(infixExpression[i]))
					{
						while(!(higherPrecedence(infixExpression[i], postfixStack)))
						{
							if(postfixStack.isEmpty())
								break;
							retStr[counter] = postfixStack.pop();
							counter++;
						}
						postfixStack.push(infixExpression[i]);
						
					
					}
				}
				
			}
			

		}
		while(!postfixStack.isEmpty())
		{
			retStr[counter] = postfixStack.pop();
			counter++;
			
		}
		
		if(!postfixStack.isEmpty())
			throw new IllegalArgumentException("Error expression not balanced");
			
		return Arrays.copyOf(retStr, counter);
	}
	
	public static String[] convertToPrefix(String [] infixExpression)
	{
		Stack<String> operatorStack = new Stack<>();
		Stack<String> operandStack = new Stack<>();
		String retStr[] = new String[infixExpression.length];
		int counter = 0;
		
		if(!isBalanced(infixExpression))
			throw new IllegalArgumentException("Error expression is not balanced");
		if(!hasCorrectOperations(infixExpression))
			throw new IllegalArgumentException("Error the expression is not correct, missing either operand or operator");
		
		for(int i = 0; i < infixExpression.length; i++)
		{
			if((Character.isAlphabetic(infixExpression[i].charAt(0)) || (Character.isDigit(infixExpression[i].charAt(0)))))
			{
				operandStack.push(infixExpression[i]);
			}
			else if(infixExpression[i].equals("("))
			{
				operatorStack.push(infixExpression[i]);
			}
			else if(infixExpression[i].equals(")"))
			{
				
				while(!operatorStack.peek().equals("("))
				{
					String op = operatorStack.pop();
					String rhs = operandStack.pop();
					String lhs = operandStack.pop();
					operandStack.push(op + lhs + rhs);	
				}
				operatorStack.pop();
			}
			else if(higherPrecedence(infixExpression[i], operatorStack))
			{
				if(isOperator(infixExpression[i]))
				{
					operatorStack.push(infixExpression[i]);
				}
					
				
			}
			else if ((samePrecedence(infixExpression[i], operatorStack) || (!higherPrecedence(infixExpression[i], operatorStack))))
			{
				if(isOperator(infixExpression[i]))
				{
					while((samePrecedence(infixExpression[i], operatorStack) || (!higherPrecedence(infixExpression[i], operatorStack))))
					{
					
						if(operatorStack.isEmpty() || operandStack.isEmpty())
							break;
						String op = operatorStack.pop();
						String rhs = operandStack.pop();
						String lhs = operandStack.pop();
						operandStack.push(op + lhs + rhs);
					
					}
					operatorStack.push(infixExpression[i]);
				}
			}
		}
		while(!operatorStack.isEmpty())
		{
			
			if(operatorStack.isEmpty() || operandStack.isEmpty())
				break;
			String op = operatorStack.pop();
			String rhs = operandStack.pop();
			String lhs = operandStack.pop();
			operandStack.push(op + lhs + rhs);
		}
		while(!operandStack.isEmpty())
		{
			
			retStr[counter] = operandStack.pop();
			counter++;
		}
		
		if(!operandStack.isEmpty())
			throw new IllegalArgumentException("Error expression not balanced");
		
	
		return Arrays.copyOf(retStr, counter);
	}
	/*
	 *  Name: higherPrecedence
	 *  Return type: boolean
	 *  Description: Checks if the string passed in has a higher mathematical
	 *  precedence that the value on the top of the stack. If it does it returns true;
	 *  else it returns false. 
	 */
	public static boolean higherPrecedence(String operator, Stack <String> s1)
	{
		if(s1.isEmpty())
		{
			return false;
		}
		if (s1.peek().equals(operator))
		{
			return false;
		}		
		else if(s1.peek().equals("("))
		{
			
			return true;
		}
		else if((operator.equals("*")) || (operator.equals("/")))
		{
			if((s1.peek().equals("*")) || s1.peek().equals("/"))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else if((operator.equals("+")) ||(operator.equals("-")))
		{
			if((s1.peek().equals("*")) || (s1.peek().equals("/")))
			{
				return false;
			}
				
		}
		else 
		{
			return false;
		}
		return false;
		
	}
	/*
	 * Name: samePrecedence
	 * Return type: boolean
	 * Description: Boolean method that checks whether the operator has the same precedence as the 
	 * item on the top of the stack. If it is the same precedence the method returns true; else it returns false.
	 */
	public static boolean samePrecedence(String operator, Stack<String> s1)
	{
		if(s1.isEmpty())
		{
			return false;
		}
		if(operator.equals(s1.peek()))
		{
			return true;
		}
		else if((operator.equals("*")) || (operator.equals("/")))
		{
			if((s1.peek().equals("*")) || (s1.peek().equals("/")))
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
		else if((operator.equals("+")) || (operator.equals("-")))
		{
			if((s1.peek().equals("+")) || s1.peek().equals("-"))
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
		
	/*
	 * Name: isOperator
	 * Return type: boolean
	 * Description: Verifies that the character being scanned is an operand, if it verifies as an operand it 
	 * returns true; else it returns false.
	 */
	public static boolean isOperator(String exp)
	{
		if((exp.equals("+")) ||(exp.equals("-")) || (exp.equals("*")) || (exp.equals("/")))
		{
			return true;
		}
		return false;
	}
	/*
	 * Name : isBalanced
	 * Return type: boolean
	 * Description: Verifies that expression passed into the method has balanced parenthesis
	 * If it does the method returns true; else it returns false.
	 */
	public static boolean isBalanced(String [] expression)
	{
		Stack <String> balancedExp = new Stack<>();
		
		for(int i = 0; i < expression.length; i++)
		{
			if(expression[i].equals("("))
			{
				balancedExp.push(expression[i]);
			}
			else if(expression[i].equals(")"))
			{
				if(((balancedExp.isEmpty()) ||(!balancedExp.pop().equals("("))))
				{
					return false;
				}
									
						
			}
		}
		if(!balancedExp.isEmpty())
			return false;
			
		
		return true;
	}
	/*
	 * Name: hasCorrectOperations
	 * Return type: boolean
	 * Description: Verifies that the expression does not contain
	 * extra operators or operands. If it has the correct number of
	 * operand and operators it returns true; else it returns false.
	 */
	public static boolean hasCorrectOperations(String []expression)
	{
		Stack <String> operand = new Stack<>();
		Stack <String> operator = new Stack<>();
		
		for(int i = 0; i < expression.length; i++)
		{
			if((Character.isAlphabetic(expression[i].charAt(0)) || (Character.isDigit(expression[i].charAt(0)))))
			{
				operand.push(expression[i]);
			}
			else if(isOperator(expression[i]))
			{
				operator.push(expression[i]);
			}
		}
		while(!operator.isEmpty())
		{
			if(operator.isEmpty())
				return false;
			String op = operator.pop();
			String oper1 = "";
			String oper2 = "";
			if(operand.isEmpty())
				return false;
			oper1 = operand.pop();
			if(operand.isEmpty())
				return false;
			oper2 = operand.pop();
			operand.push(op + oper1 + oper2);
		}
		operand.pop();
		if(!operand.isEmpty())
			return false;
		return true;
	}
	/*
	 * This case test postfix and prefix methods ability to 
	 * convert a correct infix expression with no parenthesis
	 */
	public static void testCase1()
	{
		System.out.println("Verifies methods can convert: A+B");
		String []expression = {"A", "+", "B"};
		String []postfixExp = convertToPostfix(expression);
		String []prefixExp = convertToPrefix(expression);
		System.out.print("Postfix: " );
		for(int i = 0; i < postfixExp.length; i++)
		{
			System.out.print(postfixExp[i]);
		}
		
		System.out.print("\n");
		System.out.print("Prefix: " );
		for(int i = 0; i < prefixExp.length; i++)
		{
			System.out.print(prefixExp[i]);
		}
		System.out.print("\n");
		
		
	}
	/*
	 * Verifies that prefix and postfix conversion method works properly 
	 * for expression with parenthesis.
	 */
	public static void testCase2()
	{
		System.out.println("Verifies methods can convert: (A*B*(C-D))");
		String []expression = {"(","A", "*", "B","*","(","C","-","D",")",")"};
		String []postfixExp = convertToPostfix(expression);
		String []prefixExp = convertToPrefix(expression);
		System.out.print("Postfix: " );
		for(int i = 0; i < postfixExp.length; i++)
		{
			System.out.print(postfixExp[i]);
		}
		System.out.print("\n");
		System.out.print("Prefix: " );
		for(int i = 0; i < prefixExp.length; i++)
		{
			System.out.print(prefixExp[i]);
		}
		System.out.print("\n");
	}
	/*
	 * Verifies methods can detect unbalanced expression
	 * The method will pass an unbalanced expression to convertToPostfix
	 * and the method will throw an exception.
	 */
	public static void testCase3()
	{
		System.out.println("Verifies convertToPostfix can detect the expression as unbalanced: (A*B*((C-D))");
		String []expression = {"(","A", "*", "B","*","(","(","C","-","D",")",")"};
		String []postfixExp = convertToPostfix(expression);
		
		for(int i = 0; i < postfixExp.length; i++)
		{
			System.out.print(postfixExp[i]);
		}
		
		System.out.print("\n");	
	}
	/*
	 * Verifies that expression is valid by checking there
	 * are the correct number of operands and operator. This method
	 * will send an expression with too many variable and covertToPostfix
	 * will throw an exception.
	 */
	public static void testCase4()
	{
		System.out.println("Verifies convertToPostfix will throw error for: A+BC");
		String []expression = {"A", "+", "B", "C"};
		String []postfixExp = convertToPostfix(expression);
		
		for(int i = 0; i < postfixExp.length; i++)
		{
			System.out.print(postfixExp[i]);
		}
		
		
		System.out.print("\n");
	}
	/*
	 * Verifies methods can detect unbalanced expression
	 * The method will pass an unbalanced expression to convertToPrefix
	 * and the method will throw an exception.
	 */
	public static void testCase5()
	{
		System.out.println("Verifies convertToPrefix can detect the expression as unbalanced: (A*B*((C-D))");
		String []expression = {"(","A", "*", "B","*","(","(","C","-","D",")",")"};
		String []prefixExp = convertToPrefix(expression);
		
		for(int i = 0; i < prefixExp.length; i++)
		{
			System.out.print(prefixExp[i]);
		}
		
		System.out.print("\n");	
	}
	/*
	 * Verifies that expression is valid by checking there
	 * are the correct number of operands and operator. This method
	 * will send an expression with too many variable and covertToPrefix
	 * will throw an exception.
	 */
	public static void testCase6()
	{
		System.out.println("Verifies convertToPrefix will throw error for: A+BC");
		String []expression = {"A", "+", "B", "C"};
		String []prefixExp = convertToPrefix(expression);
		
		for(int i = 0; i < prefixExp.length; i++)
		{
			System.out.print(prefixExp[i]);
		}
		
		
		System.out.print("\n");
	}
	
	
}
