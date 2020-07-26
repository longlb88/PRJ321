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
public class RegistrationCreateAccountError implements Serializable{
    private String usernameLengthError;
    private String passwordLengthError;
    private String confirmNotMatch;
    private String fullnameLengthError;
    private String usernameExisted;

    /**
     * @return the usernameLengthError
     */
    public String getUsernameLengthError() {
	return usernameLengthError;
    }

    /**
     * @param usernameLengthError the usernameLengthError to set
     */
    public void setUsernameLengthError(String usernameLengthError) {
	this.usernameLengthError = usernameLengthError;
    }

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
     * @return the confirmNotMatch
     */
    public String getConfirmNotMatch() {
	return confirmNotMatch;
    }

    /**
     * @param confirmNotMatch the confirmNotMatch to set
     */
    public void setConfirmNotMatch(String confirmNotMatch) {
	this.confirmNotMatch = confirmNotMatch;
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

    /**
     * @return the usernameExisted
     */
    public String getUsernameExisted() {
	return usernameExisted;
    }

    /**
     * @param usernameExisted the usernameExisted to set
     */
    public void setUsernameExisted(String usernameExisted) {
	this.usernameExisted = usernameExisted;
    }
    
    
}
