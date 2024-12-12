package Controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




@WebServlet("/ServletFich")
public class ServletFich extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletFich() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fact1 = request.getParameter("fact1") != null ? request.getParameter("fact1") : "";
		String fact2 = request.getParameter("fact2") != null ? request.getParameter("fact2") : "";
		String fact3 = request.getParameter("fact3") != null ? request.getParameter("fact3") : "";
		String fact4 = request.getParameter("fact4") != null ? request.getParameter("fact4") : "";
		String fact5 = request.getParameter("fact5") != null ? request.getParameter("fact5") : "";
		String fact6 = request.getParameter("fact6") != null ? request.getParameter("fact6") : "";

		String page = "";

		switch (request.getParameter("readingWriting")) {

		case "lectura":
			if (!fact1.isBlank() || !fact2.isBlank() || !fact3.isBlank() || !fact4.isBlank() || !fact5.isBlank()
					|| !fact6.isBlank()) {
				request.setAttribute("emptyError", "(*)Los campos no pueden contener ningun dato");
				page = "TratamientoFich.jsp";

			} else {
				switch (request.getParameter("fileTypes")) {

				case "XLS":
					lecturaXLS(request);
					break;

				case "CSV":
					break;

				case "JSON":
					break;

				case "XML":
					break;

				case "RDF":
					break;

				default:
					request.setAttribute("error", "Formato no soportado para lectura.");
					break;
				}
				page = "AccesoDatosA.jsp";
			}
			break;

		case "escritura":

			if (fact1.isBlank() || fact2.isBlank() || fact3.isBlank() || fact4.isBlank() || fact5.isBlank()
					|| fact6.isBlank()) {
				request.setAttribute("emptyError", "(*)Los campos no pueden estar vacios");
				page = "TratamientoFich.jsp";

			} else {
				switch (request.getParameter("fileTypes")) {

				case "XLS":
					escrituraXLS(fact1, fact2, fact3, fact4, fact5, fact6, request);
					break;

				case "CSV":
					break;

				case "JSON":
					break;

				case "XML":
					break;

				case "RDF":
					break;

				default:
					request.setAttribute("error", "Formato no soportado para escritura.");
					break;

				}
				page = "AccesoDatosA.jsp";
			}
			break;

		default:
			request.setAttribute("error", "Operación no válida.");
			page = "TratamientoFich.jsp";
			break;
		}

		request.getRequestDispatcher(page).forward(request, response);
	}

	private void lecturaXLS(HttpServletRequest request) {
        /**
         * Intento abrir y leer el archivo.
         */
        try (FileInputStream archivo = new FileInputStream("C:\\Users\\Eduardo\\Desktop\\ArchivoXLS.xls")) {
            /**
             * Creo un libro de trabajo (HSSFWorkbook) para utilizar el contenido del
             * archivo seleccionado en el FIS.
             */
            HSSFWorkbook libroDeTrabajo = new HSSFWorkbook(archivo);

            /**
             * Obtengo la primera hoja del archivo a modificar.
             */
            HSSFSheet hoja = libroDeTrabajo.getSheetAt(0);

            /**
             * Creo el objeto SB para crear una cadena para almacenar los datos leídos.
             */
            StringBuilder datos = new StringBuilder();

            /**
             * Recorro cada fila de la hoja.
             */
            for (Row fila : hoja) {
                /**
                 * Recorro cada celda de la fila.
                 */
                for (Cell celda : fila) {
                    /**
                     * Localizo el tipo de la celda para extraer el valor.
                     */
                    switch (celda.getCellType()) {
                        /**
                         * Si la celda contiene texto, se obtiene el texto de esa celda y lo añado al SB
                         * de datos, seguido de un espacio.
                         */
                        case STRING:
                            datos.append(celda.getStringCellValue()).append(" ");
                            break;

                        /**
                         * Si la celda contiene un número, se obtiene el valor numérico y lo añado al SB
                         * de datos, seguido de un espacio.
                         */
                        case NUMERIC:
                            datos.append(celda.getNumericCellValue()).append(" ");
                            break;
                        default:
                            datos.append("DESCONOCIDO ");
                            break;
                    }
                }
                /**
                 * Añado un salto de línea al final de cada fila, para que no aparezca todo
                 * junto (DATO1, DATO2, DATO3... ).
                 */
                datos.append("\n");
            }

            /**
             * Añado los datos leídos como atributo en la solicitud.
             */
            request.setAttribute("datosArchivo", datos.toString());
        } catch (IOException excepcion) {
            /**
             * En caso de error al leer el archivo, establece un mensaje de error como
             * atributo en la solicitud.
             */
            request.setAttribute("error", "Error al leer el archivo XLS: " + excepcion.getMessage());
        }
    }

	private void escrituraXLS(String dato1, String dato2, String dato3, String dato4, String dato5, String dato6,
			HttpServletRequest request) {
		/**
		 * Creo y escribe datos en un nuevo archivo XLS.
		 */
		try (HSSFWorkbook libroDeTrabajo = new HSSFWorkbook()) {
			/**
			 * Creo una nueva hoja en el archivo XLS.
			 */
			HSSFSheet hoja = libroDeTrabajo.createSheet("Datos");

			/**
			 * Creo la primera fila de la hoja.
			 */
			Row fila = hoja.createRow(0);

			/**
			 * Escribo los valores recibidos como parámetros en las celdas de la primera
			 * fila.
			 */
			fila.createCell(0).setCellValue(dato1);
			fila.createCell(1).setCellValue(dato2);
			fila.createCell(2).setCellValue(dato3);
			fila.createCell(3).setCellValue(dato4);
			fila.createCell(4).setCellValue(dato5);
			fila.createCell(5).setCellValue(dato6);

			/**
			 * Guardo el archivo.
			 */
			try (FileOutputStream salidaArchivo = new FileOutputStream(
					"C:\\Users\\Eduardo\\Desktop\\ArchivoXLS.xls")) {
				libroDeTrabajo.write(salidaArchivo);
			}
		} catch (IOException excepcion) {
			/**
			 * Si se produce un error al escribir el archivo, se muestra un mensaje de error
			 * como atributo en la solicitud.
			 */
			request.setAttribute("error", "Error al escribir el archivo XLS: " + excepcion.getMessage());
		}
	}
	
	private void LeerXML(){
try {
			
			// Guardo todos los datos en un Array bidimencional
		    ArrayList<ArrayList<String>> asr = new ArrayList<>();
		    File f = new File("src/main/resources/datos.xml"); 

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

		    // Imprimir los resultados
		    for (ArrayList<String> strs : asr) {
		        for (String string : strs) {
		            System.out.println(string);
		        }
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}


            
	}
	private void EscribirXML(){
		try {
			File f = new File("");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			
			Element nel = doc.createElement("");
			nel.setAttribute("lable", "");
			nel.setTextContent("");
			nel.appendChild(doc.createElement("").appendChild(doc.createElement("")));
			
			doc.getDocumentElement().appendChild(nel);
			
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
	
}
