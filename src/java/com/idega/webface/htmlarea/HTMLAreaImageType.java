/*
 * $Id: HTMLAreaImageType.java,v 1.2 2006/12/05 15:27:29 gimmi Exp $
 * Created on 8.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import com.idega.idegaweb.IWBundle;


public interface HTMLAreaImageType {
	/**
	 * Returns a valuebinding to be used on the HtmlOutputLink that link to
	 * the corresponding LinkTypeCreator.
	 * @param iwb
	 * @return
	 */
	public ValueExpression getLinkTypeName(IWBundle iwb);
	
	/**
	 * Returns a table 
	 * @return
	 */
	public UIComponent getCreationComponent(String url);
	
	/**
	 * Returns a string with the type of the link... e.g. "email"
	 * @return
	 */
	public String getLinkType();
}
