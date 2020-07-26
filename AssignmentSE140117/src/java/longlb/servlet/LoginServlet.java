/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longlb.registration.RegistrationDAO;
import longlb.registration.RegistrationDTO;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final String INVALID_PAGE = "invalid";
    private final String SEARCH_PAGE = "searchPage";
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
	String url = INVALID_PAGE;
	
	try {
	    //Call DAO
	    RegistrationDAO dao = new RegistrationDAO();
	    RegistrationDTO dto = dao.checkLogin(username, password);

	    if (dto != null) {
		Cookie usernameCookie = new Cookie("USERNAME", username);
		usernameCookie.setMaxAge(60 * 3);
		response.addCookie(usernameCookie);
		
		Cookie passwordCookie = new Cookie("PASSWORD", password);
		passwordCookie.setMaxAge(60 * 3);
		response.addCookie(passwordCookie);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("ACCOUNT", dto);
		
		url = SEARCH_PAGE;
	    }//end if dto exist
	} catch (SQLException ex) {
	    log("LoginServlet _ SQLException: " + ex.getMessage());
	} catch (NamingException ex) {
	    log("LoginServlet _ NamingException: " + ex.getMessage());
	} finally {
	    response.sendRedirect(url);
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
