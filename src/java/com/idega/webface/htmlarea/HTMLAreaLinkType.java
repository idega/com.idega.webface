/*
 * $Id: HTMLAreaLinkType.java,v 1.1 2005/03/08 10:39:39 gimmi Exp $
 * Created on 1.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;

import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import com.idega.idegaweb.IWBundle;


public interface HTMLAreaLinkType {

	/**
	 * Returns a String that the URL input should contain on the first load. 
	 */
	public String getStartingURL();
	
	/**
	 * Returns a String that the URL input should contain on the first load. 
	 */
	public String getStartingTitle();
	
	/**
	 * Returns a String that the URL input should contain on the first load. 
	 */
	public String getStartingTarget();
	
	/**
	 * Returns a valuebinding to be used on the HtmlOutputLink that link to
	 * the corresponding LinkTypeCreator.
	 * @param iwb
	 * @return
	 */
	public ValueBinding getLinkTypeName(IWBundle iwb);
	
	/**
	 * Returns a table 
	 * @return
	 */
	public UIComponent getLinkCreation();
	
	/**
	 * Returns a string with the type of the link... e.g. "email"
	 * @return
	 */
	public String getLinkType();
	
}
