package xyz.merccurion;

import java.util.Scanner;
import javax.swing.*;

public class GetSetInput {
	Scanner scan = new Scanner(System.in);
	private static int rowInput;
	private static int colInput;
	private static String outputFilePath;
	private static String menu;
	private static String searchString;
	private static int rowEdit;
	private static int colEdit;
	private static String editOption;
	private static String editValue;
	private static String editKeyString;
	private static String editKey;
	private static String editValueString;
	private static String sortOrder;

	// ========= INPUT DIMENSIONS ========
	// row input
	public void setRowInput() {
		rowInput = Integer.parseInt(scan.nextLine());
	}
	public int getRowInput() {
		return rowInput;
	}
	// col input
	public void setColInput() {
		colInput = Integer.parseInt(scan.nextLine());
	}
	public int getColInput() {
		return colInput;
	}

	// ======== MENU ========
	// menu
	public void setMenu() {
		menu = scan.nextLine().toLowerCase();
		checkForNullInput(menu);
	}
	public String getMenu() {
		return menu;
	}

	// ======== FILE PATH ========
	// file path
	public void setFilePath() {
		JFileChooser f = new JFileChooser(".");
        f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
        f.showSaveDialog(null);

        System.out.println(f.getCurrentDirectory());
        outputFilePath = f.getSelectedFile().getAbsolutePath();
	}
	public String getFilePath() {
		return outputFilePath;
	}

	// ======== SEARCH ========
	// search string
	public void setSearchString() {
		searchString = scan.nextLine();
		checkForNullInput(searchString);
	}
	public String getSearchString() {
		return searchString;
	}

	// choose to edit either key or value
	public void setEditOption() {
		scan.nextLine();
		editOption = scan.nextLine().toLowerCase();
		checkForNullInput(editOption);
	}
	public String getEditOption() {
		return editOption;
	}

	// edit row index
	public void setRowEdit() {
		do {
		System.out.printf("Specify row index (0:%d) to update: ", rowInput-1);
		rowEdit = Integer.parseInt(scan.nextLine());
		}
		while (rowEdit > rowInput-1 || rowEdit < 0);
	}
	public int getRowEdit() {
		return rowEdit;
	}
	// edit col index
	public void setColEdit() {
		do {
		System.out.printf("Specify column index (0:%d) to update: ", colInput-1);
		colEdit = Integer.parseInt(scan.nextLine());
		} while (colEdit > colInput-1 || colEdit < 0);
	}
	public int getColEdit() {
		return colEdit;
	}
	
	// replace Key
	public void setEditValue(String editValue) {
		GetSetInput.editValue = editValue;
	}
	public String getEditValue() {
		return editValue;
	}
	public void setEditKeyString() {
		GetSetInput.editKeyString = scan.nextLine();
		checkForNullInput(editKeyString);
	}
	public String getEditKeyString() {
		return editKeyString;
	}

	// replace Value
	public void setEditKey(String editKey) {
		GetSetInput.editKey = editKey;
	}
	public String getEditKey() {
		return editKey;
	}
	public void setEditValueString() {
		GetSetInput.editValueString = scan.nextLine();
		checkForNullInput(editValueString);
	}
	public String getEditValueString() {
		return editValueString;
	}

	// ======== SORT ========
	// sort order
	public void setSortOrder() {
		sortOrder = scan.nextLine();
		checkForNullInput(sortOrder);
	}
	public  String getSortOrder() {
		return sortOrder;
	}

	// invalid input
	public void checkForNullInput(String input) {
		if (input.isEmpty()){
			throw new NullPointerException ();
		}
	}

}
