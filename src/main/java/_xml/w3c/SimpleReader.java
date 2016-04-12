package _xml.w3c;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SimpleReader {

	public static String getValue(String fileName, String elementName) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(fileName);
			
			Element config = document.getDocumentElement();
			if(config != null) {
				NodeList nodes = config.getChildNodes();
				for(int i = 0; i < nodes.getLength(); i++) {
					if(nodes.item(i).getNodeName().equals(elementName)) {
						return nodes.item(i).getNodeValue();
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
