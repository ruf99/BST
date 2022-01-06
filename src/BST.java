import java.io.*;
import java.util.*;   


///////////////////////////////////////////////////////////////
class Student {
	
	public int iData;
	public String studentName;
	public double gradeData;
	public Student leftChild;
	public Student rightChild;

	public Student (int iData, String sData, double dData) {
		this.iData = iData;
		this.studentName = sData;
		this.gradeData = dData;
		leftChild = null;
		rightChild = null;
	}
	
	public void displayNode() 
		{
		System.out.print("{");
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(studentName);
		System.out.print(", ");
		System.out.print(gradeData);
		System.out.print("}");
		}
}

///////////////////////////////////////////////////////////////
class trees {
	private static Student root;
	
	trees (int iData, String sData, double dData) {
		root = new Student(iData,sData,dData);
	}
	
	trees() {
		root = null;
	}
	
	public static void createArr() throws NumberFormatException, IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		
		int num = 1;
		
		while (num < 11) {			//For Modifying BST size, change the value within the while loop for upper-bound
			System.out.println();
			System.out.print("Enter Student ID (INT)" + num + ": ");
			int Stu_ID = Integer.parseInt(r.readLine());
			System.out.print("Enter Student's grade (DOUBLE) " + num + ": ");
			Double Stu_Grade = Double.parseDouble(r.readLine());
			System.out.print("Enter student name (STRING)" + num + ": ");
			String Stu_Name = r.readLine();
			System.out.println();
			num++;
			insertElem(Stu_ID, Stu_Name,Stu_Grade);
		}

	}
	
	public static void insertElem(int stu_ID, String stu_Name,Double stu_Grade) {
		root = insert(root,stu_ID, stu_Name, stu_Grade);
	}
	
	public static Student insert(Student root, int stu_ID, String stu_Name,Double stu_Grade) {

		
		if (root == null) {
			root = new Student(stu_ID, stu_Name, stu_Grade);
			return root;
		}
		
		if (stu_ID < root.iData) {
			root.leftChild = insert(root.leftChild, stu_ID, stu_Name, stu_Grade);
		}
		
		if (stu_ID > root.iData) {
			root.rightChild = insert(root.rightChild, stu_ID, stu_Name, stu_Grade);
		}
		return root;
	}
	
	public static void inOrder() {
		inOrderDisplay(root);
	}
	
	public static void inOrderDisplay(Student root) {
		
		if (root != null) {
			inOrderDisplay(root.leftChild);
			System.out.println("Student ID: " + root.iData + "; Student Name: " + root.studentName + "; Student Grade: " + root.gradeData + ". ");
			System.out.println();
			inOrderDisplay(root.rightChild);
		}

	}
		

	
	public static void insertStudent() throws IOException {
		BufferedReader rr = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println();
		System.out.print("Enter Student ID (INT): ");
		int iD = Integer.parseInt(rr.readLine());
		System.out.print("Enter Student's grade (DOUBLE): ");
		Double Grade = Double.parseDouble(rr.readLine());
		System.out.print("Enter student name (STRING): ");
		String Name = rr.readLine();
		System.out.println();
		insertElem(iD, Name,Grade);
		
	}
		
	

	public static void deleteStudent() {
		//We need access to the root and also need this to be recursive since we need to look in the BST for the element user wants to delete
		@SuppressWarnings("resource")
		Scanner rrr = new Scanner(System.in);
		System.out.println("Please enter the Student ID (int) of the student you want deleted from BST: ");
		int i = rrr.nextInt();
		System.out.println("Please enter the Student Name (string) of the student you want deleted from BST: ");
		String s = rrr.next();
		System.out.println("Please enter the Student Grade (double) of the student you want deleted from BST: ");
		double d = rrr.nextDouble();
		root = delete(root, i, s, d);
		
	}
	
	static Student delete(Student r, int i, String s, double d) {
		Student parent = null;
		Student current = root;
		
		while (current.iData != i) {			//search for node
			parent = current;
			if (i < current.iData) {
				current = current.leftChild;	//go to left of tree root if key is smaller than root
			}
			else {
				current = current.rightChild;	//go to right of tree root if key is bigger than root
            }
			
			if (current == null) {		//base case
				return root;
			}
		}

		/*
		 * There are three cases in deletion, for leaf, for one node child, and for 2 node children. Special Case: deleting the root itself.
		 */
		
		if (current.leftChild == null) {
			if (current.rightChild == null) {
				
				//LEAF, simplest to delete, gotta relink the pointers or can get errors
				
				if (current != root) {
					if (parent.leftChild == current) {
						parent.leftChild = null;
					}
					
					else {
						parent.rightChild = null;
					}
				}
				
				else {
					root = null;
				}

			}
		}//leaf end
		
		// if no right child, replace with left subtree
		
		else if(current.rightChild==null) {
			if(current == root) {
				root = current.leftChild;
			}
			
			else if (parent.leftChild == current) {
				parent.leftChild = current.leftChild;
				
			}
			
			else {
				parent.rightChild = current.leftChild;
			}
		}
		
		// if no left child, replace with right subtree
		
		else if(current.leftChild==null) {
			if(current == root) {
				root = current.rightChild;
			}
			
			else if (parent.leftChild == current) {
				parent.leftChild = current.rightChild;
			}
			
			else {
	            parent.rightChild = current.rightChild;
			}
		}
	         
		
		//NOW FOR THAT NODE WITH TWO CHILD NODES, replace with inOrder SUCCESSOR (smallest elem on right subtree). 
		
		else {
			// get successor of node to delete (current)
			Student successor = getSuccessor(current);
			// connect parent of current to successor instead
			if(current == root) {
	            root = successor;
			}
			else if(parent.leftChild == current) {
	            parent.leftChild = successor;
			}
	         else {
	            parent.rightChild = successor;
	         }
				
			// connect successor to current's left child
	         successor.leftChild = current.leftChild;
				

		}	//end else for two child nodes
		
		return root;
	}

	private static Student getSuccessor(Student delThisNode) {
		 // returns node with next-highest value after delNode
		 // goes to right child, then right child's left descendents
		Student successorParent = delThisNode;
		Student successor = delThisNode;
		
		Student current = delThisNode.rightChild;
		
		while (current != null) {
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		
		if (successor != delThisNode.rightChild) {
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = delThisNode.rightChild;
		}
				
		return successor;
	}

	public static void displayStudent() {
		@SuppressWarnings("resource")
		Scanner sss = new Scanner(System.in);
		System.out.println("Please enter the Student ID you want to access (INT): ");
		int thisIsID = sss.nextInt();
		
		displayS(root, thisIsID).displayNode();;
	}
	
	public static Student displayS(Student root2, int key) {
		
		Student current = root2;
		while (current.iData != key) {
			if (key < current.iData) {
				current = current.leftChild;
			}
			
			else {
				current = current.rightChild;
			}
			
			if (current == null) {
				return null;
			}
		}
		
		return current;
	}
	
	
	static ArrayList<Integer> inOrderSort(Student localRoot, ArrayList<Integer> a) {
		
		if (localRoot != null) {
			inOrderSort(localRoot.leftChild,a);  
			a.add(localRoot.iData);
			inOrderSort(localRoot.rightChild,a);
		}
		
		return a;
    }
	
	static void displayArrList(ArrayList<Integer> arr) {
		
		Collections.sort(arr);
		
		for (int dis : arr) {
			System.out.print(dis + " ");
		}
		
	}

	public static void displayID() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		displayArrList(inOrderSort(root, a));
	}

	public static void displayGrades() {
		
		ArrayList<Double> b = new ArrayList<Double>();
		displayArrList2(inOrderSort2(root, b));
	}
	
	private static void displayArrList2(ArrayList<Double> arr2) {
		Collections.sort(arr2);
		for(double gr: arr2)  
		{  
		System.out.println(gr);  
		}  

		
	}

	static ArrayList<Double> inOrderSort2(Student localRoot2, ArrayList<Double> b) {
		if (localRoot2 != null) {
			inOrderSort2(localRoot2.leftChild,b);  
			b.add(localRoot2.gradeData);
			inOrderSort2(localRoot2.rightChild,b);
		}
		
		return b;
    }
	

	public static void displayKeyGrades() {
		displayKG (root);
	}

	private static void displayKG(Student root3) {
		ArrayList<Double> d = new ArrayList<Double>();
		lastMethod(inOrderSort2(root, d));
	}

	private static void lastMethod(ArrayList<Double> arr4) {
		Collections.sort(arr4);
		@SuppressWarnings("resource")
		Scanner lastOne = new Scanner(System.in);
		System.out.println("Please enter the Student Grade (key-value): ");
		double thisIsKey = lastOne.nextDouble();
		
		for (int k = 0; k < arr4.size(); k++) {
			if (arr4.get(k) > thisIsKey) {
				System.out.print(arr4.get(k) + " ");
			}
		}

	}
}

