package com.citi.WebConfiguratorService.Service;

import java.io.File;
import java.io.IOException;
//import java.time.LocalDateTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.citi.WebConfiguratorService.DTO.TemplateDTO;
import com.citi.WebConfiguratorService.Service.XMLService.ComponentService;
import com.citi.WebConfiguratorService.Service.XMLService.LayoutService;
import com.citi.WebConfiguratorService.common.DecodeXML;
import com.citi.WebConfiguratorService.common.StreamResultCloser;

import lombok.extern.slf4j.Slf4j;

import com.citi.WebConfiguratorService.DTO.Components;
import com.citi.WebConfiguratorService.DTO.Presentation;

// page loop
// take comp from it's indexing
// update XML -> w.r.t flag -> and check  how to delete the changes done in the updated the XMl.
// update the coordinates w.r.t the flag -> U

/**
 * Flags -> f - first Time Save  u - update  d- delete n- no change 
 * Presentation flag->
 * 
 * f -> fetch layout XML
 * 	First Time Tags updated:-
 * 		addMultiLingualTag -> not going to change while updations
 * 		add page header Tag -> not going to change while updations
 * 		add page Footer Tag ->not going to change while updations
 * 		set  typeTag -> not going to change while updations
 * 		set report name Tag ->not going to change while updations
 * 		set page Id Tag-> not going to change while updations
 * 		set page name Tag ->not going to change while updations
 *		set object Id Tag-> not going to change while updations
 *		update paper size ->    ** might get changed when paper size is being updated
 * 	
 * u -> update existing page layout
 * 	    Updations handled:-
 * 		report name tag
 * 		paper Size tag
 * 		
 * 
 * 
 * d-> delete the layout -> has to be replaced by some other at same time in case of additon of a new layout via drag and drop-> need a flag with f
 * 		delete and update the multilingual tag
 * 		page header
 * 		page footer
 * 		type Tag
 * 		Report Name -> can be same as previous
 * 		page Id -> can be same as previous
 * 		page name =-> same as previous
 * 		PageObject Id< same as previous
 * 		paper size -> can  be same as previous
 * 
 * n -> for no update or any changes just go as it is
 * 
 * component flag
 * 
 * 		
 * f-> fetch Component XML at Component level -> multiple components at a page level
 * 		updateMaster_presentation -> for every  new component -> 
 * 		appendLayerTag ->
 * 		provideSequencedSqlId ->
 * 		updateDimensions -> for both first and update
 *  
 * u -> update the component -> 
 * 		updateDimensions -> both for first and update component
 * 
 * 
 * d-> delete the component -,> then after adding a new one will require f flag to add this new one
 * 		removeLayerTag -> delete Layer Tag 
 * 		delete the sequence Id and it's reference id's -> trying to use Hashmap to do the function
 * 		
 * 
 * n-> no updation or changes just go as it is
 *
 */

@Service
@Slf4j
public class TemplateServiceDaoImpl implements TemplateServiceDao {

	public static StreamResult result= null;
	
