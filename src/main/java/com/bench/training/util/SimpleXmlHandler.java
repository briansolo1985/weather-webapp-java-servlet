package com.bench.training.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bench.training.exception.AppException;
import com.bench.training.exception.ErrorInfoFactory;

public class SimpleXmlHandler {
	protected final static DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
	
	protected Document	xmlDocument = null;
	protected String	xmlString	= null;
	
	public SimpleXmlHandler(String xmlString) throws AppException {
		if( (xmlString == null) || ("".equals(xmlString.trim())) ) {
			AppException ae = new AppException();
			ae.addInfo(
					ErrorInfoFactory.getIllegalInputParameterErrorInfo(
							"xmlString", xmlString, "a valid string must be provided for xmlString", "SimpleXmlHandler/SimpleXmlHandler()"));
			throw ae;
		}
		
		DocumentBuilder dBuilder;
		try {
			this.xmlString = xmlString;
			dBuilder 	= dbFactory.newDocumentBuilder();
			xmlDocument = dBuilder.parse(new InputSource(new ByteArrayInputStream(xmlString.getBytes("utf-8"))));
			xmlDocument.normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			AppException ae = new AppException();
			ae.addInfo(
					ErrorInfoFactory.getXmlParseErrorInfo(
							e, "error parsing xml string", "SimpleXmlHandler/SimpleXmlHandler()").setParameter("xmlString", this.xmlString));
			throw ae;
		}
	}
	
	protected Element getElementByNameAndIndex(String elementName, int index) throws AppException {
		Node tempNode = xmlDocument.getElementsByTagName(elementName).item(index);
		
		if(tempNode == null) {
			AppException ae = new AppException();
			ae.addInfo(
					ErrorInfoFactory.getXmlParseErrorInfo(
							null, elementName + " node not found @ index=" + index, 
							"SimpleXmlHandler/checkElementValidation()").setParameter("xmlString", xmlString));
			throw ae;
		} else if( !(tempNode instanceof Element) ) {
			AppException ae = new AppException();
			ae.addInfo(
					ErrorInfoFactory.getXmlParseErrorInfo(
							null, elementName + " node cannot be parsed @ index=" + index, 
							"SimpleXmlHandler/checkElementValidation()").setParameter("tempNode", tempNode).setParameter("xmlString", xmlString) );
			throw ae;
		}
		
		return (Element) tempNode;
	}
}