public class BST {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		@SuppressWarnings("resource")
		Scanner welcome = new Scanner(System.in);
		System.out.println("Hello and welome. Would you like to see the user menu? (y for yes n for no): ");
		String input = welcome.next();
		if (input.equals("y")){
			userMenu();
		}
		
		if (input.equals("n")) {
			System.out.println("You are exiting now. Thanks.");
			System.exit(0);
		}
	
		
	}

	
	private static void userMenu() throws NumberFormatException, IOException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int user_input;
		do {
			System.out.println("\n\n          Binary Search Trees Menu");
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.println("1 - Create a BST (ordered by students’ IDs)");
			System.out.println("2 - Insert a new student");
			System.out.println("3 - Delete a student");
			System.out.println("4 - Search for student by ID and display their grade and name");
			System.out.println("5 - Display students’ IDs sorted");
			System.out.println("6 - Display students’ Grades sorted");
			System.out.println("7 - Display ALL student grades above a certain value entered by the user");
			System.out.println("8 - Exit");
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.print("\nSelect a Menu Option: ");
			
			user_input = sc.nextInt();
			
			switch (user_input) {
		        case 1:  trees.createArr();
		        		 trees.inOrder();
		        		 System.out.println("Successfully created the BST!!!");
		                 break;
		        case 2:  trees.insertStudent();
		        	     trees.inOrder();						//Utilize this for when you need to delete a student, please enter exact values.
		        		 System.out.println("Successfully added a student!!!");
		                 break;
		                 
		        case 3:  trees.deleteStudent();
		        		 System.out.println();
		        		 System.out.println("Successfully deleted a student!!!");
		        		 trees.inOrder();		
                		 break;
                		 
		        case 4:  trees.displayStudent();
		        		 System.out.println();
		        		 System.out.println("Successfully displayed student information!!!");
                		 break;
                		 
		        case 5:  trees.displayID();
		        		 System.out.println();
		        		 System.out.println("Successfully displayed sorted student ID's!!!");
                		 break;
                		 
		        case 6:  trees.displayGrades();
		        		 System.out.println();
		        		 System.out.println("Successfully displayed sorted student grades!!!");
                 		 break;
                 		 
		        case 7:  trees.displayKeyGrades();
		        	     System.out.println();
		        		 System.out.println("Successfully displayed (key-based) student grades!!!");
                		 break;
                		 
		        case 8:  System.out.println("You are now exiting. Thank you!");
		        		 System.exit(0);
		        		 break;
		        		 
		        default : 
		        	System.out.println("Sorry, that is not a valid Menu Option!");
		        	break;
		       }
		}while(user_input != 8); 
	}
}

