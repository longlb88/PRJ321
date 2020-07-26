/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.book;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longlb.util.DBHelpers;

/**
 *
 * @author Long Le
 */
public class BookDAO implements Serializable{
    private List<String> listBook;

    public List<String> getListProduct() {
	return listBook;
    }
    
    public void loadBookList() 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. make connection
	    con = DBHelpers.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "SELECT bookName "
			+ "FROM Book";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		
		//4.Query
		rs = stm.executeQuery();
		
		//5.Process data
		while (rs.next()){
		    String book = rs.getString("bookName");
		    
		    if (listBook == null){
			listBook = new ArrayList<>();
		    }
		    
		    listBook.add(book);
		}
	    }
	} finally {
	    if (rs != null){
		rs.close();
	    }
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
    }
}
