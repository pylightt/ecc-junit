package xyz.merccurion;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.RandomStringUtils;

public class Menu {
	GetSetInput get = new GetSetInput();
	GetSetInput set = new GetSetInput();
	
	String outputFilePath = get.getFilePath();
	private File file = new File(outputFilePath);

	int row = get.getRowInput();
	int col = get.getColInput();
	String menu = get.getMenu();
	String searchString = get.getSearchString();

	TableData newTable = new TableData();
	LinkedHashMap<String, String> table = newTable.getTable();
	
	public void menuSearch() throws FileNotFoundException {
		newTable.splitKey(table);
		newTable.splitValue(table);
		newTable.searchArray(row, col);
	}

	public void menuEdit(int rowEdit, int colEdit) throws FileNotFoundException {

		newTable.getKeyFromEditLocation(file, row, col, rowEdit, colEdit);
	
		switch (get.getEditOption()) {
			case "key":
				List<String> splitKey = newTable.splitKey(table);
				newTable.splitValue(table);
				System.out.print("Set updated key: ");
				set.setEditKeyString();
				String editKeyString = get.getEditKeyString();
				newTable.editKey(row, col, rowEdit, colEdit, splitKey, editKeyString);
				newTable.writeToTableNewKey();
				break;
			case "value":
				System.out.print("Set updated value: ");
				set.setEditValueString();
				String editValueString = get.getEditValueString();
				String editKey = get.getEditKey();
				newTable.editValue(editKey,editValueString,table);
				break;
			default:
				menuDefault();
				menuExit();
				break;
		}
		newTable.writeToFile(file);
		newTable.printTable(file);
		
	}

	public void menuAddRow() throws FileNotFoundException {
		// generate and append additional row to hashmap
		for (int rowCount = 0; rowCount < 1; rowCount++) {
			for (int colCount = 0; colCount < col; colCount++) {
				table.put(RandomStringUtils.randomAscii(3), RandomStringUtils.randomAscii(3));
			}
		}
		// rewrite table to file
		newTable.writeToFile(file);
		newTable.printTable(file);
	}

	public void menuSortRow() throws FileNotFoundException {
		// concatenate and sort
		List<String> concat = newTable.concatTable(table);
		String sortOrder = get.getSortOrder();
		newTable.sortTable(sortOrder,concat);
		newTable.writeToTable(concat,table); // rewrite sorted data to table

		// reprint table
		newTable.writeToFile(file);
		newTable.printTable(file);
	}

	public void menuExit() {
		System.exit(0);
	}

	public void menuDefault(){
		System.out.println("Not a valid option.");
		menu = "reset";
	}
}