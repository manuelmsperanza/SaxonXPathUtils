package com.hoffnungland.xpath;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LogManager.getLogger(App.class);
	public static void main( String[] args )
	{
		logger.traceEntry();
		
		String xml = "<?xml version=\"1.0\"?><ROWSET><ROW><DUMMY>X</DUMMY></ROW></ROWSET>";
		String newNode = "<ENTITY>new entity</ENTITY>";

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Reader reader = new StringReader(xml);
		
			Document doc = docBuilder.parse(new InputSource(reader));
			
			// Get the root element
			Node root = doc.getDocumentElement();
		
			Document childDoc = docBuilder.parse(new InputSource(new StringReader(newNode)));
			
			// Create a duplicate node
		    Node newChild = childDoc.getDocumentElement().cloneNode(true);
			
			doc.adoptNode(newChild);
			
			root.appendChild(newChild);
			
			Attr objTypeAttr = doc.createAttribute("obj_type");
			
			objTypeAttr.setValue("1000");
			
			// update root attribute
			NamedNodeMap attrs = root.getAttributes();
			attrs.setNamedItem(objTypeAttr);
			

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
			System.out.println(output);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		logger.traceExit();
	}
}
