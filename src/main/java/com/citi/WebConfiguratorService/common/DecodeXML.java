package com.citi.WebConfiguratorService.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class DecodeXML {

	public static void fetchXML(String base64Array, String filePath) {
		byte[] decodedBytes = Base64.getDecoder().decode(base64Array);
		String xmlContent = new String(decodedBytes, StandardCharsets.UTF_8);
		try (FileOutputStream fos = new FileOutputStream(filePath)) {

			fos.write(xmlContent.getBytes(StandardCharsets.UTF_8));
			System.out.println("XML file created successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
