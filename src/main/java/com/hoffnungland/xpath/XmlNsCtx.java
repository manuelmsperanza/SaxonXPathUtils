package com.hoffnungland.xpath;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;


/**
 * Namespace context and XPath compiler helper for dynamic XML namespace handling.
 */
public class XmlNsCtx implements NamespaceContext {
	
	private static final Logger logger = LogManager.getLogger(XmlNsCtx.class);
	
	private TreeMap<String, String> nsList;
	private XPathCompiler xPathCompiler;
	
	/**
	 * Builds a namespace context backed by the provided compiler.
	 *
	 * @param xPathCompiler Saxon XPath compiler instance
	 */
	public XmlNsCtx(XPathCompiler xPathCompiler) {
		logger.traceEntry();
		this.nsList = new TreeMap<String, String>();
		this.xPathCompiler = xPathCompiler;
		logger.traceExit();
	}
	
	/**
	 * Enables/disables Saxon XPath compilation cache.
	 *
	 * @param cachingFlag {@code true} to enable caching
	 */
	public void setXpathCompilerCaching(boolean cachingFlag){
		logger.traceEntry();
		this.xPathCompiler.setCaching(cachingFlag);
		logger.traceExit();
	}
	
	/**
	 * Declares a variable that can later be bound at evaluation time.
	 *
	 * @param bindingVar variable name to declare
	 */
	public void setCompilerBinding(net.sf.saxon.s9api.QName bindingVar){
		logger.traceEntry();
		this.xPathCompiler.declareVariable(bindingVar);
		logger.traceExit();
	}
	
	/**
	 * Parses and registers XML namespace declarations (e.g. {@code xmlns:a="urn:a"}).
	 *
	 * @param namespace namespace declaration string
	 */
	public void addNamespace(String namespace){
		logger.traceEntry();
		logger.debug("addNamespace " + namespace);
		Pattern nsPattern = Pattern.compile("xmlns(:(.+?))?=\"(.+?)\"");
		Matcher nsMatcher = nsPattern.matcher(namespace);
		while(nsMatcher.find()) {
			
			String prefix = nsMatcher.group(2);
			String nsUri = nsMatcher.group(3);
			if (prefix == null){
				prefix = new String();
			}
			
			this.xPathCompiler.declareNamespace(prefix, nsUri);
			this.addNamespace(prefix, nsUri);
		}
		logger.traceExit();
	}
	
	private void addNamespace(String prefix, String nsUri){
		logger.traceEntry();
		logger.debug("addNamespace " + prefix + " " + nsUri);
		this.nsList.put(prefix, nsUri);
		logger.traceExit();
	}
	
	@Override
	public String getNamespaceURI(String prefix) {
		logger.traceEntry();
		logger.debug("getNamespaceURI " + prefix);
		logger.debug(this.nsList.get(prefix));
		
		return logger.traceExit(this.nsList.get(prefix));
	}

	@Override
	public String getPrefix(String namespaceURI) {
		logger.traceEntry();
		logger.debug("getPrefix " + namespaceURI);
		for (Entry<String, String> namespace : this.nsList.entrySet()) {
			
			if (namespace.getValue().equals(namespaceURI)){
				logger.debug(namespace.getKey());
				return logger.traceExit(namespace.getKey());
			}
		}
		
		return logger.traceExit((String) null);
	}
	
	/**
	 * Compiles and loads an XPath expression into a selector.
	 *
	 * @param xPathStr XPath expression text
	 * @return loaded selector ready for context and evaluation
	 * @throws SaxonApiException if compilation fails
	 */
	public XPathSelector compileXPath(String xPathStr) throws SaxonApiException{
		logger.traceEntry();
		XPathExecutable xPathExe = this.xPathCompiler.compile(xPathStr);
		return xPathExe.load();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getPrefixes(String namespaceURI) {
		logger.traceEntry();
		logger.debug("getPrefixes " + namespaceURI);
		List<String> list = new ArrayList<String>();

		for (Entry<String, String> namespace : this.nsList.entrySet()) {
			
			if (namespace.getValue().equals(namespaceURI)){
				logger.trace(namespace.getKey());
				list.add(namespace.getKey());
			}
		}
		
		if (list.isEmpty()) {
			return logger.traceExit((Iterator) null);
		}else {
			
			return logger.traceExit(list.iterator());
		}
		
	}
	
}
