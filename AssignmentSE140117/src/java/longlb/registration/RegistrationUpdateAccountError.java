/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.registration;

import java.io.Serializable;

/**
 *
 * @author Long Le
 */
public class RegistrationUpdateAccountError implements Serializable{
    private String passwordLengthError;
    private String fullnameLengthError;

    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
	return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
	this.passwordLengthError = passwordLengthError;
    }

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
    
    
    
}
