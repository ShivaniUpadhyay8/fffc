package com.octo.fffc;

public class Validator {

	public static void validate(String[] args) {
		System.out.println("VALIDATING INPUTS......");
		if(args.length<2) {
			System.out.println("Command line argument is/are missing......");
			System.out.println("COMMAND USAGE -- [java -jar <input data file path> <metadata file path> ");
			System.exit(0);
		}
		String inputFilePath = args[0];
		String metadataFilePath = args[1];
		if(inputFilePath==null || inputFilePath.isEmpty()) {
			System.out.println("File path for input data file can not be empty......");
			System.out.println("COMMAND USAGE -- [java -jar <input data file path> <metadata file path> ");
			System.exit(0);
		}
		if(metadataFilePath == null || metadataFilePath.isEmpty()) {
			System.out.println("File path for metadata file can not be empty......");
			System.out.println("COMMAND USAGE -- [java -jar <input data file path> <metadata file path> ");
			System.exit(0);
		}
		System.out.println("VALIDATION COMPLETED......");
	}

}
