//
// Name: Arias, Raymond
// Homework: Project 1
// Due: 05/05/15
// Course: cs-240-sp15
//
//	Description:
//				This program read int from a file passed in from command line.
//				The program put the ints into a linked then an array. Once the
//				the array has been made the program calculates the average and standard deviation.



import java.util.Scanner;
import java.io.*;

public class Stat {
	private SinglyLinkedList list;
	private int[] listArray;
	
	/*
	 * intitalizes an instance of Stat class with a new singlylinkedlist instance
	 * and the int array to null
	 */
	public Stat()
	{
		list = new SinglyLinkedList();
		listArray = null;
	}
	
	/*
	 * creates a linked from the file passed into method
	 */
	public void createLinkedList(Scanner fileInput)
	{
		list.readFromFile(fileInput);
		
	}
	/*
	 * creates array from linked list created
	 */
	public void createArray()
	{
		listArray = list.createArray();
		
	}
	/*
	 * output stats collected from array
	 */
	public void statOutput()
	{
		
		outputList();
		System.out.println("Count = " +list.getCounter());
		System.out.printf("Average = %.2f \n",averageCalc());
		System.out.printf("Standard Deviatation = %.2f \n",stanDevCalc());
	}
	/*
	 * calculates the average for the array
	 */
	public double averageCalc()
	{
		double sum = 0.0;
		for (int i = 0; i < listArray.length; i++)
		{
			sum += listArray[i];
		}
		
		return sum / list.getCounter();
	}
	/*
	 * calculates standard deviation for array
	 */
	public double stanDevCalc()
	{
		double mean = averageCalc();
		double [] tempArray = new double[listArray.length];
		
		for(int i = 0; i <listArray.length; i++)
		{
			tempArray[i] = listArray[i] - mean;
			tempArray[i] = tempArray[i] * tempArray[i];
			
		}
		
		double squaredDiffMean = 0;
		double counter = 0;
		for(int i = 0; i < tempArray.length; i++)
		{
			squaredDiffMean += tempArray[i];
			counter ++;
			
		}
		
		squaredDiffMean = squaredDiffMean / counter;
		
		return Math.sqrt(squaredDiffMean);
		
		
	}
	public void outputList()
	{
		int lineCounter = 0;
		System.out.println("List: ");
		for (int i = 0; i < listArray.length; i++)
		{
			lineCounter++;
			//System.out.printf(listArray[i] + "  ");
			System.out.format("%3d ", listArray[i]);
			if(lineCounter == 20)
			{
				System.out.print("\n");
				lineCounter = 0;
			}
			
		}
		System.out.print("\n");
	}
	
	public class SinglyLinkedList
	{
		protected Node head; //head of list
		private int counter;
		/*
		 * constructor that set head node to null
		 */
		public SinglyLinkedList()
		{
			head = null;
			counter = 0;
		}
		/*
		 * adds node to beginning of linked list
		 */
		public void addFirst (int data)
		{
			head = new Node(data, head);
			
		}
		/*
		 * adds node to end of list
		 */
		public void addLast(int data)
		{
			Node node = new Node(data, null);
			
			if(head == null)
			{
				head = node;
			}
			else
			{
				Node trav = head;
				while(trav.getNext() != null)
				{
					trav = trav.getNext();
				}
				trav.setNext(node);
			}
			
		}
		
		/*
		 * outputs the data held in linked list to console
		 */
		public void traverseList()
		{
			Node traverse = head;
			
			while(traverse != null)
			{
				System.out.println(traverse.getElement());
				traverse = traverse.getNext();
			}
		}
		/*
		 * reads data from file and makes a singly linked list out of data
		 */
		public void readFromFile(Scanner fileInput)
		{
			while(fileInput.hasNext())
			{
				this.addFirst(fileInput.nextInt());
				this.counter++;
			}
			
		}
		/*
		 * returns the counter variable
		 */
		public int getCounter()
		{
			return counter;
		}
		
		/*
		 * create integer array from data held in linked list 
		 */
		public int[] createArray()
		{
			int []tempArray = new int[counter];
			int i = 0;
			
			Node traverse = head;
			
			while (traverse != null)
			{
				tempArray[i] = traverse.getElement();
				traverse = traverse.getNext();
				i++;
			}
			return tempArray;
		}
		
		
		
	}
	public class Node
	{
		private int element;
		private Node next;
		/* 
		 * creates new node with element passed to it and next node
		 */
		public Node(int element, Node next)
		{
			this.element = element;
			this.next = next;
		}
		/*
		 * return the data stored at this node
		 */
		public int getElement()
		{
			return element;
		}
		/*
		 * returns reference to next node in list
		 */
		public Node getNext()
		{
			return next;
			
		}
		/*
		 * set data at this node to data passed in
		 */
		public void setElement(int element)
		{
			this.element = element;
		}
		/*
		 * sets next node to node passed in
		 */
		public void setNext(Node newNext)
		{
			this.next = newNext;
		}
		/*
		 * checks to see if the data passed in is equal
		 * to data held at this node
		 */
		public boolean equal(int data)
		{
			if(this.element == data)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
	}
	public static void main(String []args)
	{
		
		Stat stat1 = new Stat();
		
		try
		{
			Scanner fileInput = new Scanner(new File(args[0]));
			stat1.createLinkedList(fileInput);
			
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
			System.exit(0);
		}
		stat1.createArray();
		stat1.statOutput();
		
	}

}
