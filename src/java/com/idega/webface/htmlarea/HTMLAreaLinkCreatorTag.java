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

import com.idega.presentation.ComponentTag;


public class HTMLAreaLinkCreatorTag extends ComponentTag {

	private Object externalTab = null;

	@Override
	public String getComponentType() {
		return "HTMLAreaLinkCreator";
	}

	@Override
	public String getRendererType() {
		return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void release() {
		super.release();
		this.externalTab = null;
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		if (component instanceof HTMLAreaLinkCreator) {

			HTMLAreaLinkCreator o = (HTMLAreaLinkCreator) component;
			String tabClass = getTypedValue(externalTab);
			if (tabClass != null) {
				o.setExternalTabClass(tabClass);
			}
		}
	}

	public void setExternalTabClass(Object externalTab) {
		this.externalTab = externalTab;
	}
}