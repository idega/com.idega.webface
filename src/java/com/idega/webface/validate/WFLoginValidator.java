package com.idega.webface.validate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.idega.core.accesscontrol.business.LoginDBHandler;

public class WFLoginValidator extends WFValidatorBase {

	public static final String VALIDATOR_ID = "WFLoginValidator";

    /**
     * <p>The message identifier of the {@link FacesMessage} to be created if
     * the personalId is invalid.</p>
     */
    public static final String LOGIN_MESSAGE_ID_TAKEN = "com.idega.webface.validate.login.TAKEN";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (context == null) throw new NullPointerException("facesContext");
        if (component == null) throw new NullPointerException("uiComponent");

        if (value == null) {
         return;
        }
        
        if(LoginDBHandler.isLoginInUse(value.toString())){
        	Object[] args = {value.toString()};
            throw new ValidatorException(getFacesMessage(LOGIN_MESSAGE_ID_TAKEN, args));
        }
		
	}

	
	
}
