/*
 * $Id: HTMLAreaImageChooserTag.java,v 1.1 2005/03/09 09:45:43 gimmi Exp $
 * Created on 8.3.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface.htmlarea;


public class HTMLAreaImageChooserTag extends HTMLAreaLinkCreatorTag {

	public String getComponentType() {
		return "HTMLAreaImageChooser";
	}

	public String getRendererType() {
		return null;
	}

}
