/*
 * $Id: WFErrorMessages.java,v 1.5 2005/03/09 14:40:41 eiki Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;

/**
 * Container for error messages.
 * <p>
 * Last modified: $Date: 2005/03/09 14:40:41 $ by $Author: eiki $
 *
 * @author Anders Lindman
 * @version $Revision: 1.5 $
 */
public class WFErrorMessages extends WFContainer {
	
	/**
	 * Default contructor.
	 */
	public WFErrorMessages() {
		setStyleClass("wf_errormessages");
	}

	/**
	 * Adds an error message.
	 * @param forId the id for the input component connected to the message.
	 */
	public void addErrorMessage(String forId) {
		HtmlMessage message = new HtmlMessage();
		message.setFor(forId);
		message.setShowSummary(true);
		message.setStyleClass("wf_errortext");
		add(message);
		add(WFUtil.getText(" "));
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		
		boolean hasMessages = false;

		Iterator childIter = getChildren().iterator();
		while (childIter.hasNext()) {
			UIComponent child = (UIComponent) childIter.next();
			if (child instanceof HtmlMessage) {
				HtmlMessage m = (HtmlMessage) child;
				UIComponent forComponent = m.findComponent(m.getFor());
				if(forComponent!=null){
					Iterator iter = context.getMessages(forComponent.getClientId(context));
					if (iter.hasNext()) {
						hasMessages = true;
					}				
				}
			}
		}
		
		if (hasMessages) {
			super.encodeBegin(context);
			super.encodeChildren(context);
			super.encodeEnd(context);
		}
	}
}
