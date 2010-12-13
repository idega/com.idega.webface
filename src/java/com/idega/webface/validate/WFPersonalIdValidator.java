package com.idega.webface.validate;

import java.util.Locale;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.idega.presentation.IWContext;
import com.idega.util.StringUtil;

public class WFPersonalIdValidator extends WFValidatorBase {

	public static final String VALIDATOR_ID = "WFPersonalIdValidator";
	private static Locale ICELAND = new Locale("is","IS");
	private static final String SUCCESS = "SUCCESS";

    /**
     * <p>The message identifiers of the {@link FacesMessage} to be created if
     * the personalId is invalid.</p>
     */
    public static final String MESSAGE_ID_INVALID = "com.idega.webface.validate.PersonalId.INVALID";
    public static final String MESSAGE_ID_EMPTY_STRING = "com.idega.webface.validate.PersonalId.EMPTY_STRING";
    public static final String MESSAGE_ID_INCORRECT_LENGTH = "com.idega.webface.validate.PersonalId.INCORRECT_LENGTH";
    public static final String MESSAGE_ID_NOT_DIGITS = "com.idega.webface.validate.PersonalId.NOT_DIGITS";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (context == null) throw new NullPointerException("facesContext");
        if (component == null) throw new NullPointerException("uiComponent");

        if (value == null) {
         return;
        }
        
        IWContext iwc = IWContext.getIWContext(context);  
        Locale locale = iwc.getCurrentLocale();
        
        if (!ICELAND.equals(locale)){
        	Logger.getLogger(WFPersonalIdValidator.class.toString()).warning("Validator is only implemented for locale = is_IS, hence only checks if the given value is a valid icelandic personal id.");
        }
        
        String result = validateIcelandicSSN(value.toString());
        if(!SUCCESS.equals(result)){
        	Object[] args = {value.toString()};
            throw new ValidatorException(getFacesMessage(result, args));
        }
		
	}
	
	
	/**
	 * Validate the Icelandic SSN checksum.<br/>
	 * <small>Copied from com.idega.user.business.UserBusinessBean#validateIcelandicSSN(String ssn) and modified to change error handling</small>
	 */
	private String validateIcelandicSSN(String ssn) {
		if (StringUtil.isEmpty(ssn)) {
			return MESSAGE_ID_EMPTY_STRING;
		}
		
		int sum = 0;
		if (ssn.length() == 10) {
			try {
				sum = sum + Integer.parseInt(ssn.substring(0, 1)) * 3;
				sum = sum + Integer.parseInt(ssn.substring(1, 2)) * 2;
				sum = sum + Integer.parseInt(ssn.substring(2, 3)) * 7;
				sum = sum + Integer.parseInt(ssn.substring(3, 4)) * 6;
				sum = sum + Integer.parseInt(ssn.substring(4, 5)) * 5;
				sum = sum + Integer.parseInt(ssn.substring(5, 6)) * 4;
				sum = sum + Integer.parseInt(ssn.substring(6, 7)) * 3;
				sum = sum + Integer.parseInt(ssn.substring(7, 8)) * 2;
				sum = sum + Integer.parseInt(ssn.substring(8, 9)) * 1;
				sum = sum + Integer.parseInt(ssn.substring(9, 10)) * 0;
				if ((sum % 11) == 0) {
					return SUCCESS;
				} else {
//					LOGGER.warning(ssn + " is not a valid SSN. If fails validation test.");
					return MESSAGE_ID_INVALID;
				}
			} catch (NumberFormatException e) {
//				LOGGER.warning(ssn + " is not a valid SSN. It contains characters other than digits.");
				return MESSAGE_ID_NOT_DIGITS;
			}
		} else {
			return MESSAGE_ID_INCORRECT_LENGTH;
//			LOGGER.warning(ssn + " is not a valid SSN. It is not 10 characters.");
		}
	}
	
}
