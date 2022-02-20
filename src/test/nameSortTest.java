package test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import main.nameSort;

public class nameSortTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	// check Person constructor and method
	@Test
	public void testPerson() {
		String lastName = "Ferrell";
		String firstName = "Will";
		String expected = "Ferrell, Will";
		nameSort.Person myPerson = new nameSort.Person(lastName, firstName);
		String result = myPerson.toString();
		
		assertEquals(expected, result, "Error constructing Person.");
	}
	
	// check name sorting comparator
	@Test
	public void SortbylastNameTest() {
		String expected = "BAKER, THEODORE, KENT, MADISON, SMITH, ANDREW, SMITH, FREDRICK";
		
		ArrayList<nameSort.Person> output = new ArrayList<nameSort.Person>();
		output.add(new nameSort.Person("BAKER", "THEODORE"));
		output.add(new nameSort.Person("SMITH", "ANDREW"));
		output.add(new nameSort.Person("KENT", "MADISON"));
		output.add(new nameSort.Person("SMITH", "FREDRICK"));
		
		Collections.sort(output, new nameSort.SortbylastName());
		
		//messy conversion of ArrayList<Person> to a basic string
		String result = String.join(",", Arrays.toString(output.toArray()).replace("[", "").replace("]", ""));	
		
		assertEquals(expected, result, "Error sorting by last name.");
	}
	
	// check if .txt files are read correctly
	@Test
	public void readFileTest() {
		// create a test file
		File testFile;
		try {
			testFile = folder.newFile("names_test.txt");
			// write file content
			FileWriter writer;
			try {
				writer = new FileWriter(testFile);
				
				writer.write("BAKER, THEODORE\n" + "SMITH, ANDREW\n" + "KENT, MADISON\n" + "SMITH, FREDRICK");
				
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<String> expected = new ArrayList<String>();
			expected.add("BAKER, THEODORE");
			expected.add("SMITH, ANDREW");
			expected.add("KENT, MADISON");
			expected.add("SMITH, FREDRICK");
			
			nameSort obj = new nameSort();
			ArrayList<String> result = obj.readFile(testFile);
			
			assertArrayEquals(expected.toArray(), result.toArray(), "Error reading file.");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// check if names are sorted alphabetically by last name
	@Test
	public void sortNamesTest() {
		String expected = "BAKER, THEODORE, KENT, MADISON, SMITH, ANDREW, SMITH, FREDRICK";
		
		ArrayList<String> input = new ArrayList<String>();
		input.add("BAKER, THEODORE");
		input.add("SMITH, ANDREW");
		input.add("KENT, MADISON");
		input.add("SMITH, FREDRICK");	
		
		nameSort obj = new nameSort();
		ArrayList<nameSort.Person> output = new ArrayList<nameSort.Person>(obj.sortNames(input));
		
		//messy conversion of ArrayList<Person> to a basic string
		String result = String.join(",", Arrays.toString(output.toArray()).replace("[", "").replace("]", ""));
		
		assertEquals(expected, result, "Error sorting names");
	}
	
	// check if names are wrote to file correctly
	@Test
	public void writeFileTest() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("BAKER, THEODORE");
		expected.add("KENT, MADISON");
		expected.add("SMITH, ANDREW");
		expected.add("SMITH, FREDRICK");
		
		//use the readFile function
		nameSort obj = new nameSort();
		//String testFile = "names_sorted_test.txt";
		//ArrayList<String> result = obj.readFile(testFile);
		
		//assertArrayEquals(expected.toArray(), result.toArray(), "Error writing file.");
	}
	

}
