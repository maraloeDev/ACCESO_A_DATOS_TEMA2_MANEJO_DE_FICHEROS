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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
		
		File f = new File("C:\\Users\\Eduardo\\Desktop\\ACCESO_A_DATOS_TEMA2_MANEJO_DE_FICHEROS\\Reto1Grupo\\DatosAbiertos\\centros.xls");
        /**
         * Intento abrir y leer el archivo.
         */
        try (FileInputStream archivo = new FileInputStream(f)) {
            /**
             * Creo un libro de trabajo (HSSFWorkbook) para utilizar el contenido del
             * archivo seleccionado en el FIS.
             */
            Workbook libroDeTrabajo = new HSSFWorkbook(archivo);

            /**
             * Obtengo la primera hoja del archivo a modificar.
             */
            Sheet hoja = libroDeTrabajo.getSheetAt(0);
            

            // * Creo el objeto SB para crear una cadena para almacenar los datos leídos.
             
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
            libroDeTrabajo.close();
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
			 * Creo la primera fila para la cabecera.
			 */
			Row filaCabecera = hoja.createRow(0);
			filaCabecera.createCell(0).setCellValue("DATO1");
			filaCabecera.createCell(1).setCellValue("DATO2");
			filaCabecera.createCell(2).setCellValue("DATO3");
			filaCabecera.createCell(3).setCellValue("DATO4");
			filaCabecera.createCell(4).setCellValue("DATO5");
			filaCabecera.createCell(5).setCellValue("DATO6");

			/**
			 * Creo la segunda fila para los datos.
			 */
			Row filaDatos = hoja.createRow(1);
			filaDatos.createCell(0).setCellValue(dato1);
			filaDatos.createCell(1).setCellValue(dato2);
			filaDatos.createCell(2).setCellValue(dato3);
			filaDatos.createCell(3).setCellValue(dato4);
			filaDatos.createCell(4).setCellValue(dato5);
			filaDatos.createCell(5).setCellValue(dato6);

			/**
			 * Guardo el archivo.
			 */
			try (FileOutputStream salidaArchivo = new FileOutputStream(
					"C:\\Users\\Eduardo\\Desktop\\ACCESO_A_DATOS_TEMA2_MANEJO_DE_FICHEROS\\Reto1Grupo\\DatosAbiertos\\centros.xls")) {
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
}
