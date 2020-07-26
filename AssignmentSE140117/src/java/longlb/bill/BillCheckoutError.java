/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.bill;

import java.io.Serializable;

/**
 *
 * @author Long Le
 */
public class BillCheckoutError implements Serializable{
    private String fullnameLengthError;
    private String addressLengthError;

    /**
     * @return the fullnameLengthError
     */
    public String getFullnameLengthError() {
	return fullnameLengthError;
    }

    /**
     * @param fullnameLengthError the fullnameLengthError to set
     */
    public void setFullnameLengthError(String fullnameLengthError) {
	this.fullnameLengthError = fullnameLengthError;
    }

    /**
     * @return the addressIsEmtpy
     */
    public String getAddressLengthError() {
	return addressLengthError;
    }

    /**
     * @param addressLengthError the addressLengthError to set
     */
    public void setAddressLengthError(String addressLengthError) {
	this.addressLengthError = addressLengthError;
    }
    
    
}
