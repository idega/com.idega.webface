/*
 * $Id: WFPlainTextOutput.java,v 1.2 2004/06/07 07:52:20 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Text component for rendering plain unformat text.   
 * <p>
 * Last modified: $Date: 2004/06/07 07:52:20 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */

public class WFPlainTextOutput extends HtmlOutputText {

	/**
	 * Default constructor.
	 */
	public WFPlainTextOutput() {
		super();
	}
	
	/**
	 * Constructs an plain text output with the specified text.
	 */
	public WFPlainTextOutput(String text) {
		this();
		setValue(text);
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
	}
	
	/**
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.write((String) getValue());
	}
	
}
