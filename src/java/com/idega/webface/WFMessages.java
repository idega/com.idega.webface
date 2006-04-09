/*
 * $Id: WFMessages.java,v 1.2 2006/04/09 11:59:21 laddi Exp $
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
 * <p>
 * Container for JSF Messages.
 * </p>
 * Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 *
 * @author Anders Lindman, Tryggvi Larusson
 * @version $Revision: 1.2 $
 */
public class WFMessages extends WFContainer {
	
	public final static String STYLE_CLASS="wf_messages";
	
	public static final String ERROR_DEFAULT_STYLE_CLASS="error";
	public static final String INFO_DEFAULT_STYLE_CLASS="info";
	public static final String WARN_DEFAULT_STYLE_CLASS="warn";
	public static final String FATAL_DEFAULT_STYLE_CLASS="fatal";
	
	private String errorStyleClass=ERROR_DEFAULT_STYLE_CLASS;
	private String infoStyleClass=INFO_DEFAULT_STYLE_CLASS;
	private String warningStyleClass=WARN_DEFAULT_STYLE_CLASS;
	private String fatalStyleClass=FATAL_DEFAULT_STYLE_CLASS;
	
	/**
	 * Default contructor.
	 */
	public WFMessages() {
		setStyleClass(STYLE_CLASS);
	}

	/**
	 * Adds a message do be displayed.
	 * @param forId the id for the input component connected to the message.
	 */
	public void addMessageToDisplay(String forId) {
		addMessageToDisplay(forId,true,false);
	}
	
	/**
	 * Adds a message do be displayed.
	 * @param forId the id for the input component connected to the message.
	 */
	public void addMessageToDisplay(String forId,boolean showSummary,boolean showDetail) {
		HtmlMessage message = new HtmlMessage();
		message.setFor(forId);
		message.setShowSummary(showSummary);
		message.setShowDetail(showDetail);
		message.setInfoClass(getInfoStyleClass());
		message.setWarnClass(getWarningStyleClass());
		message.setErrorClass(getErrorStyleClass());
		message.setFatalClass(getFatalStyleClass());
		add(message);
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
	
	/**
	 * @see javax.faces.component.UIPanel#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[5];
		values[0] = super.saveState(ctx);
		values[1] = this.infoStyleClass;
		values[2] = this.warningStyleClass;
		values[3] = this.errorStyleClass;
		values[4] = this.fatalStyleClass;
		return values;
	}

	/**
	 * @see javax.faces.component.UIPanel#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		this.infoStyleClass = (String)values[1];
		this.warningStyleClass = (String)values[2];
		this.errorStyleClass = (String)values[3];
		this.fatalStyleClass = (String)values[4];
	}
	
	/**
	 * @return Returns the errorStyleClass.
	 */
	public String getErrorStyleClass() {
		return this.errorStyleClass;
	}

	
	/**
	 * @param errorStyleClass The errorStyleClass to set.
	 */
	public void setErrorStyleClass(String errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	
	/**
	 * @return Returns the fatalStyleClass.
	 */
	public String getFatalStyleClass() {
		return this.fatalStyleClass;
	}

	
	/**
	 * @param fatalStyleClass The fatalStyleClass to set.
	 */
	public void setFatalStyleClass(String fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	
	/**
	 * @return Returns the infoStyleClass.
	 */
	public String getInfoStyleClass() {
		return this.infoStyleClass;
	}

	
	/**
	 * @param infoStyleClass The infoStyleClass to set.
	 */
	public void setInfoStyleClass(String infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	
	/**
	 * @return Returns the warningStyleClass.
	 */
	public String getWarningStyleClass() {
		return this.warningStyleClass;
	}

	
	/**
	 * @param warningStyleClass The warningStyleClass to set.
	 */
	public void setWarningStyleClass(String warningStyleClass) {
		this.warningStyleClass = warningStyleClass;
	}
}
