/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longlb.registration.RegistrationDAO;
import longlb.registration.RegistrationDTO;
import longlb.registration.RegistrationUpdateAccountError;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();

	String username = request.getParameter("txtUsername");
	String password = request.getParameter("txtPassword");
	String fullname = request.getParameter("txtFullname");
	String isAdmin = request.getParameter("chkAdmin");
	boolean role = false;
	if (isAdmin != null) {
	    role = true;
	}

	RegistrationUpdateAccountError errors = new RegistrationUpdateAccountError();
	boolean foundErr = false;

	try {
	    if (password.trim().length() < 6 || password.trim().length() > 30) {
		foundErr = true;
		errors.setPasswordLengthError("Password must in range form 6 to 30 chars");
	    }
	    if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
		foundErr = true;
		errors.setFullnameLengthError("Full name must in range from 2 to 50 chars");
	    }

	    if (foundErr) {
		request.setAttribute("UPDATED_USER", username);
		request.setAttribute("UPDATE_ERRORS", errors);
	    } else {
		//Call DAO
		RegistrationDAO dao = new RegistrationDAO();
		boolean result = dao.updateAccount(username, password, fullname, role);

		if (result) {
		    //check if updated user is current user
		    HttpSession session = request.getSession(false);
		    if (session != null) {
			RegistrationDTO currentAccount = (RegistrationDTO) session.getAttribute("ACCOUNT");
			if (username.equals(currentAccount.getUsername())) {
			    currentAccount.setFullname(fullname); //update fullname
			    session.setAttribute("ACCOUNT", currentAccount);
			}//end if username
		    }//end if session is not null
		}//end if result
	    }

	} catch (NamingException ex) {
	    log("UpdateAccountServlet _ NamingException: " + ex.getMessage());
	} catch (SQLException ex) {
	    log("UpdateAccountServlet _ SQlException: " + ex.getMessage());
	} finally {
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
	    String url = functionMap.get("search");

	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	    out.close();
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
