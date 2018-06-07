import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class markTask extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException {
	  try{
	  File xmlFile = new File("C:\\Users\\sfisco\\Documents\\ApacheTomcat\\apache-tomcat-9.0.8\\webapps\\to-do_list2\\database.xml");
	  FileWriter fw = new FileWriter("C:\\Users\\sfisco\\Documents\\test.txt");
	  PrintWriter pw = new PrintWriter(fw);
	  String identifier = "0";
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document document = db.parse(xmlFile);
		  NodeList tasklistNodeList = document.getElementsByTagName("Task");
		  for (int i =0; i<tasklistNodeList.getLength(); i++) {
			  Node tNode = tasklistNodeList.item(i);
			  Element task = (Element) tNode;
			  NodeList taskNodeList = task.getChildNodes();
			  if (tNode.getAttributes().getNamedItem("id").getNodeValue().equals(identifier)) {
				  for (int j = 0; j<taskNodeList.getLength(); j++) {
					  if (taskNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						  Node textnode = taskNodeList.item(j);
						  if (textnode.getNodeName().equals("Complete") && textnode.getTextContent().equals("Complete")) {
							pw.println("Task is complete. This program will set to Not Complete");
							textnode.setTextContent("Not Complete");
							pw.println("setting is now: " + textnode.getTextContent());
						  }
						  else if (textnode.getNodeName().equals("Complete") && textnode.getTextContent().equals("Not Complete")) {
							  pw.println("Task is not complete. This program will set to Complete");
								textnode.setTextContent("Complete");
						  }
					  }
				  }
			  }
		  }
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  Transformer transformer = transformerFactory.newTransformer();
		  DOMSource source = new DOMSource(document);
		  StreamResult result = new StreamResult(xmlFile);
		  transformer.transform(source, result);
		  pw.close();
		  response.setHeader("Refresh", "0; URL=http://localhost:9999/to-do_list2/to_do_list_buttons.jsp");
	  } catch(Exception exc) {
	  }
	  }
	
}
