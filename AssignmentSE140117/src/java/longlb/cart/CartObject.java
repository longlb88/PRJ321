/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.cart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Long Le
 */
public class CartObject implements Serializable{
    private String cartId;
    private Map<String, Integer> itemList;

    public Map<String, Integer> getItemList() {
	return itemList;
    }

    public String getCartId() {
	return cartId;
    }
    
    public void addBookToCart(String book){
	//1. Check existed itemList
	if (itemList == null){
	    itemList = new HashMap<>();
	}//end if itemList is not exist
	
	//2. Check existed item in itemList
	int quantity = 1;
	if (itemList.containsKey(book)){
	    quantity = itemList.get(book) + 1;
	}//end if itemList existed
	itemList.put(book, quantity);
    }
    
    public void removeBook(String book){
	if (itemList == null){
	    return;
	}//end if item list not existed
	
	if (itemList.containsKey(book)){
	    itemList.remove(book);
	} //end if item list contains book
	
	if (itemList.size() == 0){
	    itemList = null; //remove cart when no items
	} //end if item list emtpy
    }
    
    //generate id with format: ddMMyy-HHmmss-xxxxxx (x is random digit)
    public void generateCartId(){
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-HHmmss");
	Date currentDate = new Date();
	
	String datePortion = sdf.format(currentDate);
	String randomPortion = "";
	
	for (int i = 0; i < 6; i++){
	    int randomNumber = new Random().nextInt(10);
	    randomPortion += randomNumber;
	}
	
	cartId = datePortion + "-" + randomPortion;
    }
}
