package Controller;

import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class XMLParser {
  private final DocumentBuilder DOCUMENT_BUILDER;
  private String filePath = "doc/SpreadingFire.xml";

  public XMLParser() throws ParserConfigurationException {
    DOCUMENT_BUILDER = createDocumentBuilder();
  }


  private HashMap<String, String> parseXML(String filePath) throws IOException, SAXException {
    File XMLFile = new File(filePath);
    Document XMLDocument = DOCUMENT_BUILDER.parse(XMLFile);
    Element simulation = XMLDocument.getDocumentElement();
    HashMap<String, String> data = new HashMap<String, String>();
    for (String tag : GeneralController.TAGS){
      data.put(tag, simulation.getElementsByTagName(tag).item(0).getTextContent());
    }
    return data;
  }

  private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }

}