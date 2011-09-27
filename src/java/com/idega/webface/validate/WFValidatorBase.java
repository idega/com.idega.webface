package com.idega.webface.validate;

import java.util.Locale;

import javax.faces.application.FacesMessage;

import org.apache.myfaces.shared_tomahawk.util.MessageUtils;
import org.apache.myfaces.validator.ValidatorBase;

import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;

public abstract class WFValidatorBase extends ValidatorBase {
	

    private static final String DETAIL_SUFFIX = "_detail";
    public static final String BUNDLE_IDENTIFIER = "com.idega.webface";
    protected String bundleIdentifer;
    
    
	public String getBundleIdentifier() {
		if(bundleIdentifer == null){
			return BUNDLE_IDENTIFIER;
		} else {
			return bundleIdentifer;
		}
	}


	public void setBundleIdentifer(String bundleIdentifer) {
		this.bundleIdentifer = bundleIdentifer;
	}


	/**
     * @param defaultMessage The default message we would expect.
     * @param args Arguments for parsing this message.
     * @return FacesMessage
     */
    protected FacesMessage getFacesMessage(String defaultMessage, Object[] args) {
        FacesMessage msg;

        if (getSummaryMessage() == null && getDetailMessage() == null)
        {
            msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, defaultMessage, args);
            IWContext iwc = IWContext.getCurrentInstance();
            IWMainApplication iwma = IWMainApplication.getIWMainApplication(iwc);
            IWResourceBundle iwrb = iwma.getBundle(getBundleIdentifier()).getResourceBundle(iwc);
            String summaryText = iwrb.getLocalizedAndFormattedString(defaultMessage, defaultMessage, args);
            String detailText = iwrb.getLocalizedAndFormattedString(defaultMessage+DETAIL_SUFFIX, defaultMessage, args);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summaryText, detailText);
        } else {
            Locale locale = MessageUtils.getCurrentLocale();
            String summaryText = MessageUtils.substituteParams(locale, getSummaryMessage(), args);
            String detailText = MessageUtils.substituteParams(locale, getDetailMessage(), args);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summaryText, detailText);
        }
        return msg;
    }

}
