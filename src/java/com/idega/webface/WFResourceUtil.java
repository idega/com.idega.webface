/*
 * $Id: WFResourceUtil.java,v 1.2 2006/04/09 11:59:21 laddi Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.ActionListener;


/**
 * 
 * Last modified: $Date: 2006/04/09 11:59:21 $ by $Author: laddi $
 *
 * @author Joakim Johnson
 * @version $Revision: 1.2 $
 */
public class WFResourceUtil {
	private static final String ARTICLE_BUNDLE = "com.idega.block.article";
	private static final String CONTENT_BUNDLE = "com.idega.content";
	
	private String bundle = "com.idega.block.article";

	private WFResourceUtil(String s) {
		this.bundle = s;
	}
	
	public static WFResourceUtil getResourceUtilArticle() {
		return new WFResourceUtil(ARTICLE_BUNDLE);
	}

	public static WFResourceUtil getResourceUtilContent() {
		return new WFResourceUtil(CONTENT_BUNDLE);
	}

	public String getBundleString() {
		return this.bundle;
	}

	/**
	 * Returns a localized HtmlOutputText as a header
	 */
	public HtmlOutputText getHeaderTextVB(String localizationKey) {
		return WFUtil.getHeaderTextVB(this.bundle, localizationKey);
	}

	/**
	 * Returns a localized HtmlOutputText
	 */
	public HtmlOutputText getTextVB(String localizationKey) {
		return WFUtil.getTextVB(this.bundle, localizationKey);
	}

	/**
	 * Returns an html list text with value binding.
	 */
	public HtmlOutputText getListTextVB(String localizationKey) {
		return WFUtil.getListTextVB(this.bundle, localizationKey);
	}

	/**
	 * Returns a localized HtmlCommand Button
	 */
	public HtmlCommandButton getButtonVB(String id, String localizationKey, ActionListener actionListener) {
		return WFUtil.getButtonVB(id, this.bundle, localizationKey, actionListener);
	}
	
	/**
	 * Adds a UIParameter with value binding to the specified component. 
	 */
	public void addParameterVB(UIComponent component, String name, String localizationKey) {
		WFUtil.addParameterVB(component, name, this.bundle, localizationKey);
	}
	
	/**
	 * Adds a message with value binding for the specified component. 
	 */
	public void addMessageVB(UIComponent component, String localizationKey) {
		WFUtil.addMessageVB(component, this.bundle, localizationKey);
	}
}
