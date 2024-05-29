package team2.uco.edu;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class GUI_2 {
	
			private static int PORT= 8006;
			private static String SERVER_IP = "localhost";
			
			private static Socket socket;
			private static ServerSocket listener;
			
		
			private static InputStream is;
			private static OutputStream os; 
			
			private static String patientID;
			private static String age;
			private static String menopause;
			private static String Tumor_size;
			private static String Inv_nodes;
			private static String Node_caps;
			private static String Deg_malig;
			private static String Breast;
			private static String Breast_quad;
			private static String Irradiat;
			
			private static String server_response;
			
			// Variable when requesting opinion from other servers
			private static String inputpatientID;
			private static String inputAge;
			private static String inputMenopause;
			private static String inputTumor_size;
			private static String inputInv_nodes;
			private static String inputNode_caps;
			private static String inputDeg_malig;
			private static String inputBreast;
			private static String inputBreast_quad;
			private static String inputIrradiat;
			private static String inputClass;
	
	
    public static void main(String[] args) throws IOException, InterruptedException {
               
        startup_GUI GUI = new startup_GUI();
        GUI.setVisible(true);
        
        while(true)
        	{
        	
        	System.out.println("");
        		       
        		// Handling code for requesting opinion from other servers System acting as Client 
	         if (GUI.request_clicked==true)
	         {
	        	//PORT = 8005;
        		GUI.jTextArea_Sys_Response.append("\nSystem:  Requesting opinion...\n");
        		GUI.jTextArea_Sys_Response.append("System:  Listening halted...\n");
	        	GUI.jTextArea_Sys_Response.append("System:  Processing Request...\n\n");
	        	
	        	
		        	inputpatientID = GUI.jTextField_Patient_Id.getText();
		        	inputAge = (String) GUI.jComboBox_Age.getSelectedItem();
		        	inputMenopause = (String) GUI.jComboBox_Menopause.getSelectedItem();
		        	inputTumor_size = (String) GUI.jComboBox_Tumor_Size.getSelectedItem();
		        	inputInv_nodes = (String) GUI.jComboBox_Inv_Nodes.getSelectedItem();
		        	inputNode_caps = (String) GUI.jComboBox_Node_Caps.getSelectedItem();
		        	inputDeg_malig = (String) GUI.jComboBox_Deg_Malig.getSelectedItem();
		        	inputBreast = (String) GUI.jComboBox_Breast.getSelectedItem();
		        	inputBreast_quad = (String) GUI.jComboBox_Breast_Quad.getSelectedItem();
		        	inputIrradiat = (String) GUI.jComboBox_Irradiat.getSelectedItem();
	        	
	        	support_GUI GUI_REQ = new support_GUI();
		        GUI_REQ.setVisible(true);
		        GUI_REQ.jScrollPane_Sys_Response.setBorder(javax.swing.BorderFactory.createTitledBorder("Request sent by System to Clients"));
				
		        GUI_REQ.jTextArea_Sys_Response.append("Awaiting for Connection...\n");
		        GUI_REQ.jTextField_Patient_Id.setText(inputpatientID);
				GUI_REQ.jTextField_Age.setText(inputAge);
				GUI_REQ.jTextField_Menopause.setText(inputMenopause);
				GUI_REQ.jTextField_Tumor_Size.setText(inputTumor_size);
				GUI_REQ.jTextField_Inv_Nodes.setText(inputInv_nodes);
				GUI_REQ.jTextField_Node_Caps.setText(inputNode_caps);
				GUI_REQ.jTextField_Deg_Malig.setText(inputDeg_malig);
				GUI_REQ.jTextField_Breast.setText(inputBreast);
				GUI_REQ.jTextField_Breast_Quad.setText(inputBreast_quad);
				GUI_REQ.jTextField_Irradiat.setText(inputIrradiat);
				Thread.sleep(2*1000);
				try {
					
					socket = new Socket(SERVER_IP,PORT);
					//socket.setSoTimeout(10*1000);
					GUI_REQ.jTextArea_Sys_Response.append("Connection obtained at at \n          IP:  "+"\n          PORT: " +PORT +"\n");
					GUI_REQ.jTextArea_Sys_Response.append("Creating XML file of request..\n");
					
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
					GUI_REQ.jTextArea_Sys_Response.append("File created...\n");
							File xmlFile = new File("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\server_request.xml");
							FileOutputStream outStream = new FileOutputStream(xmlFile);
						
							XMLSerializer serializer = new XMLSerializer(outStream,outFormat);
							serializer.serialize(xmlRequest);
							long request_size = xmlFile.length();
					GUI_REQ.jTextArea_Sys_Response.append("File size..."+ request_size+"\n");
							outStream.close();
				
					//	System.out.println("Client: Sending request..");
					GUI_REQ.jTextArea_Sys_Response.append("Reading created file...\n");
							byte []b = new byte[(int)request_size];
					
							FileInputStream fr = new FileInputStream("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\server_request.xml");
							fr.read(b,0,b.length);
							fr.close();
					GUI_REQ.jTextArea_Sys_Response.append("Sending created file...\n");
							OutputStream ser_os = socket.getOutputStream();
							InputStream ser_is = socket.getInputStream();
							ser_os.write(b,0,b.length);
					GUI_REQ.jTextArea_Sys_Response.append("Receiving response for the request...\n");		
							
					// Waits 10 seconds before getting response for given request
							Thread.sleep(10*1000);
					
					// Reading response and saving it to a file
					
					
							long response_size = ser_is.available();
					GUI_REQ.jTextArea_Sys_Response.append("File size..."+ response_size+"\n");
							byte []b2 = new byte[(int) response_size];
							ser_is.read(b2,0,b2.length);
					GUI_REQ.jTextArea_Sys_Response.append("Writing opinion file...\n");
							FileOutputStream fo = new FileOutputStream("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\server_response.xml");
							fo.write(b2,0,b2.length);
							fo.close();
							ser_os.close();
							ser_is.close();
				//	System.out.println("Client: Request Sent..");
					
					GUI_REQ.jTextArea_Sys_Response.append("Interpreting Response...\n");
					
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
						              inputClass = eElement.getElementsByTagName("opinion").item(0).getTextContent(); 
						              System.out.println("Opinion: " + 
						            		  	eElement.getElementsByTagName("opinion").item(0).getTextContent());
						           }
						        }
					GUI_REQ.jTextArea_Sys_Response.append("\n\nGiven event is a "+ inputClass +"\n\n");
					GUI_REQ.jTextArea_Sys_Response.append("Closing connection\n");
						socket.close();
						Thread.sleep(6*1000);
					GUI_REQ.setVisible(false);
		        
			} catch (Exception e)
				{
		        e.printStackTrace();
		        GUI_REQ.jTextArea_Sys_Response.append("No connections found...\n");
		        GUI_REQ.jTextArea_Sys_Response.append("Closing request...\n");
		        GUI_REQ.jTextArea_Sys_Response.append("Request Again...\n");
		        
					Thread.sleep(6*1000);
				
		        GUI_REQ.setVisible(false);
				}
	        	GUI.request_clicked = false;
	         }
        
	         	// Handling code for processing requests System acting as Server    
	        else  {
        		try {
        			      			
	        		listener = new ServerSocket(PORT);
	        		
	        		GUI.jTextArea_Sys_Response.setText("System:  Listening for Client...\n");
	        		
			        listener.setSoTimeout(4*1000);		        		        		             
			        Socket client_socket = listener.accept();
			        
			        // If client connected do this
			        System.out.println("Client Connected");
			        GUI.jTextArea_Sys_Response.append("System: Client connected at \n          IP:  "+"\n          PORT: " +PORT +"\n");
			        
			        GUI.jTextArea_Sys_Response.append("System: Processing client...\n");
		        
			        // New pop up screen
					support_GUI GUI_FOR_CLIENTS = new support_GUI();
			        GUI_FOR_CLIENTS.setVisible(true);
			        GUI_FOR_CLIENTS.jScrollPane_Sys_Response.setBorder(javax.swing.BorderFactory.createTitledBorder("Requests received by System from Clients"));
				
			        GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Receiving request from client...\n");
					 //   System.out.println("Server: Receiving request from Client..");
					
			        // Getting input and output stream on client socket
						is = client_socket.getInputStream();
						os = client_socket.getOutputStream();
					
					// Very important wait 2 sec before you can obtain the input stream
						Thread.sleep(2*1000);
						long in_size = is.available();
					GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Size of request file..." + in_size+"\n");
						
						byte []b = new byte[(int) in_size];
						is.read(b,0,b.length);
										
						FileOutputStream fo = new FileOutputStream("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\client_request.xml");
						fo.write(b,0,b.length);
					//	fo.close();
					//System.out.println("Length of client request file:"+ b.length);
					GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Request received from client...\n");
					GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Interpreting request...\n");
						
						File inputFile = new File("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\client_request.xml");
				        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				         
				         Document doc = dBuilder.parse(inputFile);
				         doc.getDocumentElement().normalize();
				         System.out.println("Element Type :" + doc.getDocumentElement().getNodeName());
				        // System.out.println("requestID :" + doc.getDocumentAttribute()
				         NodeList nList = doc.getElementsByTagName("patient");
				         System.out.println("----------------------------");
			         
			         for (int i = 0; i < nList.getLength(); i++) {
			            Node nNode = nList.item(i);
			            System.out.println("\nCurrent Element :" + nNode.getNodeName());
			            
			            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			               Element eElement = (Element) nNode;
			               System.out.println("Patient ID: " + eElement.getAttribute("patientID"));
			               	patientID = eElement.getAttribute("patientID");
			               
			               System.out.println("Age: " + 
			            		   eElement.getElementsByTagName("age").item(0).getTextContent());
			               	age = eElement.getElementsByTagName("age").item(0).getTextContent();
			               
			               System.out.println("Menopause: " +
			            		   eElement.getElementsByTagName("menopause").item(0).getTextContent());
			                menopause = eElement.getElementsByTagName("menopause").item(0).getTextContent();
			               
			               System.out.println("Tumor-size: " +
			            		   eElement.getElementsByTagName("tumor-size").item(0).getTextContent());
			                Tumor_size = eElement.getElementsByTagName("tumor-size").item(0).getTextContent();
			               
			               System.out.println("Inv-nodes: " +
			            		   eElement.getElementsByTagName("inv-nodes").item(0).getTextContent());
			                Inv_nodes = eElement.getElementsByTagName("inv-nodes").item(0).getTextContent();
			               
			               System.out.println("Node-caps: " +
			            		   eElement.getElementsByTagName("node-caps").item(0) .getTextContent());
			                Node_caps = eElement.getElementsByTagName("node-caps").item(0) .getTextContent();
			               
			 	           System.out.println("Deg-malig: " +
			 	        		   eElement.getElementsByTagName("deg-malig").item(0).getTextContent());
			 	            Deg_malig = eElement.getElementsByTagName("deg-malig").item(0).getTextContent();
			 	           
			 	           System.out.println("Breast: " +
			 	        		   eElement.getElementsByTagName("breast").item(0).getTextContent());
			 	            Breast = eElement.getElementsByTagName("breast").item(0).getTextContent();
			 	           
			 	           System.out.println("Breast-quad: " +
			 	        		   eElement.getElementsByTagName("breast-quad").item(0).getTextContent());
			 	            Breast_quad = eElement.getElementsByTagName("breast-quad").item(0).getTextContent();
			 	           
			 	           System.out.println("Irradiat: " +
			 	        		   eElement.getElementsByTagName("irradiat").item(0).getTextContent());
			 	            Irradiat = eElement.getElementsByTagName("irradiat").item(0).getTextContent();
			 	           
			 	           System.out.println("Class: " +
			 	        		   eElement.getElementsByTagName("Class").item(0).getTextContent());     
			 	                  
			            }
			         }
			         	GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Request interpreted...\n");
			         	GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Populating table...\n");
			         	GUI_FOR_CLIENTS.jTextField_Age.setText(age);
						GUI_FOR_CLIENTS.jTextField_Menopause.setText(menopause);
						GUI_FOR_CLIENTS.jTextField_Tumor_Size.setText(Tumor_size);
						GUI_FOR_CLIENTS.jTextField_Inv_Nodes.setText(Inv_nodes);
						GUI_FOR_CLIENTS.jTextField_Node_Caps.setText(Node_caps);
						GUI_FOR_CLIENTS.jTextField_Deg_Malig.setText(Deg_malig);
						GUI_FOR_CLIENTS.jTextField_Breast.setText(Breast);
						GUI_FOR_CLIENTS.jTextField_Breast_Quad.setText(Breast_quad);
						GUI_FOR_CLIENTS.jTextField_Irradiat.setText(Irradiat);
			         
			      
				// Running the test on the request case.
						GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Running test on request...\n");
				Tester response_test = new Tester(age, menopause, Tumor_size, Inv_nodes, Node_caps, Deg_malig, Breast, Breast_quad, Irradiat);
				
					server_response = response_test.test();
						GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("-----------------------"+"\n");
						GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Given event is a: " +server_response+"\n");
						GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("-----------------------"+"\n");
						
					System.out.println("The response for the given event is  " + server_response);
				
			    
			    	GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Replying client...\n");
			    	// Server response code goes here
			    	DocumentBuilderFactory docFactory1 = DocumentBuilderFactory.newInstance();
					
					DocumentBuilder docBuilder1 = docFactory1.newDocumentBuilder();
					
					Document xmlResponse = docBuilder1.newDocument();
					
					Element rootElement1 = xmlResponse.createElement("response");
						rootElement1.setAttribute("responseID","var0");
						rootElement1.setAttribute("requestID","var1");
						rootElement1.setAttribute("senderIPAddress","var2");
						rootElement1.setAttribute("receiverIPAddress","var3");
						
							
					Text patientIDText = xmlResponse.createTextNode(patientID);
					Element patient_ID = xmlResponse.createElement("patientID");
					patient_ID.appendChild(patientIDText);
					
					Text patientOpinionText = xmlResponse.createTextNode(server_response);
					Element patientOpinion = xmlResponse.createElement("opinion");
					patientOpinion.appendChild(patientOpinionText);
					
					rootElement1.appendChild(patient_ID);
					rootElement1.appendChild(patientOpinion);
					
					xmlResponse.appendChild(rootElement1);
					
					OutputFormat outFormat1 = new OutputFormat (xmlResponse);
					outFormat1.setIndent(1);
					
					File xmlFile1 = new File("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\response1.xml");
					FileOutputStream outStream1 = new FileOutputStream(xmlFile1);
					
					XMLSerializer serializer1 = new XMLSerializer(outStream1,outFormat1);
					serializer1.serialize(xmlResponse);
					long response_size = xmlFile1.length();
					outStream1.close();
					
				//	Sending the response file back to client
					GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Size of Response file written..."+response_size+"\n");
						byte []b1 = new byte[(int) response_size];
				
						FileInputStream fr = new FileInputStream("C:\\Users\\badal\\Desktop\\Software Eng I\\SE Project\\src\\response1.xml");
						fr.read(b1,0,b1.length);
						fr.close();
										
					
						os.write(b1,0,b1.length);
				
						System.out.println("Opinion sent to client");
					GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Opinion sent to client...\n");
					GUI_FOR_CLIENTS.jTextArea_Sys_Response.append("Closing connection to client...\n");
					
						is.close();
						os.close();
						client_socket.close();
						Thread.sleep(5*1000);
					GUI_FOR_CLIENTS.setVisible(false);
			
					
        		GUI.jTextArea_Sys_Response.append("System: Client Processed..."+"\n");
        		GUI.jTextArea_Sys_Response.append("-----------------------"+"\n");
		        GUI.jTextArea_Sys_Response.append("System: "+ server_response +"\n");
		        GUI.jTextArea_Sys_Response.append("-----------------------"+"\n");
		        GUI.jTextArea_Sys_Response.append("System: Opinion provided..."+"\n");
		        GUI.jTextArea_Sys_Response.append("System: Going back to listen...\n");
		        GUI.jTextArea_Sys_Response.append("System: Please wait...\n");
		        listener.close();
		        	Thread.sleep(7*1000);
        				} 
        		catch (SocketTimeoutException exception)
		        			{
				        	GUI.jTextArea_Sys_Response.append("System: Listening timeout...\n");     
				        	Thread.sleep(1*1000);
				        	listener.close();
		        			}
				catch (Exception e)
				        		{
				        			e.printStackTrace();
				        			listener.close();
				        		}      
		      	        
		        			}
		        	
		        }
    }
    }

