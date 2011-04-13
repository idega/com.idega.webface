/*
 * $Id: HTMLAreaEmailLinkType.java,v 1.1 2005/03/08 10:39:39 gimmi Exp $
 * Created on 3.3.2005
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


public class HTMLAreaEmailLinkType implements HTMLAreaLinkType {

	public String getStartingURL() {
		return "mailto:";
	}

	public String getStartingTitle() {
		return null;
	}

	public String getStartingTarget() {
		return null;
	}

	public ValueExpression getLinkTypeName(IWBundle iwb) {
		return iwb.getValueExpression("link_type_email");
	}

	public UIComponent getLinkCreation() {
		return null;
	}

	public String getLinkType() {
		return "email";
	}
}
