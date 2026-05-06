package com.qa.opencart.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {
	public static final String CSV_PATH = "./src/test/resources/testdata/";

	private static List<String[]> rows;


	public static Object[][] getCSVTestData(String csvName) {
		
		CSVReader reader; 
		
		String filename = CSV_PATH + csvName + ".csv";
		try {
			reader = new CSVReader(new FileReader(filename));
			rows = reader.readAll();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
		

		Object data[][]=new Object[rows.size()][];
		for (int i = 0; i < rows.size(); i++) {
			data[i]=rows.get(i);
		}
		
		
		return data;
	}

}
