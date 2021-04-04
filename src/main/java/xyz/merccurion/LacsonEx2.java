package xyz.merccurion;

import java.io.*;

public class LacsonEx2 {
	public static void main(String[] args) throws FileNotFoundException {
		try {
		String menu;
		do {
		GetSetInput set = new GetSetInput();
		GetSetInput get = new GetSetInput();

		System.out.print("Select file location and input file name: ");
		set.setFilePath();
		String outputFilePath = get.getFilePath();
		File file = new File(outputFilePath);

		// get table dimensions
		System.out.print("\nEnter number of rows: ");
		set.setRowInput();
		int row = get.getRowInput();
		System.out.print("Enter number of columns: "); 
		set.setColInput();
		int col =get.getColInput();

		// table data
		TableData table = new TableData();
		table.resetTable();
		table.keyValue(row, col);
		table.writeToFile(file);
		table.printTable(file);

		// get menu input
		System.out.print("\n\nMenu:\nSearch\nEdit\nPrint\nAdd Row\nSort\nReset\nExit\n\n");
		System.out.print("Choose an option: ");
		set.setMenu();
		Menu choose = new Menu();
		menu = get.getMenu();

		switch (menu){
			case "search":
				System.out.print("Input search character/s: ");
				set.setSearchString();
				choose.menuSearch();
				break;

			case "edit":
				set.setRowEdit();
				set.setColEdit();
				System.out.print("Edit key or value?: ");
				set.setEditOption();
				int rowEdit = get.getRowEdit();
				int colEdit = get.getColEdit();
				choose.menuEdit(rowEdit, colEdit);
				break;

			case "print":
				table.printTable(file);
				break;

			case "add row":
				choose.menuAddRow();
				break;

			case "sort":
				System.out.print("Sort order = asc/desc: ");
				set.setSortOrder();	
				choose.menuSortRow();
				break;

			case "reset":
				break;

			case "exit":
				choose.menuExit();
				break;
			default:
				choose.menuDefault();
		}
		}
		while (menu.equals("reset"));
		} catch (NullPointerException e) {
			System.out.println("Invalid. Input must not be null.\n");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Invalid. Input must be an integer.\n");
			e.printStackTrace();
		} 
	}
}