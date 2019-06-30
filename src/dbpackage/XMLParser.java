package dbpackage;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

public class XMLParser {
    String getQueryFromXML (String xmlFileName) {
        String name_book = "", autor_book  = "", price_book  = "";
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFileName);
            XPath xpath = XPathFactory.newInstance().newXPath();
            name_book = (String) xpath.compile("//table//books//name_book").evaluate(document, XPathConstants.STRING);
            autor_book = (String) xpath.compile("//table//books//autor_book").evaluate(document, XPathConstants.STRING);
            price_book = (String) xpath.compile("//table//books//price_book").evaluate(document, XPathConstants.STRING);
        }
        catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException e){
            e.printStackTrace();
        }

        return "insert into BOOKS (NAME_BOOK, AUTOR_BOOK, PRICE_BOOK) values ('" + name_book +"', '" + autor_book + "', " + price_book + ")";
    }

    ConnectionManager getConfigFromXML (String xmlFileName) {
        String encoding = "", driver = "", username = "", password = "";
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFileName);
            XPath xpath = XPathFactory.newInstance().newXPath();
            encoding = (String) xpath.compile("//config//jdbc//encoding").evaluate(document, XPathConstants.STRING);
            driver = (String) xpath.compile("//config//jdbc//driver").evaluate(document, XPathConstants.STRING);
            username = (String) xpath.compile("//config//jdbc//username").evaluate(document, XPathConstants.STRING);
            password = (String) xpath.compile("//config//jdbc//password").evaluate(document, XPathConstants.STRING);
        }
        catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException e){
            e.printStackTrace();
        }

        return new ConnectionManager(encoding, driver, username, password);
    }



}
