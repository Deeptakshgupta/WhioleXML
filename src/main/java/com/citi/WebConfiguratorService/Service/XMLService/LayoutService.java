package com.citi.WebConfiguratorService.Service.XMLService;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.citi.WebConfiguratorService.DTO.PaperSize;

/*
 * 1.) page Header -> unwrite all the text content of the Child Tags
 * 2.) page Footer -> unwrite the Text Content of the Child Tags
 * 3.) MultiLingual tag -> delete whole
 */
public class LayoutService {

	
/* ==================================================================================================================
	 1.)  Append MultiLingual Tag from Layout XML at Statement Level
	 */
	
public static void addMultiLingualTag(String destinationFilePath,Document sourceDocument, Document destinationDocument,Transformer transformer,StreamResult result) throws TransformerException, IOException{
		

		/** for layout only once this tag has to be appended **/
		// rest all other must be according to this tag details only, otherwise there is a risk for report failure
		NodeList multilingual=sourceDocument.getElementsByTagName("MultiLingual");
		Node multilingualNode= multilingual.item(0);
		
		Node importedTag = destinationDocument.importNode(multilingualNode, true);
		
		// appending the Tag, to be appended at Statement Level
		Element root = destinationDocument.getDocumentElement();
		NodeList taglist = root.getElementsByTagName("MultiLingual");
		
		if(taglist.getLength()==1)
		{
			// write in error Log 
			// Multilingual already present
			return;
		}
		Node childTextNode= destinationDocument.createTextNode("  ");
		Node parentTextNode = destinationDocument.createTextNode("\n");
		
		root.appendChild(childTextNode);
		root.appendChild(importedTag);
		root.appendChild(parentTextNode);
		result= new StreamResult(new File(destinationFilePath));
		transformer.transform(new DOMSource(destinationDocument),result);
	
}
/* ---------------------------------------------------------------------------------------- */

/*
=================================================================================
2.) Replacing Page Header at presentation level from layout xml  
*/
public static void addPageHeaderTag(String destinationFilePath, Document sourceDocument,
		Document destinationDocument, Transformer transformer,StreamResult result)throws TransformerException, IOException{	
	
	Node pageHeader= sourceDocument.getElementsByTagName("PageHeader").item(0);
	
	
	Node importedTag = destinationDocument.importNode(pageHeader, true);
	// to be replaced with the tag at presentation Level		
	Node old_pageHeader= destinationDocument.getElementsByTagName("PageHeader").item(1);
	
	Node parentNode= old_pageHeader.getParentNode();				
	
	parentNode.replaceChild(importedTag, old_pageHeader);
			
	result= new StreamResult(new File(destinationFilePath));
	transformer.transform(new DOMSource(destinationDocument),result);

			
}
/* ---------------------------------------------------------------------------------------- */

/*
=================================================================================
3.) Replacing Page Footer at presentation level from layout xml  
*/
public static void addPageFooterTag(String destinationFilePath, Document sourceDocument,
		Document destinationDocument, Transformer transformer,StreamResult result)throws TransformerException, IOException{	
	
	Node pageHeader= sourceDocument.getElementsByTagName("PageFooter").item(0);
	
	
	Node importedTag = destinationDocument.importNode(pageHeader, true);
	// to be replaced with the tag at presentation Level		
	Node old_pageHeader= destinationDocument.getElementsByTagName("PageFooter").item(1);
	
	Node parentNode= old_pageHeader.getParentNode();				
	
	parentNode.replaceChild(importedTag, old_pageHeader);
			
	result= new StreamResult(new File(destinationFilePath));
	transformer.transform(new DOMSource(destinationDocument),result);

}
/* ---------------------------------------------------------------------------------------- */

/*
=================================================================================
4.) Paper Size from Layout Xml, both Paper Size must be same else throw error 
*/
public static void updatePaperSize(String destinationFilePath, PaperSize paperSize, Document destinationDocument, Transformer transformer,StreamResult result) throws TransformerException,IOException {

	NodeList paperSizeTag= destinationDocument.getElementsByTagName("PaperSize").item(0).getChildNodes();
	
	paperSizeTag.item(1).setTextContent(paperSize.getHeight());// set Height
	paperSizeTag.item(3).setTextContent(paperSize.getWidth());// set width
	paperSizeTag.item(5).setTextContent(paperSize.getType());// set type
	
	result= new StreamResult(new File(destinationFilePath));
	transformer.transform(new DOMSource(destinationDocument),result);

	
}
/* ---------------------------------------------------------------------------------------- */

/* ---------------------------------------------------------------------------------------- */

}
