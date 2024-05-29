package team2.uco.edu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class client {

				private static final String SERVER_IP = "localhost";
				private static final int SERVER_PORT = 8005;
				
				private static String inputpatientID = "ryadav1";
				
				private static String inputAge="50-59";
				private static String inputMenopause="premeno";
				private static String inputTumor_size="35-39";
				private static String inputInv_nodes="0-2";
				private static String inputNode_caps="no";
				private static String inputDeg_malig="3";
				private static String inputBreast="right";
				private static String inputBreast_quad="right_low";
				private static String inputIrradiat="yes";
				private static String inputClass="no";
		
		public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
				Socket socket = new Socket(SERVER_IP,SERVER_PORT);
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document xmlRequest = docBuilder.newDocument();
			
			Element rootElement = xmlRequest.createElement("request");
				rootElement.setAttribute("requestID","var1");
				rootElement.setAttribute("senderIPAddress","var2");
				rootElement.setAttribute("receiverIPAddress","var3");
				
			Element mainElement = xmlRequest.createElement("patient");
				mainElement.setAttribute("patientID", inputpatientID);
			
			Text patientAgeText = xmlRequest.createTextNode(inputAge);
			Element patientage = xmlRequest.createElement("age");
			patientage.appendChild(patientAgeText);
			
			Text patientMenopauseText = xmlRequest.createTextNode(inputMenopause);
			Element patientmenopause = xmlRequest.createElement("menopause");
			patientmenopause.appendChild(patientMenopauseText);
			
			Text patientTumor_sizeText = xmlRequest.createTextNode(inputTumor_size);
			Element patienttumor_size = xmlRequest.createElement("tumor-size");
			patienttumor_size.appendChild(patientTumor_sizeText);
			
			Text patientInv_nodesText = xmlRequest.createTextNode(inputInv_nodes);
			Element patientinv_nodes = xmlRequest.createElement("inv-nodes");
			patientinv_nodes.appendChild(patientInv_nodesText);
				
			Text patientNode_capsText = xmlRequest.createTextNode(inputNode_caps);
			Element patientnode_caps = xmlRequest.createElement("node-caps");
			patientnode_caps.appendChild(patientNode_capsText);
			
			Text patientDeg_maligText = xmlRequest.createTextNode(inputDeg_malig);
			Element patientdeg_malig = xmlRequest.createElement("deg-malig");
			patientdeg_malig.appendChild(patientDeg_maligText);
			
			Text patientBreastText = xmlRequest.createTextNode(inputBreast);
			Element patientbreast = xmlRequest.createElement("breast");
			patientbreast.appendChild(patientBreastText);
			
			Text patientBreast_quadText = xmlRequest.createTextNode(inputBreast_quad);
			Element patientbreast_quad = xmlRequest.createElement("breast-quad");
			patientbreast_quad.appendChild(patientBreast_quadText);
			
			Text patientIrradiatText = xmlRequest.createTextNode(inputIrradiat);
			Element patientirradiat = xmlRequest.createElement("irradiat");
			patientirradiat.appendChild(patientIrradiatText);
			
			Text patientClassText = xmlRequest.createTextNode(inputClass);
			Element patientclass = xmlRequest.createElement("Class");
			patientclass.appendChild(patientClassText);
			
			mainElement.appendChild(patientage);
			mainElement.appendChild(patientmenopause);
			mainElement.appendChild(patienttumor_size);
			mainElement.appendChild(patientinv_nodes);
			mainElement.appendChild(patientnode_caps);
			mainElement.appendChild(patientdeg_malig);
			mainElement.appendChild(patientbreast);
			mainElement.appendChild(patientbreast_quad);
			mainElement.appendChild(patientirradiat);
			mainElement.appendChild(patientclass);
			
			rootElement.appendChild(mainElement);
			xmlRequest.appendChild(rootElement);
			
			OutputFormat outFormat = new OutputFormat (xmlRequest);
			outFormat.setIndent(1);
			
			File xmlFile = new File("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\request.xml");
			FileOutputStream outStream = new FileOutputStream(xmlFile);
			
			XMLSerializer serializer = new XMLSerializer(outStream,outFormat);
			serializer.serialize(xmlRequest);
			long request_size = xmlFile.length();
			outStream.close();
		//	System.out.println("Client: Sending request..");
			System.out.println("Request Size:"+request_size);
			byte []b = new byte[(int)request_size];
			
			FileInputStream fr = new FileInputStream("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\request.xml");
			fr.read(b,0,b.length);
			fr.close();
			OutputStream os = socket.getOutputStream();
			os.write(b,0,b.length);
			
			// Waits 10 second to get the results
			
			Thread.sleep(10*1000);
			// Reading response and saving it to a file
			
			InputStream is = socket.getInputStream();
			long response_size = is.available();
			byte []b2 = new byte[(int) response_size];
			System.out.println("Server Response size:"+response_size);
			is.read(b2,0,b2.length);
			FileOutputStream fo = new FileOutputStream("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\server_response.xml");
			fo.write(b2,0,b2.length);
			fo.close();
		//	os.close();
		//	is.close();
		//	System.out.println("Client: Request Sent..");
			
			// Interpreting response
			File inputFile1 = new File("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\server_response.xml");
	         DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
	         
	         Document doc = dBuilder1.parse(inputFile1);
	         doc.getDocumentElement().normalize();
	         System.out.println("Element Type :" + doc.getDocumentElement().getNodeName());
	        // System.out.println("requestID :" + doc.getDocumentAttribute()
	         NodeList nList = doc.getElementsByTagName("response");
	         System.out.println("----------------------------");
        
        for (int i = 0; i < nList.getLength(); i++) {
           Node nNode = nList.item(i);
       //    System.out.println("\nCurrent Element :" + nNode.getNodeName());
           
           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;
              System.out.println("Patient ID: " +
              			eElement.getElementsByTagName("patientID").item(0).getTextContent());
              
              System.out.println("Opinion: " + 
            		  	eElement.getElementsByTagName("opinion").item(0).getTextContent());
           socket.close();
           }
        }

	}

}
