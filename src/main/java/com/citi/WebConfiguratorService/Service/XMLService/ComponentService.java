package com.citi.WebConfiguratorService.Service.XMLService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.extern.slf4j.Slf4j;



/*
1.)  masterTag_Presentation Level -> delete the content
2.)  delete Layer Tag, no such changes would be there
3.)  update the Dimensions
*/

@Slf4j

public class ComponentService {

	static String compTagName = "ComponentName"; // component name tag as per in the XML to search the component as per the component name 
	
	
	/*
	 * =============================================================================
	 * ===================================== 1.)Layer Tag from Component XML
	 */
	public static void appendLayerTag(String destinationFilePath, Document sourceDocument, Document destinationDocument,
			Transformer transformer, StreamResult result) throws TransformerException, IOException {
		// Get the Layer element from the source document

		NodeList nodeList = sourceDocument.getElementsByTagName("Layer");
//      traverseXML(nodeList.item(0));
		Element tagElement = (Element) nodeList.item(0);

		// Importing the whole Layer Tag extracted from the Component XML
		Node importedTag = destinationDocument.importNode(tagElement, true);

		// Appending the imported Tag into Template XML
		NodeList layerList = destinationDocument.getElementsByTagName("Layers");
		Node layer = layerList.item(0);
		Node childTextNode = destinationDocument.createTextNode("  ");
		Node parentTextNode = destinationDocument.createTextNode("\n\t\t");

		/**
		 * Appending the imported Tag and empty Text nodes to maintain the XML Structure
		 **/
		layer.appendChild(childTextNode);
		layer.appendChild(importedTag);
		layer.appendChild(parentTextNode);

		destinationDocument.normalize();
		result = new StreamResult(new File(destinationFilePath));
		transformer.transform(new DOMSource(destinationDocument), result);

	}

	/*
	 * -----------------------------------------------------------------------------
	 * -----------
	 */

	/*
	 * =============================================================================
	 * ==== 2.)Dimension Tag Update
	 */
	public static void updateDimensions(String compName, String top, String left, String destinationFilePath,
			Document destinationDocument, Transformer transformer, StreamResult result)
			throws TransformerException, Exception {

		/** Entering the layers Tag in the XML **/
		NodeList layersTag = destinationDocument.getElementsByTagName("Layers");

		DocumentBuilderFactory sourceFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder sourceBuilder = sourceFactory.newDocumentBuilder();

		/** Getting the list of all the layer tags present in the layers Tag **/
		NodeList layerList = layersTag.item(0).getChildNodes();

		for (int i = 0; i < layerList.getLength(); i++) {
			// getting the Layer from the layer List w.r.t the index
			Node layer = layerList.item(i);

			Element layerElement = null;
			// Text Node Filter Only traversing the Element Node
			if (layer instanceof Element) {
				layerElement = (Element) layer;

				if (checkComponentName_dimensions(layerElement,compTagName, compName)) {
					NodeList layerDimensions = layerElement.getElementsByTagName("Dimensions");
					modifyDimension(layerDimensions, top, left, destinationFilePath, destinationDocument, transformer,
							result);
				}
			}
		}
	}

	public static void modifyDimension(NodeList nodeList, String top, String left, String destinationFilePath,
			Document destinationDocument, Transformer transformer, StreamResult result)
			throws TransformerException, IOException {

		/**
		 * 1->Left-> to be changed 3-> Top ->to be changed 5-> Height -> w.r.t to the
		 * XMl Appended from the Layer Tag 7-> Width -> w.r.t to the XMl Appended from
		 * the Layer Tag 9-> PageType -> w.r.t to the XMl Appended from the Layer Tag
		 */

		NodeList nodeprops = nodeList.item(0).getChildNodes();
		Node left_node = nodeprops.item(1);
		Node top_node = nodeprops.item(3);

		left_node.setTextContent(left);
		top_node.setTextContent(top);

		// log for Successful updation of Dimensions
		/** Writing the Changes into the XMl file **/
		result = new StreamResult(new File(destinationFilePath));
		transformer.transform(new DOMSource(destinationDocument), result);

	}

	private static boolean checkComponentName_dimensions(Element layerElement, String compTagName, String value) {

		NodeList requiredNode = layerElement.getElementsByTagName(compTagName);
		Node component = requiredNode.item(0);
		if (component.getTextContent().equals(value))
		{
			log.info("Component found with Name:"+value);
			return true;
		}

		return false;
	}
	/*
	 * -----------------------------------------------------------------------------
	 * -----------
	 */

