//
//	Name: Arias, Raymond
// 	Project: 2
//	Due: 05/21/15
//	Course: cs-240-02-sp15
//
//	Description: 
//				This program uses a linked list to implement a program that 
//				that simulates sets and method to verify elements of set, like whether
//				two sets are equal.

public class Set <T>{
	private Node <T> head; //Head dummy node
	private int size;
	public Set()
	{
		
		head = new Node<T>(null, null);
		size = 1; //I set the size to one to account for dummy node
	}
	
	//Searches through linked list to verify if element in set exists.
	//If the element exists the method return true, else it returns false
	public boolean contain(T data)
	{
		if(searchElement(data))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//Searches through linked list to verify if element exists in the set.
	//If the element exists in the set then the element is removed, the method returns true,
	// and the count is decremented by one; else the method returns false.
	public boolean remove(T data)
	{
		if(searchElement(data))
		{
			deleteNode(data);
			size--;
			return true;
		}
		else
			return false;
	}
	//Searches through linked list to verify if element exists in the set.
	//If the element does not exists in the set then the element is added, method returns true,
	//and the count is incremented by one; else the method returns false.
	public boolean addElement (T data)
	{
		if(!searchElement(data))
		{
			addNode(data);
			size++;
			return true;
		}
		else
		{
			return false;
		}
	}
	//returns the size of the set
	public int getSize()
	{
		return size;
	}
	//This function verifies that the calling Set if a subset of the set
	//pass into the method. If the calling set is a subset then the method returns
	//true; else the method returns false.
	public boolean subsetOf(Set<T> superSet)
	{
		if(this.size > superSet.getSize())
		{
			return false;
		}
		T [] array = this.getArray();
		//The set is an empty set, therefore it is a sub set of
		//any set passed into this method.
		if(array == null)
		{
			return true;
		}
		for(int i = 0; i < array.length; i++)
		{
			
			if(!superSet.contain(array[i]))
			{
				return false;
			}
		}
		return true;
		
		
	}
	//Verifies the calling set and the set passed into the method are equal to each other
	//If the two sets are equal, the method returns true; else the method returns false;
	public boolean isEqual(Set<T> setCompare)
	{
		T[] secondSet = setCompare.getArray();
		//Checks if both sets are empty sets;
		if(secondSet == null)
		{
			T [] firstSet = this.getArray();
			if(firstSet == null)
			{
				return true;
			}
			
		}
		
		//If the two sets do not have the same size they cannot be equal
		if(this.getSize() != setCompare.getSize())
		{
			return false;
		}
		
		for(int i = 0; i < secondSet.length; i++)
		{	
			if(!this.contain(secondSet[i]))
			{
				return false;
			}
		}
		return true;
		
	}
	//Method returns a union of the calling set and set passed in.
	//Each set is assigned to an array. After, each element is passed to temporary
	//set. Then the temporary set is returned;
	public Set<T> union(Set<T> passSet)
	{
		Set <T> tempSet = new Set<>();
		T[] set1 = this.getArray();
		T[] set2 = passSet.getArray();
		if(set1 != null)
		{
			for(int i = 0; i < set1.length; i++)
			{
				tempSet.addElement(set1[i]);
			}
		}
		if(set2 != null)
		{
			for(int i = 0; i < set2.length; i++)
			{
				tempSet.addElement(set2[i]);
			}
		}
		return tempSet;
	}
	//Method returns an intersection set of the calling set and set passed in.
	//Each set is transfered into an array, then each element in the array is passed to 
	//other set's contain method. If that method returns true then that element is passed to 
	//a temporary set. That temporary set is returned once both sets are iterated through.
	public Set<T> intersection(Set <T> passSet)
	{
		Set <T> tempSet = new Set<>();
		T[] set1 = this.getArray();
		T[] set2 = passSet.getArray();
		if(set1 != null)
		{
			for(int i = 0; i < set1.length; i++)
			{
				if(passSet.contain(set1[i]))
				{
					tempSet.addElement(set1[i]);
				}
			}
		}
		if(set2 != null)
		{
			for(int i = 0; i < set2.length; i++)
			{
				if(this.contain(set2[i]))
				{	
					tempSet.addElement(set2[i]);
				}
			}
		}
		return tempSet;
	}
	//Return complement set of calling set compared to the passed set.
	public Set <T> complement(Set <T> passSet)
	{
		Set <T> tempSet = new Set<>();
		T[] set1 = this.getArray();
		
		if(set1 !=  null)
		{
			for(int i = 0; i < set1.length; i++)
			{
				if(!passSet.contain(set1[i]))
				{
					tempSet.addElement(set1[i]);
				}
			}
		}
		return tempSet;
	}
	//To string method to get display the elements of a set.
	public String toString()
	{
		String str = "{" +this.getList()+"}";
		return str;
		
	}
	//Add a new node to the end of list
	public void addNode(T data)
	{
		if(head.getNext() == null)
		{
			Node<T> newNode = new Node<>(data, null);
			head.setNext(newNode);
		}
		else
		{
			Node <T> traverse = head.getNext();
					
			while(traverse.getNext() != null)
			{
						
				traverse = traverse.getNext();
			}
					
					
			Node <T> newNode = new Node<T>(data,null);
			traverse.setNext(newNode);
					
		}
				
	}
	//Searches through linked list for data passed into the method. 
	//If the data is found the method returns true; else the method returns false
	public boolean searchElement(T data)
	{
		Node<T> temp = head;
		if(temp == null)
		{
			return false;
		}
		//This is used to check null in the set, because
		//.equals cannot check null.
		while(temp != null)
		{
			if(data == null && temp.getData() == null)
			{
				return true;
			}
				temp = temp.getNext();
					
		}
		temp = head;
		temp = temp.getNext();
		while(temp != null)
		{	
			if(temp.getData().equals(data))
			{
				return true;
			}
			temp = temp.getNext();
					
		}
		return false;
	}
	//Searches the linked list for data passed into the method
	//and the deletes the element if the data is found.
	public void deleteNode(T data)
	{
		Node<T> temp = head.getNext();
		Node<T> follow = head;
		
		//check if list is empty
		if (temp == null)
		{
			
			throw new IllegalArgumentException("Error list is empty");
			
		}
		//check if data is stored at first node of list
		if(temp.getData().equals(data))
		{
			follow.setNext(temp.getNext());
			head = follow;
			temp.setNext(null);
		}
		else
		{
			
			while(temp != null)
			{
				if(temp.getData().equals(data))
				{
					follow.setNext(temp.getNext());
					
					temp.setNext(null);
					
					break;
				}
				follow = temp;
				temp = temp.getNext();
			}
		}
	}
	//Makes an array out of the set/linked list and returns the array to the caller.
	public T[] getArray()
	{
		T[] tempArray = (T[]) new Object[this.size];
		int i = 0;
		
		Node <T> temp = head;
		if(temp.getNext() == null)
		{
			return null;
		}
		while(temp != null)
		{
			tempArray[i] = temp.getData();
			temp = temp.getNext();
			i++;
		}
		return tempArray;
		
	}
	//Returns a string with the elements of the linked list/set.
	public String getList()
	{
		Node <T> temp = head;
		String listContent ="";
		while(temp != null)
		{
			if(temp.getData() == null)
			{
				temp = temp.getNext();
			}
			else
			{
				if(temp.getNext() != null)
				{
					listContent += temp.getData() + ",";
				}
				else
				{
					listContent += temp.getData();
				}
				temp = temp.getNext();
			}
		}
		return listContent;
	}
	
	//Node Class. To hold each node in linked list
	private class Node <T>
	{
		private Node <T> next; //next reference
		private T data;     //data for each node
		
		public Node(T data, Node<T> next)
		{
			this.data = data;
			this.next =  next;
		}
		//Set the reference to next node.
		public void setNext(Node<T> next)
		{
			this.next = next;
		}
		//Sets data held at each node.
		public void setData(T data)
		{
			this.data = data;
		}
		//Returns the reference to the next node.
		public Node<T> getNext()
		{
			return next;
		}
		//Returns data held at node.
		public T getData()
		{
			return data;
		}
		
	}

	public static void main(String [] args)
	{
		case1();
		case2();
		case3();
		case4();
		case5();
		
	}
	//Test case 1 A and B are equal but distinct set
	public static void case1()
	{
		Set<Integer> A = new Set<>();
		Set<Integer> B = new Set<>();
		A.addElement(1);
		A.addElement(2);
		A.addElement(3);
		B.addElement(2);
		B.addElement(1);
		B.addElement(3);
		System.out.println("A=" + A.toString());
		System.out.println("B=" + B.toString());
		Set<Integer> C = A.union(B);
		System.out.println("Union of A and B =" +C.toString());
		
		if(A.isEqual(B))
		{
			System.out.println("Sets A and B are equal");
		}
	}
	//Test Case 2: Two sets with different sizes but one is subset of the other.
	public static void case2()
	{
		Set<Integer> C = new Set<>();
		Set<Integer> D = new Set<>();
		C.addElement(1);
		D.addElement(1);
		D.addElement(2);
		System.out.println("C=" + C.toString());
		System.out.println("D=" + D.toString());
		if(C.subsetOf(D))
		{
			System.out.println("C is subset of D");
		}
	}
	//Test Case 3: Two sets with different sizes but some common elements.
	public static void case3()
	{
		Set<Integer> E = new Set<>();
		Set<Integer> F = new Set<>();
		E.addElement(1);
		E.addElement(2);
		E.addElement(3);
		F.addElement(2);
		F.addElement(3);
		F.addElement(4);
		F.addElement(5);
		
		Set<Integer> intersectionSet = E.intersection(F);
		System.out.println("E=" + E.toString());
		System.out.println("F=" + F.toString());
		System.out.println("Intersection of sets E and F=" + intersectionSet.toString());
		Set<Integer> complementSet = F.complement(E);
		System.out.println("Complement of set F and E=" + complementSet.toString());
	}
	//Test case 4: Non-empty set with no common elements.
	public static void case4()
	{
		Set<Integer> G = new Set<>();
		Set<Integer> H = new Set<>();
		
		G.addElement(1);
		H.addElement(2);
		H.addElement(3);
		
		System.out.println("G=" +G.toString());
		System.out.println("H=" +H.toString());
		if(!G.subsetOf(H))
		{
			System.out.println("G is not a subset of H");
		}
		if(!H.isEqual(G))
		{
			System.out.println("H is not equal to G");
		}
	}
	//Test Case 5: One empty set and one non-empty set.
	public static void case5()
	{
		Set<Integer> I = new Set<>();
		Set<Integer> J = new Set<>();
		J.addElement(1);
		J.addElement(2);
		J.addElement(3);
		J.addElement(4);
		System.out.println("I=" +I.toString());
		System.out.println("J=" +J.toString());
		
		if(I.subsetOf(J))
		{
			System.out.println("I is subset J");
		}
	}
}
