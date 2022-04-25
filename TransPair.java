/**
 * 
 */
package ro.ew.transp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author casian
 *
 */
public class TransPair {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//load input xml - with tagged strings
	//read out xml
	//read line and tag content
	//write replace to out with the line checking the correct line tag
	String attrName = "";

	System.out.print("Enter the first name: ");
	attrName = "password_updated";//scanner.nextLine();
	        
	try {
	    File inputFile = new File("it-IT.xml");
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(inputFile);
	    document.getDocumentElement().normalize();
	    NodeList nodeList = document.getElementsByTagName("string");
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Element element = (Element) nodeList.item(i);
	        attrName = element.getAttributeNode("name").getTextContent();
	        if(element.getAttributeNode("name").getTextContent().equals(attrName)){
	            System.out.println("Elem : "+element.getAttributeNode("name").getTextContent()+" "+
	            		element.getFirstChild().getTextContent()+" "+
	            		element.getAttributeNode("introduced").getTextContent());
	            //add the default language
	            String textToPut=element.getFirstChild().getTextContent();
	            //get a translation
	            textToPut = myTranslation(attrName);
	            if (null != textToPut){
	            	//put the translation text in place
	            	element.getFirstChild().setTextContent(textToPut);
	            }
	        }
	    }
	    DOMSource source = new DOMSource(document);
	    FileWriter writer = new FileWriter(new File("output.xml"));
	    StreamResult result = new StreamResult(writer);

	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.transform(source, result);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
}
	

	private static String myTranslation(String aStrAttr) throws FileNotFoundException {
		
		//load file and get the text after the firstChild name
		String keya = aStrAttr.toString();
		Scanner s = new Scanner(new File("RoTranslated.txt"));
		while(s.hasNextLine()){
		    //read the file line by line
		String nextLine = s.nextLine();
		            //check if the next line contains the key word
		    if(nextLine.contains(keya))
		    {
              //all text between > <
		    	 Pattern p = Pattern.compile("> <(.*?)> <");
		    	 Matcher m = p.matcher(nextLine);
		    	 if(m.find()) {
		        	String text = m.group(0);
		        	//comes also > <!!
		        	text = text.replaceAll("> <", "").trim();
		        	//System.out.println(text);
		        return text;
		        }
		    }
		}
		return null;
	}}
