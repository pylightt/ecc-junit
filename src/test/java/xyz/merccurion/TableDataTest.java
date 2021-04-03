package xyz.merccurion;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TableDataTest {
    
    private TableData test;
   
    private LinkedHashMap<String, String> table;
    private ArrayList<String> testKeys;
    private ArrayList<String> testValues;
    private ArrayList<String> testConcatList;
    private ArrayList<String> testAscList;
    private ArrayList<String> testDescList;

    @BeforeEach
    public void setUp() throws IOException {
        test = new TableData();
        
        table = new LinkedHashMap<String, String>();
        table.put("@#(","2S]"); table.put("!/(","iWs"); table.put("^M$","W>2"); table.put("&2=","Ma#"); table.put("0JB","#w."); 
        table.put("m+3","V@L"); table.put("Ie<","V,^"); table.put("=]o","J9!"); table.put("{3K","<{X"); table.put("8cg","t:F");
        table.put("z9E","0{3"); table.put("_G/","O?F"); table.put("poO","9=;"); table.put("TyH","OZV"); table.put("OHi","1R3");  
        
        String testKeysPath = "src/test/java/xyz/merccurion/testKeys.txt";
        testKeys = new ArrayList<>(Files.readAllLines(Paths.get(testKeysPath)));
        String testConcat = "src/test/java/xyz/merccurion/testConcat.txt";
        testConcatList = new ArrayList<>(Files.readAllLines(Paths.get(testConcat)));
        
    }

    @Test
    @DisplayName("Table should contain right number of elements (row*column)")
    public void keyValueTest() {
        assertEquals(15, test.keyValue(3,5).size());
    }

    @Test
    @DisplayName("Split keys should contain only keys from the table.")
    public void splitKeyTest() {
        assertEquals(testKeys, test.splitKey(table));
    }

    @Test
    @DisplayName("Split values should contain only values from the table.")
    public void splitValueTest() throws IOException {
        String testValuesPath = "src/test/java/xyz/merccurion/testValues.txt";
        testValues = new ArrayList<>(Files.readAllLines(Paths.get(testValuesPath)));
        assertEquals(testValues, test.splitValue(table));
    }

    @Test
    @DisplayName("Key should be updated")
    public void editKeyTest() {
        List<String> updatedKeyList= test.editKey(3,5,0,0,testKeys,"JANN");
        String updatedKey = updatedKeyList.get(0);
        assertEquals("JANN",updatedKey);   
    }

    @Test
    @DisplayName("Value should be updated.")
    public void editValueTest() {
        String updatedValue = test.editValue("poO","ILY",table);
        assertEquals("ILY", updatedValue);
    }

    @Test
    @DisplayName("Concatinated table should contain key+value entries.")
    public void concatTableTest() {
        List<String> concat = test.concatTable(table);
        assertEquals(testConcatList,concat);
    }

    @Test
    @DisplayName("Table should be sorted in ascending order.")
    public void sortTableAscendingTest() throws IOException {
        String testAsc = "src/test/java/xyz/merccurion/testAsc.txt";  
        testAscList = new ArrayList<>(Files.readAllLines(Paths.get(testAsc)));
        
        List<String> sortedAsc = test.sortTable("asc",testConcatList);
        assertEquals(testAscList, sortedAsc);
    }

    @Test
    @DisplayName("Table should be sorted in descending order.")
    public void sortTableDescendingTest() throws IOException {
        String testDesc = "src/test/java/xyz/merccurion/testDesc.txt";  
        testDescList = new ArrayList<>(Files.readAllLines(Paths.get(testDesc)));
       
        List<String> sortedDesc = test.sortTable("desc",testConcatList);
        assertEquals(testDescList, sortedDesc);
    }

    @Test
    @DisplayName("Table should be reinputted with data")
    public void writeToTableTest() {
        Map<String,String> testTable;
        testTable = table;
        assertEquals(testTable,test.writeToTable(testConcatList,table));
    }

    @Test
    @DisplayName("Reset table should be empty.")
    public void resetTableTest() {
        Map<String,String> resetTable;
        resetTable = test.resetTable();
        assertTrue(resetTable.isEmpty());
    }

}
    