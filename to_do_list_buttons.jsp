<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>First JSP</title>
</head>
   
<body>
<div id="title">
My To-Do List
</div>
<p>
<%@ page import="java.io.File,javax.xml.parsers.DocumentBuilder,javax.xml.parsers.DocumentBuilderFactory,org.w3c.dom.Document,org.w3c.dom.NodeList,org.w3c.dom.Node,org.w3c.dom.Element" %>

<%
File file = new File("C:\\Users\\sfisco\\Documents\\ApacheTomcat\\apache-tomcat-9.0.8\\webapps\\to-do_list2\\database.xml");
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document document = db.parse(file);
%>
<% NodeList tasklistNodeList = document.getElementsByTagName("Task"); //makes a node list of all task nodes
for (int i = 0; i<tasklistNodeList.getLength(); i++) {
	Node tNode = tasklistNodeList.item(i); //assigns first task node (take out trash) to variable
	Element task = (Element) tNode; //converts task node to an element
	String taskID = tNode.getAttributes().getNamedItem("id").getNodeValue();
	NodeList taskNodeList = task.getChildNodes(); //gets child nodes (name and complete) from task element
	for (int j = 0; j<taskNodeList.getLength(); j++) {
		if (taskNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
			//Element e = (Element) taskNodeList.item(j);
			Node textnode = taskNodeList.item(j);
			if (textnode.getNodeName() == "Name") {
			%>
			<p>ID: <%= taskID%>
			Task name: <%= textnode.getTextContent()%>
						
			<%
			} else {
				%>
				&ensp; Completion Status: <%= textnode.getTextContent()%>
				<form action="markTask" method="GET">
					<button type="submit">Status Change</button>
					<input type="hidden" name="id" value= "<%= taskID %>">
				</form>
				<form action="removeTask" method="GET">
					<button type="submit">Remove Task</button>
					<input type="hidden" name="id" value= "<%= taskID %>">
				</form>
				</p>
				<br>
				<br>
			<% }
	}
	}
	} %>
	
</p>
<p>
<form action="addTask" method="GET">
	<input type="text" name="name">
	<button type="submit">Add Task</button>
</form>
</p>
</body>
</html>