package com.octo.fffc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileFormatConverter {

	private static final String OUTPUT_CSV_FILE_CSV = "outputCSVFile.csv";

	static String charset = "UTF-8";
	
	static List<String>columnType = new ArrayList<String>();
	static List<String> coulmnLength = new ArrayList<String>();
	static String inputDatePattern = "yyyy-mm-dd";
	static String dateFormat = "dd/mm/yyyy";
	static List<String>columnHeader = new ArrayList<String>();
	//static List<List<String>>rows = new ArrayList<List<String>>();
	//static List<List<String>>coulmnDetails = new ArrayList<List<String>>();
	
	public static void processFile(String inputFileName, String metadataFileName) {
		File dataFile = new File(inputFileName);
		File metadataFile = new File(metadataFileName);
		
		
	    
	    try {
	    	//read metadata file and extract column details 
	    	List<List<String>>coulmnDetails = parseMetadata(metadataFile);
            // read data file using details of column
	    	List<List<String>>rows = parseInputDataFile(dataFile, coulmnDetails);
            // write the output file
            creatOutputFile(rows);
            
	    } catch (FileNotFoundException e) {
	    	System.out.println("Input or Data file is not found on the path provided in command line arguments....");
			e.printStackTrace();
						
		} catch (UnsupportedEncodingException e) {
			System.out.println("File format is not correct....");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error occured during file reading/writing....");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Error occurred during parsing....");
			e.printStackTrace();
		}
		
	}

	static List<List<String>> parseInputDataFile(File dataFile, List<List<String>> coulmnDetails) throws FileNotFoundException, IOException, ParseException {
		BufferedReader dataBr = new BufferedReader(new FileReader(dataFile));
		String dataLine;
		List<List<String>>rows = new ArrayList<List<String>>();
		while((dataLine = dataBr.readLine()) !=null) {
			
			int startIndex=0;
			int endIndex = 0;
			List<String>columns = new ArrayList<String>();
			for (List<String> entry : coulmnDetails) {
				
				String columnLength = entry.get(1);            		
				String coulmnType = entry.get(2);            		
				endIndex = startIndex + (Integer.parseInt(columnLength.trim()));            		
				String columnData = dataLine.substring(startIndex, endIndex).trim();            		
				startIndex = endIndex;
				
				//format column data as per the column type
				columnData = formatColumnData(coulmnType, columnData);            		
				columns.add(columnData);
			}
			rows.add(columns);
		}
		dataBr.close();
		return rows;
	}

	static String formatColumnData(String coulmnType, String columnData) throws ParseException {
		if(coulmnType.equalsIgnoreCase("date")){
			//format date
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDatePattern);
			Date date = simpleDateFormat.parse(columnData);
			
			SimpleDateFormat outputSimpleDateFormat = new SimpleDateFormat(dateFormat);
			columnData = outputSimpleDateFormat.format(date);
			
		}else if(coulmnType.equalsIgnoreCase("numeric")){
			//number formatting
			if(columnData.contains(",")) {
				columnData = columnData.replaceAll(",", "");
			}
		}else {

			columnData = escapeSpecialCharacters(columnData);
			
		}
		return columnData;
	}

	public static String escapeSpecialCharacters(String inputString){
	    final String[] specialChars = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%",",","#","!"};
	
	    for (int i = 0 ; i < specialChars.length ; i++){
	        if(inputString.contains(specialChars[i])){
	            inputString = "\""+ inputString +"\"";
	        }
	    }
	    return inputString;
	}

	static List<List<String>> parseMetadata(File metadataFile) throws FileNotFoundException, IOException {
		BufferedReader metadataBr = new BufferedReader(new FileReader(metadataFile));
		String metadataLine;
		List<List<String>>coulmnDetails = new ArrayList<List<String>>();
		while ((metadataLine = metadataBr.readLine()) != null) {
			List<String> mapEntry = new ArrayList<String>();
			String[] columnMetadata = metadataLine.split("\\s*,\\s*");
			columnHeader.add(columnMetadata[0]);
			
			mapEntry.add(columnMetadata[0]);
			mapEntry.add(columnMetadata[1]);
			mapEntry.add(columnMetadata[2]);
			coulmnDetails.add(mapEntry);
		}
		metadataBr.close();
		return coulmnDetails;
	}

	static void creatOutputFile(List<List<String>> rows) throws IOException {
		File csvFile = new File(OUTPUT_CSV_FILE_CSV);
		BufferedWriter dataWr = new BufferedWriter(new FileWriter(csvFile));
		for (String headerCol : columnHeader) {
			dataWr.append(headerCol +",");
			dataWr.flush();
		}
		dataWr.newLine();
		for (List<String> row : rows) {
			for (String column : row) {
				dataWr.append(column +",");
				dataWr.flush();
			}
			dataWr.newLine();
		}
		
		dataWr.close();
	}

}
