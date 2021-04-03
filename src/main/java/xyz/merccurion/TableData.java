package xyz.merccurion;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.RandomStringUtils;

public class TableData {
	GetSetInput get = new GetSetInput();
	GetSetInput set = new GetSetInput();
	
	BufferedWriter bw = null;

	private static LinkedHashMap<String, String> table = new LinkedHashMap<String, String>(); // key value storage
	private static ArrayList<String> concat = new ArrayList<String>(); // concatenated data storage (key+value)
	private static ArrayList<String> splitKey = new ArrayList<String>(); // key data storage
	private static ArrayList<String> splitValue = new ArrayList<String>(); // value data storage

	// generate key-value pairs
	public Map<String,String> keyValue(int row, int col) {
		// key value pairs
		for (int rowCount = 0; rowCount < row; rowCount++) {
			for (int colCount = 0; colCount < col; colCount++) {
				table.put(RandomStringUtils.randomAscii(3), RandomStringUtils.randomAscii(3));
			}			
		}
		return table;
	}

	// split key and value into different arrayLists
	public List<String> splitKey(Map<String,String> table) {
		// split table entries to keys and values in separate lists
		for (Map.Entry<String, String> entry : table.entrySet()) {
			splitKey.add(entry.getKey());
		}
		return splitKey;
	}
	public List<String> splitValue(Map<String,String> table) {
		// split table entries to keys and values in separate lists
		for (Map.Entry<String, String> entry : table.entrySet()) {
			splitValue.add(entry.getValue());
		}
		return splitValue;
	}

	// search searchString indicating location and either key or value
	public void searchArray(int row, int col) {
		String searchString = get.getSearchString();
		int keyOccurs = 0;
		int valueOccurs = 0;
		boolean inArray = false;
		int arrayCounter = 0;
		for (int rowCount = 0; rowCount < row; rowCount++) {
			for (int colCount = 0; colCount < col; colCount++) {
				if (splitKey.get(arrayCounter).contains(searchString)) {
					keyOccurs = 1;
					inArray = true;
					if (keyOccurs > 0) {
						System.out.printf("Found %s on key of (%d,%d)", searchString, rowCount, colCount);
						keyOccurs = 0;
						System.out.println(" with " 
							+ countKeyOccurs(searchString, arrayCounter, 0) 
							+ " occurence/s.");
					}
				}
				if (splitValue.get(arrayCounter).contains(searchString)) {
					valueOccurs = 1;
					inArray = true ;
					if (valueOccurs > 0) {
						System.out.printf("Found %s on value of (%d,%d)", searchString, rowCount, colCount);
						valueOccurs = 0;
						System.out.println(" with " 
							+ countValueOccurs(searchString, arrayCounter, 0) 
							+ " occurence/s.");
					}
				}
				arrayCounter++;
			}
		}	
		if (inArray == false) System.out.println("Search String not found.");
	}

	// count Key and Value occurences
	public int countKeyOccurs(String searchString,int arrayCounter,int cellOccurs) {
		int lastIndex = 0;	
		while (lastIndex != -1) {
			lastIndex = splitKey.get(arrayCounter).indexOf(searchString, lastIndex);
			if (lastIndex != -1) {
				cellOccurs++;
				lastIndex += searchString.length();
			}
		}
		return cellOccurs;
	}

	public int countValueOccurs(String searchString,int arrayCounter,int cellOccurs) {
		int lastIndex = 0;
		while (lastIndex != -1) {
			lastIndex = splitValue.get(arrayCounter).indexOf(searchString, lastIndex);
			if (lastIndex != -1) {
				cellOccurs++;
				lastIndex += searchString.length();
			}
		}
		return cellOccurs;
	}
	
	// get key of edit location
	public String getKeyFromEditLocation(File file, int row, int col, int rowEdit, int colEdit) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		// find cell to replace
		for (int rowCount = 0; rowCount < row; rowCount++) {
			for (int colCount = 0; colCount < col; colCount++) {
				if (rowCount == rowEdit && colCount == colEdit) {
					set.setEditKey(scan.nextLine().substring(0,3)); // get key from index
					break;
				} else scan.nextLine();
	  		}
	  		if (rowCount == get.getRowEdit()) break;			
		}
		scan.close();
		return get.getEditKey();
	}

	// update key
	public List<String> editKey(int row, int col, int rowEdit, int colEdit, List<String> splitKey, String editKeyString)  {
		int counter = 0;
		for (int rowCount = 0; rowCount < row; rowCount++) {
			for (int colCount = 0; colCount < col; colCount++) {
				if (rowCount == rowEdit && colCount == colEdit) {
					splitKey.set(counter, editKeyString); // replace key
					break;
				} 
				counter++;
	  		}
	  		if (rowCount == get.getRowEdit()) break;		
		}		
		return splitKey; // returns updated keys in a list
	}

	// update value
	public String editValue(String editKey, String editValueString, Map<String,String> table) {
		table.put(editKey, editValueString); // update value using key
		return table.get(editKey);
	}

	// return table
	public LinkedHashMap<String, String> getTable() {
		return table;
	}

	
	// write table to file
	public void writeToFile(File file) {
		// new file object 
		try {
			// create new BufferedWriter for the output file
			bw = new BufferedWriter(new FileWriter(file));
			
			// iterate map entries
			for (Map.Entry<String, String> entry : table.entrySet()) {

				// put key and value separated by a colon
				bw.write(entry.getKey() + " : " + entry.getValue());

				// new line
				bw.newLine();
			}
			bw.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try{
				// close writer
				bw.close();
			}
			catch (Exception e) {

			}
		}
	}

	// concatenate key+value for sorting
	public List<String> concatTable(Map<String,String> table) {
		for (Map.Entry<String, String> entry : table.entrySet()) {
			concat.add(entry.getKey() + entry.getValue());
		}
		return concat;
	}	

	// sort concatenated data
	public List<String> sortTable(String sortOrder, List<String> concat) {
		
		// sort arrayList
		switch (sortOrder) {
			case "asc":
				Collections.sort(concat);
				break;
			case "desc":
				Collections.sort(concat, Collections.reverseOrder());
				break;
			default:
				System.out.println("Not a valid option.");
				System.exit(0);
				break;
		}
		return concat;
	}

	// write sorted data from concatenated table to hashmap
	public Map<String,String> writeToTable(List<String> concat, Map<String,String> table) {	
		// clear and reinput to table
		resetTable();
		for (String str : concat) {
			table.put(str.substring(0,3), str.substring(3,6));
		}
		return table;
	}

	// rewrite table with updated key
	public void writeToTableNewKey() {
		int count = 0;
		table.clear();
		for (String str : splitValue) {
			table.put(splitKey.get(count), str);
			count++;
		}
	}

	// clear table
	public Map<String,String> resetTable() {
		table.clear();
		return table;
	}

	// print table
	public void printTable(File file) throws FileNotFoundException {	
		Scanner scan = new Scanner(file);
		int counter = 0;
		while(scan.hasNextLine()) {			
			if (counter == get.getColInput()) {
				System.out.println();
				counter = 0;
			} else {
				System.out.print("|" + scan.nextLine() + "|" + "    ");
				counter++;
			}
		} 	
		scan.close();
	}

}