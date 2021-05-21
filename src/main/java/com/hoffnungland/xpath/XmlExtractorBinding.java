package com.hoffnungland.xpath;
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
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class XmlExtractorBinding {
	private static final Logger logger = LogManager.getLogger(XmlExtractorBinding.class);

	private Processor xPathProcessor;
	//private ItemTypeFactory itemTypeFactory;
	//private XPathCompiler xPathCompiler;
	private XmlNsCtx nsCtx;
	private XdmNode docNode;

	//Common init steps
	private void init(String xmlNs, net.sf.saxon.s9api.QName[] listBindingVar) {
		logger.traceEntry();
		this.xPathProcessor = new Processor(false);
		//this.itemTypeFactory = new ItemTypeFactory(this.xPathProcessor);
		this.nsCtx = new XmlNsCtx(this.xPathProcessor.newXPathCompiler());

		if (xmlNs != null && !xmlNs.isEmpty()) {
			this.nsCtx.addNamespace(xmlNs);
		}

		if(listBindingVar!= null){
			for(net.sf.saxon.s9api.QName curBindingVal : listBindingVar){
				this.nsCtx.setCompilerBinding(curBindingVal);
			}
		}
		logger.traceExit();
	}

	public void init(File f, String xmlNs, net.sf.saxon.s9api.QName[] listBindingVar) throws SaxonApiException {
		logger.traceEntry();
		this.init(xmlNs, listBindingVar);
		DocumentBuilder documentBuilder = this.xPathProcessor.newDocumentBuilder();
		this.docNode = documentBuilder.build(f);
		logger.traceExit();
	}

	public void init(String xml, String xmlNs, net.sf.saxon.s9api.QName[] listBindingVar) throws SaxonApiException  {
		logger.traceEntry();
		//this.init(xmlNs, listBindingVar);
		//DocumentBuilder documentBuilder = this.xPathProcessor.newDocumentBuilder();
		//this.docNode = documentBuilder.build(new StreamSource(new StringReader(xml)));

		//if (listBindingVar!= null) {
		//	for(net.sf.saxon.s9api.QName curBindingVal : listBindingVar){
		//		this.nsCtx.setCompilerBinding(curBindingVal);
		//	}
		//}
		this.init(new StringReader(xml), xmlNs, listBindingVar);
		logger.traceExit();
	}

	public void init(Reader reader, String xmlNs, net.sf.saxon.s9api.QName[] listBindingVar) throws SaxonApiException  {
		logger.traceEntry();
		this.init(new StreamSource(reader), xmlNs, listBindingVar);
		logger.traceExit();
	}
	
	public void init(Source source, String xmlNs, net.sf.saxon.s9api.QName[] listBindingVar) throws SaxonApiException  {
		logger.traceEntry();
		
		this.init(xmlNs, listBindingVar);
		DocumentBuilder documentBuilder = this.xPathProcessor.newDocumentBuilder();
		this.docNode = documentBuilder.build(source);
	
		if (listBindingVar!= null) {
			for(net.sf.saxon.s9api.QName curBindingVal : listBindingVar){
				this.nsCtx.setCompilerBinding(curBindingVal);
			}
		}
		
		logger.traceExit();
	}

	public String extractString(String xPathStr) throws SaxonApiException  {
		logger.traceEntry();

		logger.debug("Evaluating xpath " + xPathStr);
		//this.xpath.compile(xPathStr);

		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPathStr);
		xPathSelect.setContextItem(this.docNode);
		XdmValue nodeValues = xPathSelect.evaluate();
		if (nodeValues.size() > 0) {
			return logger.traceExit(nodeValues.itemAt(0).getStringValue());
		} else {
			return logger.traceExit((String) null);
		}


	}
	
	public String extractString(XdmItem nodeItem, String xPathStr) throws SaxonApiException  {
		logger.traceEntry();
		if(nodeItem.isEmpty() || !nodeItem.isNode()) {
			return logger.traceExit((String) null);
		}

		XdmNode xPathNode = (XdmNode) nodeItem;
		logger.debug("Evaluating xpath " + xPathStr);
		//this.xpath.compile(xPathStr);

		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPathStr);
		xPathSelect.setContextItem(xPathNode);
		XdmValue nodeValues = xPathSelect.evaluate();
		if (nodeValues.size() > 0) {
			return logger.traceExit(nodeValues.itemAt(0).getStringValue());
		} else {
			return logger.traceExit((String) null);
		}

	}

	public XdmValue extractNode(String xPath) throws SaxonApiException, IndexOutOfBoundsException, SaxonApiUncheckedException, SAXException, IOException, ParserConfigurationException  {
		logger.traceEntry();
		logger.debug("Evaluating xpath " + xPath);
		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPath);

		xPathSelect.setContextItem(this.docNode);
		
		return logger.traceExit(xPathSelect.evaluate());
	}
	
	public XdmValue extractNode(XdmItem nodeItem, String xPath) throws SaxonApiException, IndexOutOfBoundsException, SaxonApiUncheckedException, SAXException, IOException, ParserConfigurationException  {
		logger.traceEntry();
		
		if(nodeItem.isEmpty() || !nodeItem.isNode()) {
			return logger.traceExit((XdmValue) null);
		}

		XdmNode xPathNode = (XdmNode) nodeItem;
		
		logger.debug("Evaluating xpath " + xPath);
		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPath);

		xPathSelect.setContextItem(xPathNode);
		
		return logger.traceExit(xPathSelect.evaluate());
	}

	public XPathSelector defineXPath(String xPath) throws SaxonApiException {
		logger.traceEntry();
		logger.debug("Evaluating xpath " + xPath);
		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPath);
		xPathSelect.setContextItem(this.docNode);
		return logger.traceExit(xPathSelect);
	}
	
	public XPathSelector defineXPath(XdmItem nodeItem, String xPath) throws SaxonApiException {
		logger.traceEntry();
		if(nodeItem.isEmpty() || !nodeItem.isNode()) {
			return logger.traceExit((XPathSelector) null);
		}

		XdmNode xPathNode = (XdmNode) nodeItem;
		logger.debug("Evaluating xpath " + xPath);
		XPathSelector xPathSelect = this.nsCtx.compileXPath(xPath);
		xPathSelect.setContextItem(xPathNode);
		return logger.traceExit(xPathSelect);
	}

	public XdmValue evaluateBinding(XPathSelector xPathSelect, net.sf.saxon.s9api.QName[] listBindingVar, net.sf.saxon.s9api.XdmItem[] listBindingValues) throws SaxonApiException{
		logger.traceEntry();
		int idx = 0;
		for(net.sf.saxon.s9api.QName curBindingVal : listBindingVar){
			xPathSelect.setVariable(curBindingVal, listBindingValues[idx]);
			idx++;
		}
		
		return logger.traceExit(xPathSelect.evaluate());

	}
	
}
