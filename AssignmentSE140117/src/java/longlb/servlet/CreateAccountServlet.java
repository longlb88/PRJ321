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
import longlb.registration.RegistrationCreateAccountError;
import longlb.registration.RegistrationDAO;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    private final String CREATE_ERROR_PAGE = "registerErrorPage";
    private final String LOGIN_PAGE = "loginPage";
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
	String confirm = request.getParameter("txtConfirm");
	String fullname = request.getParameter("txtFullname");

	String resource = CREATE_ERROR_PAGE;
		
	RegistrationCreateAccountError errors = new RegistrationCreateAccountError();
	boolean foundErr = false;

	try {
	    //check all user error
	    if (username.trim().length() < 6 || username.trim().length() > 20) {
		foundErr = true;
		errors.setUsernameLengthError("Username must in range from 6 to 20 chars");
	    }
	    if (password.trim().length() < 6 || password.trim().length() > 30){
		foundErr = true;
		errors.setPasswordLengthError("Password must in range from 6 to 30 chars");
	    } else if (!confirm.trim().equals(password.trim())){
		foundErr = true;
		errors.setConfirmNotMatch("Confirm must match password");
	    }
	    if (fullname.trim().length() < 2 || fullname.trim().length() > 50){
		foundErr = true;
		errors.setFullnameLengthError("Full name must in range from 2 to 50 chars");
	    }
	    
	    if (foundErr){
		request.setAttribute("CREATE_ERRORS", errors);
	    } else {
		//call DAO
		RegistrationDAO dao = new RegistrationDAO();
		boolean result = dao.createAccount(username, password, fullname, false);
		
		if (result){
		    resource = LOGIN_PAGE;
		}
	    }
	} catch (NamingException ex) {
	    log("CreateAccountServlet _ NamingException: " + ex.getMessage());
	} catch (SQLException ex) {
	    log("CreateAccountServlet _ SQLException: " + ex.getMessage());
	    
	    if (ex.getMessage().contains("duplicate")){
		errors.setUsernameExisted(username + " EXISTED!!!");
		request.setAttribute("CREATE_ERRORS", errors);
	    }
	} finally {
	    //get url
	    ServletContext context = request.getServletContext();
	    Map<String, String> functionMap = (Map<String, String>) context.getAttribute("FUNCTION_MAP");
	    String url = functionMap.get(resource);
		    
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
