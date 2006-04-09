/*
 * $Id: HTMLAreaLinkCreatorTag.java,v 1.2 2006/04/09 11:59:21 laddi Exp $
 * Created on 1.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;


public class HTMLAreaLinkCreatorTag extends UIComponentTag {

	private String externalTab = null;
	
	public String getComponentType() {
		return "HTMLAreaLinkCreator";
	}

	public String getRendererType() {
		return null;
	}
	
	public void release() {
		super.release();
		this.externalTab = null;
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		if (component != null) {
			HTMLAreaLinkCreator o = (HTMLAreaLinkCreator) component;
			o.setExternalTabClass(this.externalTab);
		}
	}
	
	public void setExternalTabClass(String externalTab) {
		this.externalTab = externalTab;
	}
}
