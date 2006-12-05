/*
 * $Id: HTMLAreaExternalImageType.java,v 1.2 2006/12/05 15:27:29 gimmi Exp $
 * Created on 8.3.2005
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


public class HTMLAreaExternalImageType implements HTMLAreaImageType {

	
	public ValueBinding getLinkTypeName(IWBundle iwb) {
		return iwb.getValueBinding("image_type_external");
	}

	public UIComponent getCreationComponent() {
		return null;
	}

	public String getLinkType() {
		return "external";
	}

	public UIComponent getCreationComponent(String url) {
		return null;
	}
}
