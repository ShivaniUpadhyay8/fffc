package com.octo.fffc;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FileFormatConverterTest {

	String specialCharComma = "Smith,Bill";
	String specialCharPound = "Marry#Kom";
	 List<List<String>>coulmnDetails = new ArrayList<List<String>>();
	 String dataFilePath = "/FFFC/dataFile.txt";
	 String metadataFile = "/FFFC/metadataFile.txt";
	 
	
	@Test
	public void testParseMetadata() throws FileNotFoundException, IOException {
		File filePath = new File(metadataFile);
		List<List<String>>coulmnDetails = FileFormatConverter.parseMetadata(filePath);
		assertNotNull(coulmnDetails);	
	}
	
	@Test
	public void testParseInputDataFile() throws FileNotFoundException, IOException, ParseException {
		File filePath = new File(dataFilePath);
		List<String> colData1 = new ArrayList<String>();
		colData1.add("Birth date");
		colData1.add("10");
		colData1.add("date");
		coulmnDetails.add(colData1);

		List<String> colData2 = new ArrayList<String>();
		colData2.add("First name");
		colData2.add("15");
		colData2.add("string");
		coulmnDetails.add(colData2);

		List<String> colData3 = new ArrayList<String>();
		colData3.add("Last name");
		colData3.add("15");
		colData3.add("string");
		coulmnDetails.add(colData3);

		List<String> colData4 = new ArrayList<String>();
		colData4.add("Weight");
		colData4.add("5");
		colData4.add("numeric");
		coulmnDetails.add(colData4);
		 
		List<List<String>> outputData = FileFormatConverter.parseInputDataFile(filePath, coulmnDetails);
		assertNotNull(outputData, "Output data received...");
		
	}
	
	
	@Test
	void testEscapeSpecialCharacterComma() {
		String formattedData = FileFormatConverter.escapeSpecialCharacters(specialCharComma);
		assertTrue(formattedData.contains("\""));
		assertTrue(formattedData.contains(","));
		String outputStringWithComma = "\"Smith,Bill\"";
		assertEquals(true, outputStringWithComma.matches(formattedData));
	}

	@Test
	public void testEscapeSpecialCharacterPound() {
		String formattedData = FileFormatConverter.escapeSpecialCharacters(specialCharPound);
		assertTrue(formattedData.contains("\""));
		assertTrue(formattedData.contains("#"));
		String outputStringWithComma = "\"Marry#Kom\"";
		assertEquals(true, outputStringWithComma.matches(formattedData));
	}
	
	@Test
	public void testFormatColumnData() {
		String columnType = "date";
		String columnData = "1970-01-01";
		String finalData = ""; 
		try {
			finalData = FileFormatConverter.formatColumnData(columnType, columnData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertNotEquals("", finalData);
		assertEquals(true, finalData.matches("01/01/1970"));
	}
	
	@Test
	public void testFormatColumnDataString() {
		String columnType = "string";
		String columnData = "John";
		String finalData = ""; 
		try {
			finalData = FileFormatConverter.formatColumnData(columnType, columnData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertNotEquals("", finalData);
		assertEquals(true, finalData.matches("John"));
	}
}
