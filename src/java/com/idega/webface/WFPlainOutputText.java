/*
 * $Id: WFPlainOutputText.java,v 1.1 2004/06/18 14:10:14 anders Exp $
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
 * Text component for rendering plain unformatted text.   
 * <p>
 * Last modified: $Date: 2004/06/18 14:10:14 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.1 $
 */

public class WFPlainOutputText extends HtmlOutputText {

	/**
	 * Default constructor.
	 */
	public WFPlainOutputText() {
		super();
	}
	
	/**
	 * Constructs an plain text output with the specified text.
	 */
	public WFPlainOutputText(String text) {
		this();
		setValue(text);
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
		ResponseWriter out = context.getResponseWriter();
		out.write((String) getValue());
	}	
}
