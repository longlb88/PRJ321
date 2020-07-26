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
import longlb.bill.BillCheckoutError;
import longlb.bill.BillDAO;
import longlb.cart.CartDAO;
import longlb.cart.CartObject;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "CheckoutCartServlet", urlPatterns = {"/CheckoutCartServlet"})
public class CheckoutCartServlet extends HttpServlet {

    private final String SUCCESS_PAGE = "checkoutSuccess";
    private final String CHECKOUT_PAGE = "confirmCart";

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

	String fullname = request.getParameter("txtFullname");
	String address = request.getParameter("txtAddress");
	
	String resource = CHECKOUT_PAGE;

	BillCheckoutError errors = new BillCheckoutError();
	boolean foundErr = false;

	try {
	    //check all user error
	    if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
		foundErr = true;
		errors.setFullnameLengthError("Full name must in range from 2 to 50 chars");
	    }
	    if (address.trim().length() < 5 || address.trim().length() > 100) {
		foundErr = true;
		errors.setAddressLengthError("Address must in range from 5 to 100 chars");
	    }

	    if (foundErr) {
		request.setAttribute("CHECKOUT_ERRORS", errors);
	    } else {
		HttpSession session = request.getSession(false);
		if (session != null) {
		    CartObject cart = (CartObject) session.getAttribute("CART");
		    if (cart != null) {
			cart.generateCartId();
			String cartId = cart.getCartId();

			//call DAO and write to Bill table
			BillDAO billDao = new BillDAO();
			boolean successWriteBill = billDao.insertBill(cartId, fullname, address);

			if (successWriteBill) {
			    Map<String, Integer> itemList = cart.getItemList();

			    //call DAO and write to Cart table
			    CartDAO cartDao = new CartDAO();
			    int successAdd = 0;
			    for (String book : itemList.keySet()) {
				boolean result = cartDao.inserCart(cartId, book, itemList.get(book));
				if (result) {
				    successAdd++;
				}
			    }

			    //delete cart when done
			    if (successAdd == itemList.size()) {
				session.setAttribute("CART", null);
				resource = SUCCESS_PAGE;
			    }
			}//end if successWriteBill
		    }//end if cart existed
		}//end if session existed
	    }//end else foundErr
	} catch (SQLException ex) {
	    log("CheckoutCartServlet _ SQLException: " + ex.getMessage());
	} catch (NamingException ex) {
	    log("CheckoutCartServlet _ NamingException: " + ex.getMessage());
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
