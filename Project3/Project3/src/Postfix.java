
//Name: Arias, Raymond
//Homework: 3
//Due: 06/11/15
//Course: cs-240-02-sp15
//
//Description:
//				The program converts infix expression into a postfix expression
//				and calculates the expression.
import java.util.Stack;
public class Postfix {
	public static void main(String []args)
	{
		String [] exp = {"(","3", "+", "2",")", "*", "value1", "/", "value2"};
		String [] postfix = InfixExpression.convertToPostfix(exp);
		String [] variables = {"value1", "value2", "value3"};
		String [] values = {"1.09", "2", "3.5"};
		System.out.print("Expression: ");
		for(int i = 0; i < exp.length; i++)
			System.out.print(exp[i]);
		
			
		System.out.println("\nPostfix evaluation: " +evalPostfix(postfix, variables, values));
		
		String exp1[] = {"A" ,"+", "(", "B","/", "C", ")","*","(","D","/","E",")"};
		
		String []postfixEXP = InfixExpression.convertToPostfix(exp1);
		String [] prefix = InfixExpression.convertToPrefix(exp1);
		for(int i = 0; i < postfixEXP.length; i++)
			System.out.print(postfixEXP[i]);
		System.out.print("\n");
		for(int i = 0; i < prefix.length; i++)
			System.out.print(prefix[i]);
		
		}
		
			
	
	/*
	 * Name: evalPostFix
	 * Description: Accepts a postfix expressions, calculates the expression, 
	 * and returns result of the the calculation.
	 */
	public static Double evalPostfix(String [] postfix, String[] variables, String []values)
	{
		Stack <Double> nums = new Stack<>();
		Double result = 0.0;
		for(int i = 0; i < postfix.length; i++)
		{
			
			Double val = getValue(postfix[i], variables, values);
			if(postfix[i].equals("+"))
			{
				Double rhs = nums.pop();
				Double lhs = nums.pop();
				nums.push(lhs + rhs);
			}
			else if(postfix[i].equals("-"))
			{
				Double rhs = nums.pop();
				Double lhs = nums.pop();
				nums.push(lhs - rhs);
			}
			else if (postfix[i].equals("*"))
			{
				Double rhs = nums.pop();
				Double lhs = nums.pop();
				nums.push(lhs * rhs);
			}
			else if (postfix[i].equals("/"))
			{
				Double rhs = nums.pop();
				Double lhs = nums.pop();
				nums.push(lhs/rhs);
			}
			
			else if(Character.isDigit(postfix[i].charAt(0)))
			{
				nums.push(Double.parseDouble(postfix[i]));
			}
			else
			{
				if(val == null)
					throw new IllegalArgumentException("Error variable not found");
				else
				{
					nums.push(val);
				}
					
			}
				
		}
		result = nums.pop();
		if(!nums.isEmpty())
			throw new IllegalArgumentException("Error expression is invalid");
		return result;
	}
	/*
	 * Name: getValue
	 * Description: Searches through the variable array to  verify that the string passed in
	 * is also in the variable array. If the variable name is found the value held at the index in
	 * values array is returned; else the method returns null. 
	 */
	public static Double getValue(String varName, String [] variables, String [] values)
	{	
		for (int i = 0; i < variables.length; i++)
		{
			if(variables[i].equals(varName))
				return Double.parseDouble(values[i]);
		}
		
		return null;
	}


}
