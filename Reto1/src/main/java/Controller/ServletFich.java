package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ServletFich
 */
@WebServlet("/ServletFich")
public class ServletFich extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ruta = "DatosAbiertos/datos";

    /**
     * Default constructor. 
     */
    public ServletFich() {
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fact1 = request.getParameter("fact1") != null ? request.getParameter("fact1") : "";
		String fact2 = request.getParameter("fact2") != null ? request.getParameter("fact2") : "";
		String fact3 = request.getParameter("fact3") != null ? request.getParameter("fact3") : "";
		String fact4 = request.getParameter("fact4") != null ? request.getParameter("fact4") : "";
		String fact5 = request.getParameter("fact5") != null ? request.getParameter("fact5") : "";
		String fact6 = request.getParameter("fact6") != null ? request.getParameter("fact6") : "";
		
		String page = "";
		String eleccion = request.getParameter("fileTypes");
		
		switch(eleccion) {
		case "lectura":
			if(!fact1.isBlank() || !fact2.isBlank() || !fact3.isBlank() || !fact4.isBlank() || !fact5.isBlank() || !fact6.isBlank()) {
				request.setAttribute("emptyError", "(*)Los campos no pueden contener ningun dato");
				page = "TratamientoFich.jsp";
			}else {
				switch(request.getParameter("fileTypes")) {
				case "XLS":
					readXLS(request);
					break;
					
				case "CSV":
					readCSV(request);
				    break;
					
				case "JSON":
					readJSON(response);
					break;
					
				case "XML":
					readXML(request);
					break;
					
				case "RDF":
					readRDF(request);
					break;
					
				}
				page = "AccesoDatosA.jsp";
			}
			break;
			
		case "escritura":
			if(fact1.isBlank() || fact2.isBlank() || fact3.isBlank() || fact4.isBlank() || fact5.isBlank() || fact6.isBlank()) {
				request.setAttribute("emptyError", "(*)Los campos no pueden estar vacios");
				page = "TratamientoFich.jsp";
			}else {
				switch(request.getParameter("fileTypes")) {
				case "XLS":
					writeXLS(fact1, fact2, fact3, fact4, fact5, fact6, request);
					break;
					
				case "CSV":
					writeCSV(fact1, fact2, fact3, fact4, fact5, fact6);
					break;
					
				case "JSON":
					writeJSON(request, response);
					break;
					
				case "XML":
					writeXML(fact1, fact2, fact3, fact4, fact5, fact6);
					break;
					
				case "RDF":
					writeRDF(fact1, fact2, fact3, fact4, fact5, fact6);
					break;
					
				}
				page = "AccesoDatosA.jsp";
			}
			break;
			
		}
		request.setAttribute("eleccion", eleccion);
		request.getRequestDispatcher(page).forward(request, response);
	}
	
		private void readRDF(HttpServletRequest request) throws FileNotFoundException, IOException {
			
			File file = new File(ruta + ".rdf");
	        List<String[]> dataList = new ArrayList<>();

	        Model model = ModelFactory.createDefaultModel();

	        RDFDataMgr.read(model, file.getAbsolutePath(), Lang.NTRIPLES);

	        for (Statement stmt : model.listStatements().toList()) {
	            String[] data = new String[3];
	            data[0] = stmt.getSubject().toString();
	            data[1] = stmt.getPredicate().toString();
	            data[2] = stmt.getObject().toString();
	            dataList.add(data);
	        }

	        request.setAttribute("dataList", dataList);
	        
		}
		
		private void writeRDF(String fact1, String fact2, String fact3, String fact4, String fact5, String fact6) {

			File file = new File(ruta + ".rdf");
			
		    Model model = ModelFactory.createDefaultModel();

		    if (file.exists()) {
		        RDFDataMgr.read(model, file.getAbsolutePath(), Lang.NTRIPLES);
		    }

		    Resource newResource = model.createResource("http://example.org/resource/" + fact1);

		    Property prop1 = ResourceFactory.createProperty("http://example.org/property/fact1");
		    Property prop2 = ResourceFactory.createProperty("http://example.org/property/fact2");
		    Property prop3 = ResourceFactory.createProperty("http://example.org/property/fact3");
		    Property prop4 = ResourceFactory.createProperty("http://example.org/property/fact4");
		    Property prop5 = ResourceFactory.createProperty("http://example.org/property/fact5");
		    Property prop6 = ResourceFactory.createProperty("http://example.org/property/fact6");

		    newResource.addProperty(prop1, fact1);
		    newResource.addProperty(prop2, fact2);
		    newResource.addProperty(prop3, fact3);
		    newResource.addProperty(prop4, fact4);
		    newResource.addProperty(prop5, fact5);
		    newResource.addProperty(prop6, fact6);

		    try {
		        RDFDataMgr.write(new FileOutputStream(file), model, Lang.NTRIPLES);
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }

		}
		
		private void readCSV(HttpServletRequest request) {
			
			File file = new File(ruta + ".csv");
		    System.out.println("Ruta absoluta: " + file.getAbsolutePath());
		    System.out.println("Archivo existe: " + file.exists());
			
			List<String[]> data = new ArrayList<>();

	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String line;

	            while ((line = br.readLine()) != null) {
	                String[] row = line.split(",");
	                data.add(row);
	            }
	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        request.setAttribute("data", data);
	        
		}
		
		private void writeCSV(String fact1, String fact2, String fact3, String fact4, String fact5, String fact6) {

			File file = new File(ruta + ".csv");

			try (FileWriter writer = new FileWriter(file, true)) {
				writer.append(fact1);
				writer.append(",");
				writer.append(fact2);
				writer.append(",");
				writer.append(fact3);
				writer.append(",");
				writer.append(fact4);
				writer.append(",");
				writer.append(fact5);
				writer.append(",");
				writer.append(fact6);
				writer.append("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		private void readXLS(HttpServletRequest request) {
	        try (FileInputStream archivo = new FileInputStream(ruta + ".xlsx")) {
	        	XSSFWorkbook libroDeTrabajo = new XSSFWorkbook(archivo);
	        	XSSFSheet hoja = libroDeTrabajo.getSheetAt(0);
	            List<List<String>> datos = new ArrayList<>();
	            
	            for (Row fila : hoja) {
	            	List<String> filaDatos = new ArrayList<>();
	                for (Cell celda : fila) {
	                    switch (celda.getCellType()) {
	                        case STRING:
	                        	filaDatos.add(celda.getStringCellValue());
	                            break;

	                        case NUMERIC:
	                        	filaDatos.add(String.valueOf(celda.getNumericCellValue()));
	                            break;
	                            
	                        default:
	                        	filaDatos.add("DESCONOCIDO ");
	                            break;
	                    }
	                }
	                
	                datos.add(filaDatos);
	            }

	            request.setAttribute("datosArchivo", datos);
	        } catch (IOException excepcion) {
	        	
	            request.setAttribute("error", "Error al leer el archivo XLS: " + excepcion.getMessage());
	        }
	    }

		private void writeXLS(String dato1, String dato2, String dato3, String dato4, String dato5, String dato6, HttpServletRequest request) {
			try (FileInputStream archivo = new FileInputStream(ruta + ".xlsx");
			         XSSFWorkbook libroDeTrabajo = new XSSFWorkbook(archivo)) {
			        
			        XSSFSheet hoja = libroDeTrabajo.getSheetAt(0);
			        int ultimaFila = hoja.getLastRowNum();
					Row fila = hoja.createRow(ultimaFila + 1);

			        fila.createCell(0).setCellValue(dato1);
			        fila.createCell(1).setCellValue(dato2);
			        fila.createCell(2).setCellValue(dato3);
			        fila.createCell(3).setCellValue(dato4);
			        fila.createCell(4).setCellValue(dato5);
			        fila.createCell(5).setCellValue(dato6);

			        try (FileOutputStream salidaArchivo = new FileOutputStream(ruta + ".xlsx")) {
			            libroDeTrabajo.write(salidaArchivo);
			        }
			    } catch (IOException excepcion) {
			        request.setAttribute("error", "Error al escribir el archivo XLS: " + excepcion.getMessage());
			    }
		}
		
		private void readXML(HttpServletRequest request){
		    ArrayList<ArrayList<String>> asr = new ArrayList<>();
		    
				try {
			    File f = new File(ruta + ".xml"); 

			    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			    Document doc = dBuilder.parse(f);

			    doc.getDocumentElement().normalize();

			    NodeList nodel = doc.getElementsByTagName("EAD:c");
			    
			    asr.add(new ArrayList<String>() {{ add("unittitle");add("creationPeriod");add("author");add("status");
			    									add("subject");add("accessRestrictionsPeriod");add("usagePermission");add("usagePermissionExpiringDate");
			    									add("deathAuthor");add("Dokument:unittitle");add("dao");add("genreform");}});

			    for (int i = 0; i < nodel.getLength(); i++) {
			        ArrayList<String> as = new ArrayList<>();
			        Node n = nodel.item(i);

			        if (n.getNodeType() == Node.ELEMENT_NODE) {
			            Element elemento = (Element) n;

			            NodeList did = elemento.getElementsByTagName("EAD:did");
			            if (did.getLength() > 0) {
			            	as.add(did.item(0).getTextContent().substring(0,16));
			                as.add(did.item(0).getTextContent().substring(16));
			            }
			            NodeList controlAccess = elemento.getElementsByTagName("EAD:controlaccess");
			            if (controlAccess.getLength() > 0) {
			            	NodeList ast = (NodeList) controlAccess.item(0);
			            	for (int j = 0; j < ast.getLength(); j++) {
								as.add(ast.item(j).getTextContent());
							}
			            }
			            
			            NodeList accessRestrictList = elemento.getElementsByTagName("EAD:accessrestrict");
			            if (accessRestrictList.getLength() > 0) {
			            	for (int j = 0; j < accessRestrictList.getLength(); j++) {
								as.add(accessRestrictList.item(j).getTextContent());
							}
			            }

			            NodeList bioghistList = elemento.getElementsByTagName("EAD:bioghist");
			            if (bioghistList.getLength() > 0) {
			                as.add(bioghistList.item(0).getTextContent());
			            }
			            
			            
			            asr.add(as);
			        }
			    }

			} catch (Exception e) {
			    e.printStackTrace();
			}
				request.setAttribute("data", asr);
		}
		private void writeXML(String dato1, String dato2, String dato3, String dato4, String dato5, String dato6){
			try {
				ArrayList<String> sstr = new ArrayList<String>() {{add(dato1);add(dato2);add(dato3);add(dato4);add(dato5);add(dato6);}};
				File f = new File(ruta + ".xml");
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(f);
				
				for (int i = 0; i < sstr.size(); i++) {
					Element nel1 = doc.createElement("DataAd");
					nel1.setAttribute("lable", "");
					nel1.setTextContent(sstr.get(i));
					doc.getDocumentElement().appendChild(nel1);
				}
				
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer t = tf.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult sr = new StreamResult(f);
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.transform(source, sr);
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		private void readJSON(HttpServletResponse response) throws IOException {
	        File file = new File(ruta + ".json");
	        if (!file.exists()) {
	            response.getWriter().println("El archivo JSON no existe.");
	            return;
	        }

	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            StringBuilder jsonContent = new StringBuilder();
	            String line;
	            while ((line = br.readLine()) != null) {
	                jsonContent.append(line);
	            }

	            response.setContentType("application/json");
	            response.getWriter().println(jsonContent.toString());
	        }
	    }

	    private void writeJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("dato1", request.getParameter("fact1"));
	        jsonObject.addProperty("dato2", request.getParameter("fact2"));
	        jsonObject.addProperty("dato3", request.getParameter("fact3"));
	        jsonObject.addProperty("dato4", request.getParameter("fact4"));
	        jsonObject.addProperty("dato5", request.getParameter("fact5"));
	        jsonObject.addProperty("dato6", request.getParameter("fact6"));

	        try (FileWriter writer = new FileWriter(ruta + ".json")) {
	            Gson gson = new Gson();
	            gson.toJson(jsonObject, writer);
	        }

	        response.getWriter().println("Archivo JSON escrito con éxito.");
	    }

}
