/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.cart;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import longlb.util.DBHelpers;

/**
 *
 * @author Long Le
 */
public class CartDAO implements Serializable {
    public boolean inserCart(String billId, String bookName, int quantity) 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelpers.makeConnection();
	    
	    if (con != null){
		//2.Create sql string
		String sql = "INSERT INTO Cart(billId, bookName, quantity) "
			+ "VALUES(?,?,?)";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, billId);
		stm.setString(2, bookName);
		stm.setInt(3, quantity);
		
		//4. Query
		int row = stm.executeUpdate();
		if (row > 0){
		    return true;
		}
	    }//end if con is not null
	} finally {
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return false;
    }
}
