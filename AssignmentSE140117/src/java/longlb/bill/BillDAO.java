/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.bill;

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
public class BillDAO implements Serializable{
    public boolean insertBill(String cartId, String fullname, String address) 
	    throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelpers.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "INSERT INTO Bill(billId, customerName, address) "
			+ "VALUES(?,?,?)";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, cartId);
		stm.setString(2, fullname);
		stm.setString(3, address);
		
		//4. Query
		int row = stm.executeUpdate();
		
		//5. Process data
		if (row > 0){
		    return true;
		}
	    }
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
