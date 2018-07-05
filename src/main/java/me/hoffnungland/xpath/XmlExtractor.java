package me.hoffnungland.xpath;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.SaxonApiUncheckedException;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;


/**
 * Wrapper of Saxon xpath extractor
 * @author manuel.m.speranza
 * @version 0.1
 * @since 29/06/2016
 */
public class XmlExtractor {
	
	private static final Logger logger = LogManager.getLogger(XmlExtractor.class);
	
	private Processor xPathProcessor;
	//private ItemTypeFactory itemTypeFactory;
	//private XPathCompiler xPathCompiler;
	private XmlNsCtx nsCtx;
	private XdmNode docNode;
	
	/**
	 * Default initializer of low-level components
	 */
	private void init() {
		logger.traceEntry();
		this.xPathProcessor = new Processor(false);
		//this.itemTypeFactory = new ItemTypeFactory(this.xPathProcessor);
		this.nsCtx = new XmlNsCtx(this.xPathProcessor.newXPathCompiler());
		logger.traceExit();
	}
	
	/**
	 * Initialize the low-level components starting from a File
	 * @param file The XML file
	 * @throws SaxonApiException
	 */
	public void init(File file) throws SaxonApiException {
		logger.traceEntry();
		this.init();
		DocumentBuilder documentBuilder = this.xPathProcessor.newDocumentBuilder();
		this.docNode = documentBuilder.build(file);
		logger.traceExit();
	}
	/**
	 * Initialize the low-level components starting from a string
	 * @param xml The XML string
	 * @throws SaxonApiException
	 */
	public void init(String xml) throws SaxonApiException  {
		logger.traceEntry();
		//this.init();
		//DocumentBuilder documentBuilder = this.xPathProcessor.newDocumentBuilder();
		//this.docNode = documentBuilder.build(new StreamSource(new StringReader(xml)));
		this.init(new StringReader(xml));
		logger.traceExit();
	}
	/**
	 * Initialize the low-level components starting from a Reader
	 * @param reader The reader of an XML string
	 * @throws SaxonApiException
	 * @author manuel.m.speranza
	 * @since 28-05-2017
	 */
	public void init(Reader reader) throws SaxonApiException  {
		logger.traceEntry();
		this.init(new StreamSource(reader));
		logger.traceExit();
	}
	
	/**
	 * Initialize the low-level components starting from a W3C document source
	 * @param source The W3C document source of an XML string
	 * @throws SaxonApiException
	 * @author manuel.m.speranza
	 * @since 28-05-2017
	 */
	public void init(Source source) throws SaxonApiException  {
		logger.traceEntry();
		this.init();
		DocumentBuilder documentBuilder = this.xPathProcessor.newDocumentBuilder();
		this.docNode = documentBuilder.build(source);
		logger.traceExit();
	}
	
	public String extractString(String xPathStr, String xmlNs) throws SaxonApiException  {
		logger.traceEntry();
		logger.debug("Evaluating xpath " + xPathStr + " " + xmlNs);
		//this.xpath.compile(xPathStr);
		if (xmlNs != null && !xmlNs.isEmpty()) {
			this.nsCtx.addNamespace(xmlNs);
		}
		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPathStr);
		xPathSelect.setContextItem(docNode);
		XdmValue nodeValues = xPathSelect.evaluate();
		if (nodeValues.size() > 0) {
			return logger.traceExit(nodeValues.itemAt(0).getStringValue());
		} else {
			return logger.traceExit((String) null);
		}

		
	}
	
	public XdmValue extractNode(String xPath, String xmlNs) throws SaxonApiException, IndexOutOfBoundsException, SaxonApiUncheckedException, SAXException, IOException, ParserConfigurationException  {
		logger.traceEntry();
		logger.debug("Evaluating xpath " + xPath + " " + xmlNs);
		//this.xpath.compile(xPathStr);
			if (xmlNs != null && !xmlNs.isEmpty()) {
				this.nsCtx.addNamespace(xmlNs);
			}
			XPathSelector xPathSelect = this.nsCtx.compileXPath(xPath);
			
			xPathSelect.setContextItem(docNode);
			return logger.traceExit(xPathSelect.evaluate());
	}
	
}
