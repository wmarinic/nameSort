package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.io.FileWriter; 
import java.io.IOException;


/* This program takes a text file input of a list of names 
 * and orders them alphabetically by last name;
 * outputting a new text file of the ordered names.
 */

// to compile this program:	javac nameSort.java

// to run this program:		java main.nameSort <.txt file>


public class nameSort {
	
	// A class to represent a person and their name
	public static class Person {
		// Attributes
		String lastName, firstName;
		
		// Constructor
		public Person(String lastName, String firstName) {
			this.lastName = lastName;
			this.firstName = firstName;
		}
		
		// Method
		public String toString() {
			return this.lastName + ", " + this.firstName;
		}
	}
	
	// Class implementing Comparator to sort by name
	public static class SortbylastName implements Comparator<Person> {
		
		@Override
		public int compare(nameSort.Person o1, nameSort.Person o2) {
			int num = o1.lastName.compareTo(o2.lastName);
			//check if the last names match
			if (num == 0) {
				//compare first names
				int num2 = o1.firstName.compareTo(o2.firstName);
				//if o1 is before o2
				if (num2 < 0) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return num;
			}
		}
	}
	/*
	 * Read .txt file that contains a list of names
	 * @param string containing a list of names
	 * @return list of names
	 * 
	 */
	public ArrayList<String> readFile(File txtFile) {
		
		//
		ArrayList<String> fileNames = new ArrayList<String>();
		try {
			Scanner myReader = new Scanner(txtFile);
			
			while (myReader.hasNextLine()) {
				fileNames.add(myReader.nextLine());
			}
			
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
		
		return fileNames;
	}

	/*
	 * Sort names alphabetically by last name
	 * @param ArrayList of names
	 * @return ArrayList of
	 */
	public ArrayList<Person> sortNames(ArrayList<String> namesList){
		
		//Create ArrayList of type Person
		ArrayList<Person> people = new ArrayList<Person>();
		
		//iterate through namesList
		for(int i = 0; i < namesList.size(); i++) {
			//split the name in first and last
			String[] fullName = namesList.get(i).split(", ");
			people.add(new Person(fullName[0], fullName[1]));			
		}
		
		//sort by lastName
		Collections.sort(people, new SortbylastName());
		
		return people;
	}
	
	/*
	 * Write sorted names to file
	 * @param sorted ArrayList of type String
	 * @return void
	 */
	
	public static void writeFile(ArrayList<Person> sortedNames, File fileName) throws IOException {
		
		FileWriter myWriter = new FileWriter(fileName);
		
		for (Person obj: sortedNames) {
			myWriter.write(obj.toString() + System.lineSeparator());
		}
		
		myWriter.close();
	}
	
	public static void main(String args[]){
		//declare vars
		ArrayList<String> namesList;
		ArrayList<Person> peopleList;
		
		//create object of nameSort
		nameSort obj = new nameSort();
		
		// readfile
		File txtFile = new File(args[0]);
		namesList = obj.readFile(txtFile);
		//System.out.println(namesList);
		
		// sort by last names
		peopleList = obj.sortNames(namesList);
		
		//Print nicely to console
		for (Person prsn: peopleList) {
			System.out.println(prsn.toString());
		}
		
		// write sorted names to file
		File outputFile = new File("names-sorted.txt");
		try{
			nameSort.writeFile(peopleList, outputFile);
			System.out.println("Finished: created names-sorted.txt");
		} catch (IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}		
	  }
}
