package com.citi.WebConfiguratorService.common;

import java.io.IOException;

import javax.xml.transform.stream.StreamResult;

public class StreamResultCloser {

	public static void closeStreamResult(StreamResult result) throws IOException
	{
		result.getOutputStream().close();
		result.getOutputStream().flush();
	}
}
