/*
 * $Id: HTMLAreaExternalLinkType.java,v 1.1 2005/03/08 10:39:39 gimmi Exp $
 * Created on 1.3.2005
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

public class HTMLAreaExternalLinkType implements HTMLAreaLinkType {
		
	public ValueExpression getLinkTypeName(IWBundle bundle) {
		return bundle.getValueExpression("link_type_external_link");
	}

	public UIComponent getLinkCreation() {
		return null;
	}

	public String getStartingURL() {
		return "http://";
	}

	public String getStartingTitle() {
		return null;
	}

	public String getStartingTarget() {
		return null;
	}

	public String getLinkType() {
		return "external";
	}
}
