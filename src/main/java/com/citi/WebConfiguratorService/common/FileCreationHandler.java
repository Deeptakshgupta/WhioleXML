package com.citi.WebConfiguratorService.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreationHandler {

	public static FileWriter getFileWriter(String filePath) throws IOException
	{
		File file = new File(filePath);
		// if file exists then create file writer in append mode
		if(file.exists())
			return new FileWriter(file,true);
		else
			return new FileWriter(file);
	}
}
