package com.idega.webface.initializer;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.myfaces.context.servlet.ServletExternalContextImpl;

import com.idega.presentation.FacesContextInitializer;
import com.idega.util.CoreUtil;
import com.idega.webface.WFUtil;

public class FacesContextInitializerImpl implements FacesContextInitializer {

	private static final Logger LOGGER = Logger.getLogger(FacesContextInitializerImpl.class.getName());
	
	public ExternalContext getInitializedExternalContext(ServletContext context, ServletRequest request, ServletResponse response) {
		ExternalContext externalContext = null;
		
		try {
			FacesContext fc = getInitializedFacesContext(context, request, response);
			if (fc != null) {
				externalContext = fc.getExternalContext();
			}
			
			if (externalContext == null) {
				externalContext = new ServletExternalContextImpl(context, request, response);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error initializing " + ExternalContext.class.getSimpleName(), e);
			CoreUtil.sendExceptionNotification(e);
		}
		
		return externalContext;
	}

	public FacesContext getInitializedFacesContext(ServletContext context, ServletRequest request, ServletResponse response) {
		try {
			return WFUtil.createFacesContext(context, request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error initializing " + FacesContext.class.getSimpleName(), e);
			CoreUtil.sendExceptionNotification(e);
		}
		
		return null;
	}

}