	/*
	 * =============================================================================
	 * ==== 3.)Master tag Replace from Presentation level
	 */
	public static void updateMaster_Presentation(String destinationFilePath, Document sourceDocument,
			Document destinationDocument, Transformer transformer, StreamResult result)
			throws TransformerException, IOException {
		// Get the Layer element from the source document
		// as we only need the one and only layer tag present in the component XML

		NodeList nodeList = sourceDocument.getElementsByTagName("Master");
		/**
		 * Hardcoded retrieval of Master Under Presentation Tag -> 2nd item as of the
		 * NodeList 3 Master Nodes present at Layer Level, presentation level, DFL level
		 * can also be dynamic w.r.t. presentation as parent Node check
		 *
		 **/
		Element tagElement = (Element) nodeList.item(1);// master Element of presentation as parent Node st
		Node importedTag = destinationDocument.importNode(tagElement, true);

		NodeList masterList = destinationDocument.getElementsByTagName("Master");
		// Hardcoded retrieval of master Tag present at presentation level
		Node parentNode = masterList.item(1).getParentNode();

		Node master_to_be_replaced = masterList.item(1);
		parentNode.replaceChild(importedTag, master_to_be_replaced);

		result = new StreamResult(new File(destinationFilePath));
		transformer.transform(new DOMSource(destinationDocument), result);

	}

	/*
	 * -----------------------------------------------------------------------------
	 * -----------
	 */

	/*
	 * =============================================================================
	 * ==== 4.)SQLS tag Replace from Statement level
	 */

	static List<String> usedSQLId = new ArrayList<String>();

	public static void provideSequencedSqlId(String destinationFilePath, Document sourceDocument,
			Document destinationDocument, Transformer transformer, StreamResult result)
			throws TransformerException, IOException {
		try {
			NodeList dflList = sourceDocument.getElementsByTagName("Sqls");
			Node sqls = sourceDocument.getElementsByTagName("Sqls").item(0);
				
			Node importedNode = destinationDocument.importNode(sqls, true);
				Node templateSqlsNode = destinationDocument.getElementsByTagName("Sqls").item(0);
				
				templateSqlsNode.getParentNode().replaceChild(importedNode, templateSqlsNode);
				 destinationDocument.normalize();
				 result =  new StreamResult(new File(destinationFilePath)) ;
	            transformer.transform(new DOMSource(destinationDocument),result);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
}
/** ----------------------------------------------------------------------------------------------------------------------------------------------- */

	/*
	 * =============================================================================
	 * ==== 5.)Delete Layer Tag -> removing the component
	 */
	public static void removeLayerTag(String componentName, String destinationFilePath, Document templateDoc,
			Transformer transformer, StreamResult result) throws TransformerException, IOException {
		// find the layer tag w.r.t component name
		int index =-1; 
		
		NodeList layers = templateDoc.getElementsByTagName("Layer");
		
		for( int i =0; i< layers.getLength(); i++) {
			Element layer = (Element)layers.item(i);
		if(checkComponentName_removeLayer(layer, compTagName, componentName))
			{
			index = i;
			break;
			}
		}
		
		Node layer_to_delete = layers.item(index);
		System.out.println(((Element)layers.item(index)).getElementsByTagName("ComponentName").item(0).getTextContent() );
		layer_to_delete.getParentNode().removeChild(layer_to_delete);
		
		templateDoc.normalize();
		result = new StreamResult(new File(destinationFilePath));
		transformer.transform(new DOMSource(templateDoc), result);

	}
	
	private static boolean checkComponentName_removeLayer (Element layerElement, String  compName, String value) {

		NodeList requiredNode = layerElement.getElementsByTagName(compName);
		Node component = requiredNode.item(0);
		System.out.println(component.getTextContent());
		if (component.getTextContent().equals(value))
		{	
			System.out.println("Component Found");
			return true;
			
		}

		return false;
	}
	
/** ----------------------------------------------------------------------------------------------------------------------------------------------- */

	
	/*
	 * =============================================================================
	 * ==== 7.) Sequence Id -> 
	 */
	
	
/** ----------------------------------------------------------------------------------------------------------------------------------------------- */


	/** Travrse XML **/

	/*
	 * private static void traverseXmlTree(Node node) {
	 * 
	 * NodeList childNodes = node.getChildNodes();
	 * 
	 * for (int i = 0; i < childNodes.getLength(); i++) { if
	 * (childNodes.item(i).getNodeType() == node.TEXT_NODE) continue;
	 * 
	 * else { System.out.
	 * println("-----------Entry Tag-------------------------------------" +
	 * node.getNodeName()); System.out.println("Entering into: " +
	 * childNodes.item(i).getNodeName() + " Node");
	 * 
	 * traverseXmlTree(childNodes.item(i)); } } if (node instanceof Element) { //
	 * 
	 * System.out.println("Node name: " + node.getNodeName());
	 * if(node.getTextContent()!=null) System.out.println("Node value: " +
	 * node.getTextContent());// values else System.out. System.out.
	 * println("Null Check Don't Need to Traverse, Also break recurive calls of again printing the child node values that were already printed"
	 * );
	 * 
	 * System.out.println("Child Node:" + node.getChildNodes());
	 * 
	 * }
	 * System.out.println("-----------Exit Tag-------------------------------------"
	 * + node.getNodeName()); }
	 */

}