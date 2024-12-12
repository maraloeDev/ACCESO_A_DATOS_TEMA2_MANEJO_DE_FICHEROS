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

	
}
