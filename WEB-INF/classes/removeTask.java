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

public class removeTask extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException {
	  try{
	  String identifier = (String)request.getParameter("id");
	  File xmlFile = new File("C:\\Users\\sfisco\\Documents\\ApacheTomcat\\apache-tomcat-9.0.8\\webapps\\to-do_list2\\database.xml");
	  FileWriter fw = new FileWriter("C:\\Users\\sfisco\\Documents\\test.txt");
	  PrintWriter pw = new PrintWriter(fw);
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document document = db.parse(xmlFile);
		  NodeList tasklistNodeList = document.getElementsByTagName("Task");
		  for (int i =0; i<tasklistNodeList.getLength(); i++) {
			  Node tNode = tasklistNodeList.item(i);
			  if (tNode.getAttributes().getNamedItem("id").getNodeValue().equals(identifier)) {
				tNode.getParentNode().removeChild(tNode);  
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
