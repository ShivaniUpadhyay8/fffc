package com.octo.fffc;

public class FixedFileFormatConverterMain {

	public static void main(String[] args) {
		Validator.validate(args);
		
		FileFormatConverter.processFile(args[0], args[1]);
	}
}