	@Override
	public void templatePostRequest(TemplateDTO templatePayload) {
		// templatePayload.getPresentation().get("presentation");
		 StreamResult result = null;
		
		try {
			// flag to check the kind of operation\
			
			
			String presentation_flag = templatePayload.getPresentation().getPresentation_flag();
			
			/**Get the current timestamp*/
//			LocalDateTime now = LocalDateTime.now();
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
//	        String timestamp = now.format(formatter);
			
	        String templateFileName = templatePayload.getPresentation().getReportName()+"_"+"template.xml";
			
			String emptyTemplateFilePath = "src/main/webapp/XML/EmptyTemplate.xml";
			String destinationFilePath = "src/main/webapp/XML/"+templateFileName;

			//Document Object Initialization
			DocumentBuilderFactory sourceFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder sourceBuilder = sourceFactory.newDocumentBuilder();
			

			
			

			// final Template XML To be generated
			DocumentBuilderFactory destinationFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder destinationBuilder = destinationFactory.newDocumentBuilder();
			Document emptyTemplateDocument = destinationBuilder.parse(new File(emptyTemplateFilePath)); /**empty template to data copied to Template xml for first Time **/

			
			/** Transformer object initialization **/
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			File file = new File(destinationFilePath);
			/** can't be null **/
			 Document destinationDocument = null ;
			
			if(!file.exists())
			{
				result = new StreamResult(new File(destinationFilePath));
				transformer.transform(new DOMSource(emptyTemplateDocument), result);
				
				destinationDocument = sourceBuilder.parse(new File(destinationFilePath));
			}
			else
				destinationDocument = sourceBuilder.parse(new File(destinationFilePath)); // just take the copy of that file and start updating
			
			
			
			
			
			int pages_present = templatePayload.getPresentation().getPages().size();// -> size of the pages present in
																					// the Template

			/*
			 * ============================================================================================================= 
			 * Updating only once -> flag = "f" over writing all XMl files
			 */

			/** Tag Retrieval **/

			// Type Value at presentation level
			Node typeTag = destinationDocument.getElementsByTagName("Type").item(0);
			Node reportNameTag = destinationDocument.getElementsByTagName("ReportName").item(0);
			Node pageTag = destinationDocument.getElementsByTagName("Page").item(0);
			NodeList pageNodes= pageTag.getChildNodes();
			Node pageIdTag=pageNodes.item(1);
			Node pageNameTag=pageNodes.item(3);
			Node pageObjectIdTag=pageNodes.item(5);
					
			Presentation presentation_payload = templatePayload.getPresentation();
			for (int i = 0; i < pages_present; i++) {
								
				/** first time save operations**/
				if (presentation_flag.equalsIgnoreCase("f")) {
					String layoutFileName= templatePayload.getPresentation().getPages().get(i).getLayoutName()+"_"+"layout.xml";
					String layoutFilePath = "src/main/webapp/XML/"+layoutFileName;
					DecodeXML.fetchXML(templatePayload.getPresentation().getPages().get(i).getLayoutXml(),layoutFilePath);
					// getting the first page from 0th index for time being later will add a loop
					Document layout_doc = sourceBuilder.parse(new File(layoutFilePath));
					/** Value set for XML **/

					/** MultiLingual Tag from Layout Tag Append function call **/
					LayoutService.addMultiLingualTag(destinationFilePath, layout_doc, destinationDocument, transformer,result);
					System.out.println("Log :- Multilingual Tag Appended Successful");


					/** Page Header from Layout XML Replace at presentation level **/
					LayoutService.addPageHeaderTag(destinationFilePath, layout_doc, destinationDocument, transformer,result);
					System.out.println("Log :- Page Header replace Successfully");

					/** PageFooter from layout XML replace at presentati0n level **/
					LayoutService.addPageFooterTag(destinationFilePath, layout_doc, destinationDocument, transformer,result);
					System.out.println("Log :- Page Footer replace Successfully");

					/** set Type Tag**/
					typeTag.setTextContent(presentation_payload.getType());

					/** set ReportName**/
					reportNameTag.setTextContent(presentation_payload.getReportName());

					// created By
					/** set PageId **/
					pageIdTag.setTextContent(presentation_payload.getPages().get(i).getPageId());
					
					/** set PageName **/
					pageNameTag.setTextContent(presentation_payload.getPages().get(i).getPageName());
					
					/** set PageObjectId **/
					pageObjectIdTag.setTextContent(presentation_payload.getPages().get(i).getPageObjectId());
					
					/** paperSize -> to be same for Component and layout -> check can also be applied
					/ to check the paper size and then display a warning at frontEnd*/
					LayoutService.updatePaperSize(destinationFilePath, presentation_payload.getPaperSize(),destinationDocument, transformer,result);
					
					// for saving the Tags that  was updated
					
					result = new StreamResult(new File (destinationFilePath));
					destinationDocument.normalize();
					transformer.transform(new DOMSource(destinationDocument),
							result);
				
				
				}
				
				/** No changes 'n' flag **/
				if(presentation_flag.equalsIgnoreCase("n"))
				{
					// no changes were there just continue 
					continue;					
				}
				
				/** Updations at Page level **/
				if(presentation_flag.equalsIgnoreCase("u"))
				{
					/** UPDATE report name */
					reportNameTag.setTextContent(presentation_payload.getReportName());
					
					
					/** UPDATE paper size  
					 * 
					 * before updations need to check whether paper size is same or has been changed
					 * **/
					LayoutService.updatePaperSize(destinationFilePath, presentation_payload.getPaperSize(),destinationDocument, transformer,result);
					
				}
				
				
				
				/** **/
				/*
				 * ==================================================================================================================*/

				/*
				 * ================================================================================================================== 
				 *  Loop Updates over writing same XMl file for storing
				 */

				int components_present = templatePayload.getPresentation().getPages().get(i).getComponents().size();// components present per Page
													
				
				for (int j = 0; j < components_present; j++) {
					/**
					 * More than one Components can be present traverse in loop and then generate
					 * sequential components
					 **/
					
			        		
					
					Components curr_comp = templatePayload.getPresentation().getPages().get(i).getComponents().get(j);
					String component_flag = curr_comp.getComp_flag();
					String base64Array = curr_comp.getComponentXml().toString();
					
					String componentFileName=curr_comp.getComponentName()+"_"+"component.xml";
					String componentFilePath = "src/main/webapp/XML/"+componentFileName;
					DecodeXML.fetchXML(base64Array, componentFilePath);
					Document component_doc = sourceBuilder.parse(new File(componentFilePath));

					/** Master Tag from Component XML Replace at presentation level **/
					/** Latest Component Master gets appended**/
					
					
					
					/** Layer Tag from component XML Append function call to be update once only */
					if (component_flag.equalsIgnoreCase("f"))
					{
						log.info("Adding component: "+ curr_comp.getComponentName());
						ComponentService.updateMaster_Presentation(destinationFilePath, component_doc, destinationDocument,
								transformer,result);
						log.info("Master Tag Updated as per Latest Component");
						System.out.println("Log :- Master Tag Replaced Successful");
						ComponentService.appendLayerTag(destinationFilePath, component_doc, destinationDocument,
								transformer,result);
						log.info("Layer Tag appened for the Component:"+ curr_comp.getComponentName());
						
						ComponentService.provideSequencedSqlId( destinationFilePath,  component_doc,
								 destinationDocument,  transformer,  result);
						log.info("adding sequence id:");
					}
					

					if (component_flag.equalsIgnoreCase("f") || component_flag.equalsIgnoreCase("u")) // component_flag != "d" -> then also
																					// this may Run
					{
						ComponentService.updateDimensions(curr_comp.getComponentName(), curr_comp.getTop(),
								curr_comp.getLeft(), destinationFilePath, destinationDocument, transformer,result);
						log.info("dimnensions getting updated ");						
					}
					
					/** Update Component */
					if(component_flag.equalsIgnoreCase("u"))
					{
						/** change the dimensions of the updated component when orientation is changed*/
						ComponentService.updateDimensions(curr_comp.getComponentName(), curr_comp.getTop(),
								curr_comp.getLeft(), destinationFilePath, destinationDocument, transformer,result);
					}
					
					
					/** delete the component */
					if (component_flag.equalsIgnoreCase("d")) {
						
						/** remove the component's Layer  */
						ComponentService.removeLayerTag( curr_comp.getComponentName(), destinationFilePath, destinationDocument,transformer, result);
						
						/***/
					}

				}
			}
		} catch (TransformerException e) {
			// log file
			System.out.println(e.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		finally{
			if (result != null && result.getOutputStream() != null) {
                try {
                	StreamResultCloser.closeStreamResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			
		}
	}
}
